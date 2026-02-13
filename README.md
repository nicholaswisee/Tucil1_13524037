# LinkedIn Queens Solver

**Tucil1_13524037** - Brute Force N-Queens Solution

A pure brute-force solver for the LinkedIn Queens puzzle that tests all possible queen placements to find valid configurations.

## Program Description

This program solves the LinkedIn Queens puzzle using a **pure brute-force algorithm** without any heuristics or optimizations.

## Requirements

### System Requirements

- **Java Development Kit (JDK)**: Version 21 or higher
  - JavaFX 21 is included via Gradle dependencies
- **Operating System**: Windows, macOS, or Linux with Java support
- **Memory**: At least 512 MB RAM

### Installation

1. **Java JDK 21+**: Download and install from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)

   Verify installation:

   ```bash
   java -version
   ```

   Should display Java version 21 or higher.

2. **Clone or extract this repository**:
   ```bash
   git clone <repository-url>
   cd Tucil1_13524037
   ```

## Compilation Instructions

This project uses **Gradle** as the build tool. The Gradle wrapper is included, so no separate Gradle installation is needed.

### Build the Project

On **Linux/macOS**:

```bash
./gradlew build
```

On **Windows**:

```bash
gradlew.bat build
```

## Running the Program

### Step 1: Create an Input File

Create a `.txt` file with your board configuration. Each line represents a row, and letters (A-Z) represent regions.

**Example:** Create `myboard.txt`

```
AABC
ABBC
DBBC
DDDC
```

### Step 2: Run the Solver

**Simple command:**

```bash
java -jar bin/Tucil1_13524037-1.0.jar myboard.txt
```

**Or without arguments (will prompt for filename):**

```bash
java -jar bin/Tucil1_13524037-1.0.jar
```

## Project Structure

```
Tucil1_13524037/
├── src/                    # Source code directory
│   └── main/
│       ├── java/          # Java source files
│       │   └── queens/    # Main package
│       │       ├── core/  # Core solving logic
│       │       ├── gui/   # GUI components
│       │       └── util/  # Utility classes
│       └── resources/     # Resources (images, FXML, etc.)
├── bin/                   # Compiled executable JAR files
├── test/                  # Test cases and solutions
│   ├── inputs/           # Input test files
│   └── outputs/          # Solution output files
├── doc/                   # Documentation (PDF report)
├── build.gradle           # Gradle build configuration
├── settings.gradle        # Gradle settings
└── README.md             # This file
```

## Author

**Nicholas Wise Saragih Sumbayak**  
13524037 - Informatics Engineering
