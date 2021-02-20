# Othello
<p align="center">
    <img width="60%" src="https://github.com/emma-campbell/searching/blob/master/img/demo.svg">
</p>

# Authors
|Name|Email|
|:---:|:---:|
|Emma Campbell|ecampb10@u.rochester.edu|
|Emma Schechter|eschecht@u.rochester.edu|


# Contents
The main package directory `othello` should be one of four items that you see when you unzip.

```
othello
├── ai
│   ├── algorithms
│   │   ├── Minimax.java
│   │   ├── MinimaxAlphaBeta.java
│   │   └── SearchAlgorithm.java
│   └── search
│       ├── Problem.java
│       └── State.java
├── game
│   ├── Board.java
│   ├── Color.java
│   ├── Othello.java
│   └── exceptions
│       ├── IllegalBoardDimensions.java
│       └── IllegalMove.java
├── player
│   ├── AI.java
│   ├── Human.java
│   └── Player.java
└── tui
    └── Utils.java
```

Outside of the main package, there is a singular file `Run.java`, your entry point into the program.

# How to Run

Once unzipped, navigate to the unzipped directory's location in your terminal. Once there, you simply need to compile the entry file `Run.java` using the following command:

```
javac Run.java
```
and to run

```
java Run
```

# Todos
- [x] Random  
- [X] H-MINIMAX
- [X] H-MINIMAX w/ alpha-beta pruning
- [X] Code Commenting
- [ ] Code Refactor -- Othello.java
- [ ] Timer Functionality -- AI. 
