# Board Game Application – IDATT2003

Team Members: Sasha Unruh & Dennis Moe
Group / Candidates: Group 0042 – 10006 & 10106

# Project Description

A desktop application that lets users play classic board-games—currently two variants of Snakes & Ladders—with up to five players. It delivers:

Interactive JavaFX GUI (hand-coded)

Save/load of players, boards and statistics via JSON/SQLite

High-cohesion, low-coupling codebase using MVC, Factory and Observer patterns

Command-line build & run with Apache Maven and JDK 21

The design emphasises extensibility: new games, tiles, or movement rules can be plugged in with minimal rewiring.

# Tech Stack

Language Java JDK 21
UI JavaFX – org.openjfx:javafx-controls:23.0.1
Build Maven 3.9+ with maven-compiler-plugin:3.13.0 and maven-surefire-plugin:3.2.5
Testing JUnit 5 – org.junit.jupiter:junit-jupiter-engine:5.11.4
Code Quality - CheckStyle Google rules
Persistence JSON files + SQLite
VCS / CI - Git & GitHub

# Project Structure

```txt
boardgames/ ← Maven module root
├─ src/
│ ├─ main/
│ │ ├─ java/
│ │ │ ├─ model/ ← game state & business logic
│ │ │ ├─ view/ ← JavaFX UI classes
│ │ │ ├─ controller/ ← MVC controllers
│ │ │ ├─ service/ ← higher-level workflow/services
│ │ │ ├─ dao/ ← SQLite & JSON DAOs
│ │ │ ├─ exception/ ← custom exceptions
│ │ │ └─ util/ ← helpers, factories, strategies
│ │ └─ resources/
│ │ ├─ boards/ ← JSON board layouts
│ │ ├─ pieces/ ← token images or SVGs
│ │ ├─ sound/ ← SFX
│ │ ├─ laddergame_assets/ ← extra graphics for Snakes & Ladders
│ │ └─ css/ ← styling
│ └─ test/ ← JUnit tests (AAA pattern)
└─ pom.xml
```

# Deployment

Install prerequisites:

JDK 21 – download & install from Oracle (Windows link)
https://www.oracle.com/cis/java/technologies/downloads/#jdk21-windows

Apache Maven 3.9 + – download, install, and add mvn to PATH
https://maven.apache.org/download.cgi

### Run the app

### open a terminal in the project

cd path/to/IDATx2003-exam/boardgames
mvn javafx:run
(If your file browser supports it, right-click inside the boardgames/ folder and choose “Open in terminal” to skip the cd step.)

# Development Workflow

- GitHub Flow: feature branches, PR reviews, tags for milestones

- Discord: to-dos, bug-reports, work logs, weekly stand-ups

- CheckStyle: enforced on commit to maintain naming, Javadoc, and formatting standards

# Patterns:

- Factory for boards, tiles and views

- JavaFX Observable & custom Observer for UI updates

- MVC to isolate Model, View and Controller layers

Up-front planning and continuous refactoring avoided major rewrites and kept the codebase modular.

# Known Limitations / Next Steps

- Only two Snakes & Ladders boards shipped; adding Ludo is planned.

- Unit-test coverage on UI controllers is thin due to JavaFX constraints.

- A builder pattern for complex board composition is a future improvement.
