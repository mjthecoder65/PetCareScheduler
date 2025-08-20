# Makefile

# Variables
SRC_DIR=src
OUT_DIR=classes
MAIN_CLASS=src.PetScheduler
JAVA_FILES=$(shell find $(SRC_DIR) -name "*.java")

# Default target
all: compile

# Compile all .java files into the classes directory
compile:
	@mkdir -p $(OUT_DIR)
	@javac -d $(OUT_DIR) $(JAVA_FILES)
	@echo "Compiled successfully."

# Run the application
run: compile
	@java -cp $(OUT_DIR) $(MAIN_CLASS)

# Clean compiled .class files
clean:
	@rm -rf $(OUT_DIR)
	@echo "Cleaned compiled files."

# Clean + compile
rebuild: clean compile
