# Brute-Force LinkedIn Queens Solver

A pure brute-force solver for the LinkedIn Queens puzzle that tests all possible queen placements to find valid configurations.

The brute-force approach used involves neither heuristics nor optimizations, and works purely by generating all possible placements of Queens, limited to only 1 Queen per row (exhaustive search).

A Model-View-Controller (MVC) architecture is used to structure the project cleanly, using Gradle as the build tool.

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

2. **Clone this repository**:

   ```bash
   git clone https://github.com/nicholaswisee/Tucil1_1352403
   cd Tucil1_13524037
   ```

## Compilation Instructions

This project uses **Gradle** as the build tool. The Gradle wrapper is included, so no separate Gradle installation is needed.

### Build the Project

On **Linux/macOS**:

```bash
./gradlew build --no-configuration-cache
```

On **Windows**:

```bash
gradlew.bat build --no-configuration-cache
```

## Running the Program

Run the JavaFX GUI application:

```bash
./gradlew run --no-configuration-cache
```

Or using the pre-built JAR:

```bash
java -jar bin/Tucil1_13524037-1.0.jar
```

> **Note:** If you provide just a filename (e.g., `tc2.txt`), it automatically looks in `test/inputs/`.
> When saving, if you provide just a filename, it automatically saves to `test/outputs/`.

## Input Format

Create a `.txt` file where each line represents a row of the board, and letters (`A`–`Z`) represent color regions. The board must be square (N×N) with exactly N distinct regions.

**Example** (`test/inputs/tc2.txt` — 5×5 board):

```
AABBC
AABBC
DDBBC
DDEEC
DDEEE
```

## Output Format

The `.txt` output marks queen positions with `#`:

```
AA#BC
AABBC
DDBBC
DDEEC
DD#EE
Waktu pencarian: 6 ms
Banyak kasus yang ditinjau: 350 kasus
```

## Test Cases

Pre-built test cases are available in `test/inputs/`.

## Project Structure

```
Tucil1_13524037/
├── src/
│   └── main/
│       ├── java/
│       │   └── queens/
│       │       ├── Main.java              # Application entry point
│       │       ├── model/                 # Model layer (Board, Solution, Position, SolutionStats)
│       │       ├── view/                  # View layer (BoardCanvas, QueensGUI)
│       │       ├── controller/            # Controller layer (MainController, CLIController)
│       │       ├── service/               # Service layer (SolverService, FileService)
│       │       └── util/                  # Utility classes (BoardFormatter)
│       └── resources/
│           └── queens/gui/
│               └── MainView.fxml          # JavaFX layout
├── bin/                                   # Pre-built executable JAR
│   └── Tucil1_13524037-1.0.jar
├── test/
│   ├── inputs/                            # Input test files (tc1.txt – tc6.txt)
│   └── outputs/                           # Solution output files (generated)
├── doc/                                   # Documentation and report (main.tex)
├── build.gradle                           # Gradle build configuration
├── settings.gradle                        # Gradle settings
└── README.md
```

## Author

**Nicholas Wise Saragih Sumbayak**  
13524037 — Teknik Informatika, Institut Teknologi Bandung
