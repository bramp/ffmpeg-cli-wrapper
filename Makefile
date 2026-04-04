JDK_VERSIONS := 11 17 21

.PHONY: help
help:
	@echo "Usage:"
	@echo "  make test         - Run full tests (clean verify) on all supported JDKs ($(JDK_VERSIONS))"
	@echo "  make compile      - Run clean compile on all supported JDKs ($(JDK_VERSIONS))"
	@echo "  make version      - Show current project version"
	@echo "  make test-jdk-XX  - Run tests on a specific JDK version (e.g., make test-jdk-11)"

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
