# Library Management System (LMS) — Console App

##  Overview
This is a simple **Library Management System (LMS)** console application developed in **Java 17**.  
It allows importing patrons from a text file, adding new patrons, removing patrons, listing all patrons, and exiting the system.

The project follows the **Software Development Life Cycle (SDLC)** and was created as part of the course assignment.

---

##  Features
- Load patrons automatically from `patrons_sample.txt` at startup.
- Import patrons manually from another file.
- Add new patrons with ID, name, address, and fine.
- Remove patrons by ID.
- Print a formatted list of all patrons.
- Menu-driven interface that loops until the user exits.

---

##  Project Structure

src/
└── com/luismonserratt/lms/
├── LibraryApp.java # Main class (menu + CLI)
├── LibraryManager.java # Business logic (load, add, remove, list)
├── Patron.java # Patron entity (id, name, address, fine)
└── ImportResult.java # Tracks results of file import
patrons_sample.txt # Sample input file


---

##  How to Run

### 1. Run in IntelliJ IDEA
- Open the project in IntelliJ.
- Run `LibraryApp` (contains the `main` method).
- The program will load patrons from `patrons_sample.txt` automatically.

### 2. Run from Terminal (compile manually)

First, build the JAR in IntelliJ:
File > Project Structure > Artifacts > + > JAR > From modules with dependencies → Select LibraryApp as the main class.

Then build the artifact:
```bash
javac -d out $(find src -name "*.java")
java -cp out com.luismonserratt.lms.LibraryApp

3. Run Executable JAR

First, build the JAR in IntelliJ:
File > Project Structure > Artifacts > + > JAR > From modules with dependencies → Select LibraryApp as the main class.

Then build the artifact:

java -jar LmsConsoleApp.jar

Sample Input File

The system uses a text file where each line has the format:

id-name-address-fine

Example: patrons_sample.txt

1234567-John Doe-101 Main St Orlando FL-10.50
2345678-Jane Smith-202 Pine Ave Kissimmee FL-0
3456789-Michael Brown-303 Oak Blvd Tampa FL-25
4567890-Sarah Johnson-404 Maple Dr Miami FL-5.75
5678901-David Wilson-505 Cedar Ln Jacksonville FL-0

Testing Checklist

Test 1: Read Data → Enter file path and load patrons.

Test 2: Display Data → Show patrons after import.

Test 3: Display a Menu → Menu shows options until exit.

Test 4: Create Data → Add a new patron manually.

Test 5: Remove Data → Remove a patron by ID.

Comments → Source code includes comments explaining functionality.

Author

Luis Augusto Monserratt Alvarado
Valencia College — BAS Computing Technology & Software Development
