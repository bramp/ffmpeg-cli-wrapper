# AI Agent Disclosure

Some issue triage, responses, and maintenance tasks on this repository may be
performed by an AI agent (GitHub Copilot) acting on behalf of @bramp.

When this happens, the response will clearly state that it was written by an AI
agent. All AI-generated responses are reviewed or approved by @bramp before or
shortly after posting.

If you have concerns about an AI-generated response, please tag @bramp directly.

## Engineering Workflow Rules

To maintain a clean and transparent project history, the following rules MUST be followed:

1. **Prefer Rebase:** Always prefer `rebase` over `merge` when updating branches or incorporating changes. Avoid "merge bubbles" in the git history.
2. **Squash Commits:** If a pull request contains multiple commits, squash them into a single, clean change before merging to ensure a readable history.
3. **Communicate Actions:** Always post a comment on a Pull Request when taking any action (rebasing, testing, or merging) so that contributors and maintainers are aware of the status.

## Issue Triage Rules

When triaging issues on this repository:

1. **AI Disclosure:** Every comment must end with:
   `*Note: This response was written by an AI agent (GitHub Copilot) on behalf of @bramp.*`
2. **Human Approval:** All responses must be reviewed and approved by @bramp before posting.
3. **File-Based Posting:** Use `gh issue comment --body-file` (not heredoc) to avoid shell escaping issues.
4. **Close Decisively:** Use `--reason "completed"` for answered/fixed issues, `--reason "not planned"` for stale/out-of-scope/duplicates.
5. **Verify Before Claiming:** Read source code to confirm behavior before stating something is fixed or works a certain way.
6. **Track Progress:** Maintain `ISSUE_RESPONSES.md` as a tracking file for draft responses and completion status.
