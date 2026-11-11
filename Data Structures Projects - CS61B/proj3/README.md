proj3 from CS61B at UC Berkeley taken Fall 2024: https://fa24.datastructur.es/projects/proj3/

Overview:
Your task for the next few weeks is to design and implement a 2D tile-based world exploration engine. By “tile-based”, we mean the worlds you generate will consist of a 2D grid of tiles. By “world exploration engine” we mean that your software will build a world, which the user will be able to explore by walking around and interacting with objects in that world. Your world will have an overhead perspective. As an example of a much more sophisticated system than you will build, the NES game “Zelda II” is (sometimes) a tile based world exploration engine that happens to be a video game:

Screenshot of Zelda II.

The system you build can either use graphical tiles (as shown above), or it can use text based tiles, like the game shown below:

Screenshot of Brogue.

We will provide a tile renderer and a small set of starter tiles for you to use.

Your roadmap for this project:

1. First, you’ll implement code to generate random worlds.
2. Then, you’ll add a main menu for the user to start the game from.
3. Then, you’ll add support for saving a game and loading it back.
4. Finally, you’ll get a chance to add any extra fun features to your project.
