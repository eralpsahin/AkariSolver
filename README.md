
# Akari Solver

> An Akari (Light Up) Puzzle Solver with GUI implemented in Java using Choco-Solver.

Akari Solver is a JavaFX application that reads a text puzzle file and visualizes the solution.
![Application Img](https://user-images.githubusercontent.com/10602289/57991157-03831880-7a7b-11e9-8a55-565a7ceeb625.PNG)

## How to
Click on `File`, then `New`, then browse and open an Akari Puzzle file. Application will read the file and visualize the puzzle. Click on `Solve` to start the solver.

## File Format
Application needs the Puzzle file to be in correct format. The file consists of;

- `E` Empty cell
- `B` Black cell without number
- `0` to `4` Black cell with number indicating how many light bulbs share an edge with the cell

There are 3 sample files in the directory `puzzles/`