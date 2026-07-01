# 🧩 Sudoku Solver

A desktop Sudoku Solver built with **JavaFX** that solves Sudoku puzzles using the **Backtracking Algorithm**. The application provides an interactive graphical interface where users can enter a Sudoku puzzle and instantly find the solution.

---

## 📖 Overview

This project demonstrates how the **Backtracking Algorithm** can efficiently solve Sudoku puzzles through recursive search and constraint checking. It was developed as a university project to strengthen knowledge of algorithms, Java GUI development, and object-oriented programming.

---

## ✨ Features

* 🎯 Interactive JavaFX graphical interface
* 🧩 Solve any valid Sudoku puzzle
* ⚡ Fast Backtracking algorithm
* ✔️ Input validation
* 🔄 Reset/Clear board
* 🖥️ Simple and user-friendly interface

---

## 🛠️ Tech Stack

| Technology   | Purpose                  |
| ------------ | ------------------------ |
| Java         | Programming Language     |
| JavaFX       | Graphical User Interface |
| Maven        | Dependency Management    |
| Backtracking | Sudoku Solving Algorithm |

---

## 📂 Project Structure

```text
Sudoku-Solver/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
├── .mvn/
├── pom.xml
├── README.md
└── .gitignore
```

---

## 🧠 Algorithm

The application uses the **Backtracking Algorithm**.

### Steps

1. Find an empty cell.
2. Try numbers from **1 to 9**.
3. Check whether the number is valid.
4. If valid, move to the next empty cell.
5. If no number works, backtrack to the previous cell.
6. Continue until the puzzle is solved.

### Time Complexity

Worst Case:

```text
O(9^(N×N))
```

where **N = 9** for a standard Sudoku.

Although the theoretical complexity is high, backtracking performs efficiently for typical Sudoku puzzles.

---


## 🚀 Getting Started

### Clone the repository

```bash
git clone https://github.com/rittik-kumar-dev/sudoku-solver.git
```

### Open the project

Open the project using **IntelliJ IDEA**.

### Run

Execute the application's main class to launch the JavaFX interface.

---

## 🎯 Future Improvements

* Random Sudoku puzzle generator
* Multiple difficulty levels
* Hint system
* Timer
* Score tracking
* Dark mode
* Better UI design

---

## 👨‍💻 Author

**Rittik Kumar**

* GitHub: https://github.com/rittik-kumar-dev

---

## ⭐ Support

If you found this project helpful, consider giving it a **⭐ Star** on GitHub.
