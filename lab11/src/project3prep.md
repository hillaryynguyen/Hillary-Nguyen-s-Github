# Project 3 Prep

**What distinguishes a hallway from a room? How are they similar?**

Answer: 
Hallways are narrow, elongated spaces usually used to connect rooms or other areas in larger structures. Typically, hallways have a more regular and linear shape. 
Rooms are enclosed spaces that serve specific purposes, such as bedrooms, living rooms, or kitchens. They are typically for spacious and has various shapes. 
Both hallways and rooms are areas within a structure, defined by their boundaries, where rooms are enclosed, and hallways are open.

**Can you think of an analogy between the process of 
drawing a knight world and randomly generating a world 
using rooms and hallways?**

Answer:
Drawing a knight world involves creating a structured pattern of tiles(holes and floors) within a grid. Randomly generating a world with rooms and hallways involves 
defining the layout of rooms (enclosed spaces) and hallways (transit paths) within a larger space. In both cases, you need to decide the size, shape, and placement 
of the elements (holes/rooms and floors/hallways) to achieve a specific pattern or layout.

**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually 
get to the full knight world.**

Answer:
A good starting method for world generation might be to create a method to generate a random room. This method could determine the size and position of the room 
within the larger space. It's a fundamental building block for generating the overall structure of the world.

**This question is open-ended. Write some classes 
and methods that might be useful for Project 3. Think 
about what helper methods and classes you used for the lab!**

Answer: 
WorldGenerator Class: A class that manages the generation of the entire world, including rooms and hallways.
Room Class: A class representing a room, storing its size, position, and other attributes.
Hallway Class: A class representing a hallway, with information about its length, orientation, and connections to rooms.
Random Number Generator: A utility class or method to generate random numbers for room size, position, hallway placement, etc.
Collision Detection: A method to check for collisions between rooms and hallways to ensure they don't overlap.
Tile Renderer: A utility class to render the generated world into a grid of tiles for visualization.

