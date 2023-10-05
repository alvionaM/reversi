<div id="header" align="center">
    <img alt="Wall-e" src="https://media.giphy.com/media/3rgXBB98k23dXxT1du/giphy.gif" height="140"">
</div>

## Description📌
<p align="justify">This repository hosts a Reversi game (also known as Othello) developed in Java with Minimax alpha-beta pruning algorithm.
Supposing the general rules of the game are known (<a href="https://en.wikipedia.org/wiki/Reversi">or can be found</a>), we proceed to mention some special features of this implementation:</p>

Two game modes are supported:
1. Human-player <b><u>VS</b></u> CPU-player (let's call this "User-mode")
2. CPU-player <b><u>VS</b></u> CPU-player (let's call this "AI-mode"),
The latter is used for observation purposes.
To achieve this, we construct three players, one Human and two Machines. The second Machine is used in AI-mode (where it plays against the first one). AI-mode is selected through the command-line parameter ```-testai```
<i>(see the following section for more)</i>.

➡️In case the User-mode is selected:
- The user enters their name
- The CPU-player is given the name Wall-e!
- The user then selects a difficulty level (this corresponds to the maximum depth with which the “Minimax alpha-beta” algorithm works)
- The user chooses whether to play first or second
- Depending on the previous choice, both players are assigned a color (the one who plays first is always assigned black, according to the rules)

➡️In case the AI-mode is selected:
- The operator enters the difficulty level (it should be the same for both CPU-players)
- The CPU players are named Wall-e and R2-d2
- Each CPU-players is aasigned a color arbitrarily (black color is given to Wall-e and white is given to R2-d2)

<div id="header" align="center">

</div>
<p align="justify">Firstly, a game board is created with four checkers placed on the board according to the rules. Each time it is a player's turn to play, their available moves are computed and they are expected to take one of those (if any available). Then it's the other player's turn.  It is possible that a player be unable to move at some point of the game, in which case the other player plays again. If during two consecutive rounds no player can move, it means the game is over. In this case, the winning board of the game is displayed. Finally, the score is calculated and the winner is announced or the game ends in a tie.</p>

<br>

## Play Reversi 💻
Compile and run:
1. ```javac *.java```
2. ```chcp 65001```  (for Windows<b>!</b>, keep reading for details)
3. ```java Reversi```

- To run the game in A.I. test mode (AI-mode) pass the parameter ```-testai``` in the 3<sup>rd</sup> command, i.e.:```java Reversi -testai```

- In AI-mode termination has been checked up to maxDepth=9. For higher values the program runs as well but with a substantially longer delay as the depth increases.

- For a better gaming experience, please run the project in UNIX environment (using any macOS/Linux terminal). For Windows we recommend using an IDE (e.g. IntelliJ) or the Windows Terminal (either with cmd or with PowerShell). In this case the 2<sup>nd</sup> command ```chcp 65001``` is required so that Unicode characters are printed correctly.

> Caution! The checkers will <b>not be displayed correctly</b> on Windows <b>if</b> run directly via cmd/PowerShell even if the ```chcp 65001``` command is given. It needs to be done via the Windows Terminal app!

<br>

## Demo 🧑‍🏫
<p align="justify">For demonstration purposes, we have constructed GIFs for some of the games played, each showing the successive moves taken by our CPU-player both in the case playing against a Human-player as well as when playing against another CPU-player that uses a completely different heuristic function <i>(see the implementation details for more on the heuristic function used in this project)</i>.</p>

<details>
<summary> <b>Win</b> (25⚫- 39⚪) against a Human-player (difficulty level = maximum depth = <b>3</b>) </summary>
<div align="center">
    <img src="../media/Win-human_depth3.gif" alt="Win against Human-player, maximum depth=3" width="350">
</div>
</details>

<details>
<summary> <b>Win</b> (12⚫- 52⚪) against a different CPU-player (maximum depth = <b>5</b>) </summary>
<div align="center">
    <img src="..media/Win-other_team_depth5.gif" alt="Win against CPU-player, maximum depth=5" width="350">
</div>
</details>

<details>
<summary> <b>Tie</b> (32⚫- 32⚪) against a different CPU-player (difficulty level = maximum depth = <b>8</b>)</summary>
<div align="center">
    <img src="../media/Tie-other_team_depth8.gif" alt="Win against CPU-player, maximum depth=5" width="350">
</div>
</details>

<br>

## Implementation Details📜
### ● Game board 
<p align="justify">The game board is modeled as an <b>8x8 2D array of integers</b> (gameBoard[8][8]). These integers denote if the respective cell contains a black checker (value BLACK=1) or a white one (value WHITE=-1) or if it is empty (value EMPTY=0).</p>

<div align="center">
    <img src="../media/Game_board.png" alt="The game board" width="350">
</div>

### ● Code Structure:
- **Board** class: It represents the game board.
- **Move** class: It represents a move on the board. It's used so that the Board class can keep track of the last move that resulted in a board looking the way it currently does.
- **Player** class (abstract): It represents any player by implementing the functionality needed for every kind of player.
- **Human** class: Subclass of Player. It represents a Human-player.
- **Machine** class: Subclass of Player. It represents a CPU-player that uses A.I. to play.
- **Reversi** class: The class that contains the main() method and starts the game.

### ● Heuristic function

<p align="justify">Each time a CPU-player plays, it must decide which move to take. This is achieved using the Minimax alpha-beta pruning algorithm, that, to put it succinctly, uses DFS to explore the tree of possible moves and decide on the best move based on a utility value (e.g. 1 for Win, -1 for Loss and 0 for Tie). Nevertheless, due to the fact that the tree becomes prohibitively large at some point of the game and it is, therefore, explored only looking as deep as the maximum depth limitation indicates, the utility value can only be estimated using a heuristic approach.
<br>
<b>In this implementation, the following heuristic function is used:</b></p>

$`50*\color{purple}X`$ $`+\; 20*\color{teal}Y`$ $`+\; 1*\color{olive}Z`$

$\color{purple}X$: # ⚫ corner cells + # ⚪ corner cells <br>
$\color{olive}Y$: # ⚫ side cells + # ⚪ side cells (without counting the corners) <br>
$\color{teal}Z$: # ⚫ cells (all kinds) + # ⚪ cells (all kinds) <br>

<p align="justify">💡The choice of the heuristic is such that a very strong emphasis is given in conquering corner cells as they are distinguished by stability (if conquered they cannot change color afterwards) and they are key positions for the development of the game. Less importance is given in conquering side cells as they enable of conquering a significant number of the opponent's checkers in the next rounds and they are also distinguished by a degree of stability. Finally, the total cells occupied by a player are taken into account using a small weight in order not to be considered as a catalytic factor for the decision (since occupying as many cells as possible is not an optimal strategy during all phases of the game), but be a move selection criterion before corner and side cells start becoming important.</p>
