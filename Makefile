JDK_VERSIONS := 11 17 21

.PHONY: help
help:
	@echo "Usage:"
	@echo "  make test           - Run full tests (clean verify) on all supported JDKs ($(JDK_VERSIONS))"
	@echo "  make compile        - Run clean compile on all supported JDKs ($(JDK_VERSIONS))"
	@echo "  make format         - Auto-format code (Google Java Style)"
	@echo "  make check-format   - Check code formatting (Google Java Style)"
	@echo "  make check-style    - Run checkstyle (Google Java Style)"
	@echo "  make examples       - Regenerate EXAMPLES.md and README.md from test source code"
	@echo "  make check-examples - Verify EXAMPLES.md and README.md are up to date"
	@echo "  make version        - Show current project version"
	@echo "  make test-jdk-XX    - Run tests on a specific JDK version (e.g., make test-jdk-11)"

.PHONY: version
version:
	@mvn help:evaluate -Dexpression=project.version -q -DforceStdout
	@echo

.PHONY: test
test: $(addprefix test-jdk-,$(JDK_VERSIONS))

.PHONY: compile
compile: $(addprefix compile-jdk-,$(JDK_VERSIONS))

test-jdk-%:
	@echo "------------------------------------------------------------"
	@echo "Testing with JDK $*..."
	@actual_home=$$(/usr/libexec/java_home -v $* 2>/dev/null); \
	actual_version=$$([ -n "$$actual_home" ] && "$$actual_home/bin/java" -XshowSettings:properties -version 2>&1 | grep "java.specification.version" | awk '{print $$3}' | tr -d '"'); \
	if [ "$$actual_version" != "$*" ]; then \
		brew_path=$$(brew --prefix openjdk@$* 2>/dev/null); \
		if [ -n "$$brew_path" ] && [ -d "$$brew_path/libexec/openjdk.jdk/Contents/Home" ]; then \
			actual_home="$$brew_path/libexec/openjdk.jdk/Contents/Home"; \
			actual_version=$$("$$actual_home/bin/java" -XshowSettings:properties -version 2>&1 | grep "java.specification.version" | awk '{print $$3}' | tr -d '"'); \
		fi; \
	fi; \
	if [ "$$actual_version" != "$*" ]; then \
		echo "ERROR: JDK $* not found or not active."; \
		echo "To install it, run: brew install openjdk@$*"; \
		exit 1; \
	fi; \
	export JAVA_HOME="$$actual_home"; \
	mvn -version; \
	mvn clean verify -Dgpg.skip

compile-jdk-%:
	@echo "------------------------------------------------------------"
	@echo "Compiling with JDK $*..."
	@actual_home=$$(/usr/libexec/java_home -v $* 2>/dev/null); \
	actual_version=$$([ -n "$$actual_home" ] && "$$actual_home/bin/java" -XshowSettings:properties -version 2>&1 | grep "java.specification.version" | awk '{print $$3}' | tr -d '"'); \
	if [ "$$actual_version" != "$*" ]; then \
		brew_path=$$(brew --prefix openjdk@$* 2>/dev/null); \
		if [ -n "$$brew_path" ] && [ -d "$$brew_path/libexec/openjdk.jdk/Contents/Home" ]; then \
			actual_home="$$brew_path/libexec/openjdk.jdk/Contents/Home"; \
			actual_version=$$("$$actual_home/bin/java" -XshowSettings:properties -version 2>&1 | grep "java.specification.version" | awk '{print $$3}' | tr -d '"'); \
		fi; \
	fi; \
	if [ "$$actual_version" != "$*" ]; then \
		echo "ERROR: JDK $* not found or not active."; \
		echo "To install it, run: brew install openjdk@$*"; \
		exit 1; \
	fi; \
	export JAVA_HOME="$$actual_home"; \
	mvn -version; \
	mvn clean compile

.PHONY: format
format:
	mvn fmt:format

.PHONY: check-format
check-format:
	mvn fmt:check

.PHONY: check-style
check-style:
	mvn checkstyle:check

.PHONY: examples
examples:
	python3 tools/generate_examples.py

.PHONY: check-examples
check-examples:
	@python3 tools/generate_examples.py
	@if ! git diff --quiet EXAMPLES.md README.md; then \
		echo "ERROR: EXAMPLES.md or README.md is out of date. Run 'make examples' and commit the result."; \
		git diff EXAMPLES.md README.md; \
		exit 1; \
	fi
	@echo "EXAMPLES.md and README.md are up to date."
