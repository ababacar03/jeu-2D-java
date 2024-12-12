GhostRun

A libGDX project generated with gdx-liftoff.

This project was generated with a template including simple application launchers and an ApplicationAdapter extension that draws the libGDX logo.

Platforms

core: Main module with the application logic shared by all platforms.

lwjgl3: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

lwjgl2: Legacy desktop platform using LWJGL2.

Gradle

This project uses Gradle to manage dependencies. The Gradle wrapper was included, so you can run Gradle tasks using gradlew.bat or ./gradlew commands. Useful Gradle tasks and flags:

--continue: when using this flag, errors will not stop the tasks from running.

--daemon: thanks to this flag, Gradle daemon will be used to run chosen tasks.

--offline: when using this flag, cached dependency archives will be used.

--refresh-dependencies: this flag forces validation of all dependencies. Useful for snapshot versions.

build: builds sources and archives of every project.

cleanEclipse: removes Eclipse project data.

cleanIdea: removes IntelliJ project data.

clean: removes build folders, which store compiled classes and built archives.

eclipse: generates Eclipse project data.

idea: generates IntelliJ project data.

lwjgl2:jar: builds application's runnable jar, which can be found at lwjgl2/build/libs.

lwjgl2:run: starts the application.

lwjgl3:jar: builds application's runnable jar, which can be found at lwjgl3/build/libs.

lwjgl3:run: starts the application.

test: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with name: prefix, where the name should be replaced with the ID of a specific project. For example, core:clean removes the build folder only from the core project.

About GhostRun

GhostRun is a 2D platformer game inspired by classic platformers like Super Mario. The player takes control of a Mario-like character and navigates through levels filled with platforms, obstacles, and potential hazards.

Gameplay

Movement: Use the arrow keys to move:

Right Arrow: Move Mario to the right.

Left Arrow: Move Mario to the left.

Jumping: Press the spacebar to make Mario jump.

Features

Classic Platformer Elements: The game features platforms of varying sizes and heights, requiring precise timing and movement.

Challenging Obstacles: Navigate through pits, spikes, and other hazards to reach the end of each level.

Smooth Controls: The movement and jumping mechanics are designed to feel responsive and intuitive.

Retro Aesthetic: The art style and animations give the game a nostalgic platformer vibe.

Future Enhancements

Adding collectible items (e.g., coins or gems) for scoring.

Implementing enemies and combat mechanics.

Creating multiple levels with increasing difficulty.

Adding background music and sound effects for a more immersive experience.

How to Play

Clone this repository and set up the project as described in the Gradle section.

Run the game using the lwjgl3:run task to launch the desktop version.

Use the arrow keys and spacebar to navigate the ghost through the level.

Controls Summary

Action

Key

Move Right

Right Arrow

Move Left

Left Arrow

Jump

Spacebar

Enjoy playing GhostRun!
