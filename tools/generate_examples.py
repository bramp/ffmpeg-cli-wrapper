#!/usr/bin/env python3
"""Extracts examples from Java test files, generates EXAMPLES.md, and updates README.md.

Scans for two kinds of marker comments in Java test files:

    // <!-- example: Title -->  ...  // <!-- /example -->
        → extracted into EXAMPLES.md

    // <!-- readme: Title -->   ...  // <!-- /readme -->
        → injected into README.md between matching HTML comment markers

readme: markers may nest inside example: blocks.  When extracting example
code, readme marker lines are skipped but the code between them is kept.

Usage:
    python tools/generate_examples.py
"""

import os
import re
import sys

REPO_ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

# Java source files to scan, in order
SOURCE_FILES = [
    "src/test/java/net/bramp/ffmpeg/ReadmeTest.java",
    "src/test/java/net/bramp/ffmpeg/ExamplesTest.java",
]

EXAMPLES_FILE = "EXAMPLES.md"
README_FILE = "README.md"

# --- Java marker patterns ---------------------------------------------------
EXAMPLE_START = re.compile(r"^\s*//\s*<!--\s*example:\s*(.+?)\s*-->")
EXAMPLE_END = re.compile(r"^\s*//\s*<!--\s*/example\s*-->")
README_START = re.compile(r"^\s*//\s*<!--\s*readme:\s*(.+?)\s*-->")
README_END = re.compile(r"^\s*//\s*<!--\s*/readme\s*-->")
COMMENT_LINE = re.compile(r"^\s*//\s?(.*)")

# --- Markdown marker patterns (in README.md) ---------------------------------
MD_README_START = re.compile(r"^<!--\s*readme:\s*(.+?)\s*-->")
MD_README_END = re.compile(r"^<!--\s*/readme\s*-->")

# --- README.md substitutions -------------------------------------------------
# Applied to extracted readme code before injecting into README.md.
# Each entry is (compiled_regex, replacement_string).
README_SUBSTITUTIONS = [
    (re.compile(r"Samples\.\w+"), '"input.mp4"'),
    (re.compile(r"\bStrict\.EXPERIMENTAL\b"), "FFmpegBuilder.Strict.EXPERIMENTAL"),
]

# Lines prepended to specific readme blocks (after dedenting).
README_PREAMBLES = {
    "Video Encoding": [
        'FFmpeg ffmpeg = new FFmpeg("/path/to/ffmpeg");',
        'FFprobe ffprobe = new FFprobe("/path/to/ffprobe");',
        "",
    ],
    "Get Media Information": [
        'FFprobe ffprobe = new FFprobe("/path/to/ffprobe");',
    ],
}


# -----------------------------------------------------------------------------
# Extraction
# -----------------------------------------------------------------------------

def extract_from_java(filepath):
    """Parse a Java file and return (examples, readme_blocks).

    examples:       list of (title, description_lines, code_lines) for EXAMPLES.md
    readme_blocks:  dict  {title: code_lines}                      for README.md

    readme: markers nested inside example: blocks are transparent to example
    extraction — the marker lines are skipped but the code between them is kept.
    """
    examples = []
    readme_blocks = {}

    example_title = None
    example_desc = []
    example_code = []
    in_desc = False

    readme_title = None
    readme_code = []

    with open(filepath) as f:
        for line in f:
            # --- readme markers (checked first; they nest inside examples) ---
            rm = README_START.match(line)
            if rm:
                readme_title = rm.group(1).strip()
                readme_code = []
                continue  # skip marker line for both readme and example

            if README_END.match(line):
                if readme_title is not None:
                    readme_blocks[readme_title] = readme_code
                    readme_title = None
                    readme_code = []
                continue  # skip marker line

            # Collect into current readme block (if open)
            if readme_title is not None:
                readme_code.append(line.rstrip())

            # --- example markers ---
            em = EXAMPLE_START.match(line)
            if em and example_title is None:
                example_title = em.group(1).strip()
                example_desc = []
                example_code = []
                in_desc = True
                continue

            if EXAMPLE_END.match(line) and example_title is not None:
                examples.append((example_title, example_desc, example_code))
                example_title = None
                continue

            # Inside an example block — collect description or code
            if example_title is not None:
                if in_desc:
                    cm = COMMENT_LINE.match(line)
                    if cm and not example_code:
                        example_desc.append(cm.group(1))
                    else:
                        in_desc = False
                        example_code.append(line.rstrip())
                else:
                    example_code.append(line.rstrip())

    if example_title is not None:
        print(f"WARNING: unclosed example '{example_title}' in {filepath}", file=sys.stderr)
    if readme_title is not None:
        print(f"WARNING: unclosed readme '{readme_title}' in {filepath}", file=sys.stderr)

    return examples, readme_blocks


# -----------------------------------------------------------------------------
# Helpers
# -----------------------------------------------------------------------------

