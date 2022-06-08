# Path Finding using a Maze
A program that implements basic path finding and other operations on a randomly generated maze.

## Operations:
- Generate a New Maze
- Load Maze from existing file
- Save Maze to file
- Display Loaded Maze
- Find Escape

## Working:
- Once Main.java is run, the user is prompted with 2 options,
	- Load maze from existing file
	- Generate new Maze
<p align="center">
  <img src="images/menu_one.png">
</p>
<br/>

- If new maze is to be generated, the user is asked to input an integer as input for the dimensions of the maze.
<p align="center">
  <img src="images/generate.png">
</p>
<br/>

- If maze is to be loaded, user is asked to enter the path of the file that is used for importing the maze. Errors are handled.
<p align="center">
  <img src="images/load_fail.png"> <img src="images/load_success.png">
</p>
<br/>

- Once the maze has been loaded into the program from either of the mentioned options, the maze object in Main.java is updated and other operations are made available for use.
<p align="center">
  <img src="images/menu_two.png">
</p>
<br/>

- The user is now prompted to perform any of the operations that were mentioned earlier. All the operations are coded in the 'Maze.java' file in the 'src' directory.
<p align="center">
  <img src="images/display.png"> <img src="images/escape.png">
</p>
<br/>

- User can save the maze to a file before terminating the program.
<p align="center">
  <img src="images/save.png" alt="Save Output"> <img src="images/save_file.png" alt="Saved File">
</p>
<br/>

- When done, the program can be terminated by using the Exit option from the Menu.
<p align="center">
  <img src="images/exit.png">
</p>
