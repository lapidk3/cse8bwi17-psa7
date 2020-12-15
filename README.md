Name: Kenneth Lapid David, cs8bwans
Date: March 7, 2017


For this particular PSA I picked up where I left off in previous PSA's where 
I worked with replicating the popular game 2048. I designed the visual
aspects of the game by creating the game board, and each of the tiles and
titles that are associated with it. One can run the game and play a fully
functional 2048 game without having to play on the terminal screen. I had 
to make sure that if a computer key was pressed then the tiles and score would 
be updated on my game. To play the game, just press any of the valid keys to
start a command. Pressing the arrow keys will cause the tiles on the board
to move in the direction pressed, and pressing <s> will save the current
game board for future use. The goal is to get to the number 2048 or higher.
If you run out of space to move the tiles, then the game will be over! For 
the other part of this PSA I worked on a topic
called Recursion. Basically, recursion is the repetitve use of something. In
my case, I made a recursive program to reverse numbers. For example
if I had numbers 1 2 3 4 5, then the algorithim I created will reverse these
elements into 5 4 3 2 1.

1. mkdir fooDir barDir

2. "mv" lets you move a file to another directory.To use the command:
type "mv" then the directory you want it moved to

3. -p. filename.java.c filename2.java.c ...

4. A static method is a method that belong to the class rather than the objects
of that class. A static method can be invoked without creating an instance 
of the class. You can invoke a static method by: Classname.method(thatisstatic);

5. To efficiently improve your design  you could try to use inheritance. Create
different classes for each sjape and have them inherit from the class ShapeDrawer.
Since we're going to draw each shape, make the ShapeDrawer class abstract and include
the abstract method drawCOLOR_NAMEOFSHAPE() to ensure that all the children
(shapes) inherit the method. Also have an abstract field, color, which you would
be able to define for each shape. Each shaoe will also inherit the methods and instance
variables of the superclass ShapeDrawer. The use of inheritance simplifies the coding
process, allowing you to keep everything organized and avoid having to rewrite multiple
lines of code. To make a class abstract use the abstract modifier, to inherit from
a class use the keyword extends. For example
private abstract class ShapeDrawer. and then private class RedCircle extends ShapeDrawer. 