def dedent_code(lines):
    """Remove common leading whitespace from code lines."""
    non_empty = [l for l in lines if l.strip()]
    if not non_empty:
        return list(lines)
    min_indent = min(len(l) - len(l.lstrip()) for l in non_empty)
    return [l[min_indent:] if len(l) >= min_indent else l for l in lines]


def strip_blank_edges(lines):
    """Return a copy of *lines* with leading/trailing blank lines removed."""
    out = list(lines)
    while out and not out[0].strip():
        out.pop(0)
    while out and not out[-1].strip():
        out.pop()
    return out


def title_to_anchor(title):
    """Convert a title to a GitHub-compatible markdown anchor."""
    anchor = title.lower()
    anchor = re.sub(r"[^\w\s-]", "", anchor)
    anchor = re.sub(r"\s+", "-", anchor)
    return anchor


# -----------------------------------------------------------------------------
# EXAMPLES.md generation
# -----------------------------------------------------------------------------

def generate_examples_md(all_examples):
    """Build the full EXAMPLES.md content string."""
    lines = [
        "# Examples",
        "",
        "_This file is auto-generated from test code. Do not edit directly._  ",
        "_Run `python tools/generate_examples.py` to regenerate._",
        "",
        "All examples below are extracted from compilable, tested Java code.",
        "See [ReadmeTest.java](src/test/java/net/bramp/ffmpeg/ReadmeTest.java)",
        "and [ExamplesTest.java](src/test/java/net/bramp/ffmpeg/ExamplesTest.java)",
        "for the full source.",
        "",
        "## Table of Contents",
        "",
    ]
    for title, _, _ in all_examples:
        anchor = title_to_anchor(title)
        lines.append(f"- [{title}](#{anchor})")
    lines.append("")

    for title, desc, code in all_examples:
        lines.append(f"## {title}")
        lines.append("")
        if desc:
            lines.append(" ".join(l for l in desc if l))
            lines.append("")
        code = strip_blank_edges(dedent_code(code))
        lines.append("```java")
        lines.extend(code)
        lines.append("```")
        lines.append("")

    return "\n".join(lines)


# -----------------------------------------------------------------------------
# README.md updating
# -----------------------------------------------------------------------------

def apply_readme_transforms(title, lines):
    """Apply preamble and text substitutions to a readme block."""
    result = list(lines)

    # Apply text substitutions
    for pattern, repl in README_SUBSTITUTIONS:
        result = [pattern.sub(repl, l) for l in result]

    # Prepend preamble if defined
    preamble = README_PREAMBLES.get(title)
    if preamble:
        result = list(preamble) + result

    return result


def update_readme(readme_path, readme_blocks):
    """Replace code between <!-- readme: X --> / <!-- /readme --> in README.md.

    Returns the number of blocks updated.
    """
    with open(readme_path) as f:
        lines = f.readlines()

    output = []
    updated = 0
    skipping = False

    for line in lines:
        m = MD_README_START.match(line)
        if m:
            title = m.group(1).strip()
            output.append(line)  # keep the opening marker
            if title in readme_blocks:
                code = strip_blank_edges(dedent_code(list(readme_blocks[title])))
                code = apply_readme_transforms(title, code)
                output.append("```java\n")
                for cl in code:
                    output.append(cl + "\n")
                output.append("```\n")
                skipping = True
                updated += 1
            else:
                print(f"WARNING: no Java source for readme block '{title}'", file=sys.stderr)
            continue

        if MD_README_END.match(line):
            skipping = False
            output.append(line)  # keep the closing marker
            continue

        if not skipping:
            output.append(line)

    with open(readme_path, "w") as f:
        f.writelines(output)

    return updated


# -----------------------------------------------------------------------------
# Main
# -----------------------------------------------------------------------------

def main():
    all_examples = []
    all_readme = {}

    for relpath in SOURCE_FILES:
        filepath = os.path.join(REPO_ROOT, relpath)
        if not os.path.exists(filepath):
            print(f"WARNING: {filepath} not found, skipping", file=sys.stderr)
            continue
        examples, readme_blocks = extract_from_java(filepath)
        if examples:
            print(f"  {relpath}: {len(examples)} examples")
        all_examples.extend(examples)
        all_readme.update(readme_blocks)

    if not all_examples:
        print("ERROR: no examples found. Add marker comments to source files.", file=sys.stderr)
        sys.exit(1)

    # Generate EXAMPLES.md
    examples_path = os.path.join(REPO_ROOT, EXAMPLES_FILE)
    with open(examples_path, "w") as f:
        f.write(generate_examples_md(all_examples))
    print(f"\nGenerated {EXAMPLES_FILE} with {len(all_examples)} examples.")

    # Update README.md
    if all_readme:
        readme_path = os.path.join(REPO_ROOT, README_FILE)
        count = update_readme(readme_path, all_readme)
        print(f"Updated {README_FILE} with {count} code blocks.")
    else:
        print("No readme: blocks found; README.md unchanged.")


if __name__ == "__main__":
    main()
