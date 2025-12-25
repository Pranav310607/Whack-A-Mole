# Whack-A-Mole Game (Java)

## Overview
The **Whack-a-Mole Game** is a Java-based project built to demonstrate the practical application of **Object-Oriented Programming (OOP)** concepts, **multithreading**, **event handling**, and **data persistence**.  
In this console-based version, Moles, Bombs, and BonusMoles appear randomly on a grid, and the player’s goal is to whack the right targets to earn points while avoiding penalties.

## Features
- Fully implemented using **core Java** (no JavaFX)
- Demonstrates **abstraction, inheritance, and polymorphism**
- **Multithreaded** game engine for smooth performance
- **Serializable high score system** for saving top scores
- **Custom exceptions** for robust error handling
- Modular and **extensible architecture** for future upgrades

## System Architecture
- **Main** – Initializes managers and starts the game.  
- **GameEngine** – Controls the game loop, object spawning, and timing.  
- **GameGrid** – Manages grid layout and hole states.  
- **HoleOccupant (Abstract)** – Base class for all in-hole objects.  
- **Mole / Bomb / BonusMole** – Subclasses defining scoring behavior.  
- **HighScoreManager** – Manages saving/loading of high scores using serialization.  
- **Custom Exceptions** – Handle file errors, invalid states, and thread interruptions.

## Technical Highlights
- **True runtime polymorphism** (no `instanceof` checks)
- **Runnable-based concurrency** for independent game loop  
- **Graceful thread interruption** at game end  
- **Serializable data storage** for persistent scores  

## Example Gameplay
- Whack **Moles** → +100 points  
- Avoid **Bombs** → −500 points  
- Catch **BonusMoles** → +1000 points  

## Conclusion
This project showcases essential Java programming skills, integrating **OOP design**, **multithreading**, **exception handling**, and **data management** into a compact, interactive game.

---

Would you like me to make a version formatted for a GitHub README with badges, contribution notes, and usage instructions?

