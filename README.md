# Brute-Force LinkedIn Queens Solver

A pure brute-force solver for the LinkedIn Queens puzzle that tests all possible queen placements to find valid configurations.

The brute-force approach used involves neither heuristics nor optimizations, and works purely by generating all valid placements of Queens.

A Model-View-Controller (MVC) architecture is used to modularize a clean, modular approach to structure the project, using Gradle as the package manager + builder.

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

**Example:** Create `test/inputs/myboard.txt`

```
AABC
ABBC
DBBC
DDDC
```

### Step 2: Run the Solver

**Simple command (filename only):**

```bash
java -jar bin/Tucil1_13524037-1.0.jar medium.txt
```

**Or with full path:**

```bash
java -jar bin/Tucil1_13524037-1.0.jar test/inputs/myboard.txt
```

**Or without arguments (will prompt):**

```bash
java -jar bin/Tucil1_13524037-1.0.jar
```

> **Note:** If you provide just a filename (e.g., `medium.txt`), it automatically looks in `test/inputs/`.
> When saving, if you provide just a filename, it automatically saves to `test/outputs/`.

## Project Structure

```
Tucil1_13524037/
├── src/
│   └── main/
│       ├── java/
│       │   └── queens/
│       │       ├── Main.java          # Application entry point
│       │       ├── model/             # Model Layer (Domain entities)
│       │       ├── view/              # View Layer (UI components)
│       │       ├── controller/        # Controller Layer
│       │       ├── service/           # Service Layer
│       │       └── util/              # Utility classes
│       └── resources/       # Resources (FXML layouts)
│           └── queens/gui/
│               └── MainView.fxml
├── bin/                     # Compiled executable JAR files
├── test/                    # Test cases and solutions
│   ├── inputs/              # Input test files
│   └── outputs/             # Solution output files
├── doc/                     # Documentation
├── build.gradle             # Gradle build configuration
├── settings.gradle          # Gradle settings
├── MVC_ARCHITECTURE.md      # MVC architecture documentation
└── README.md
```

## Author

**Nicholas Wise Saragih Sumbayak**  
13524037 - Informatics Engineering
