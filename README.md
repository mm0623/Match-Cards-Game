# Match-Cards-Game

### This is a Java program of the [Matching Card Pairs](https://en.wikipedia.org/wiki/Concentration_(card_game)#:~:text=Concentration%20is%20a%20round%20game,over%20pairs%20of%20matching%20cards.&text=Concentration%20can%20be%20played%20with,a%20solitaire%20or%20patience%20game.) game. After specifying a game size of `n` (at least 2 cards), users can choose to `PLAY` the game themselves or `COMPARE` how two different AIs play. The bad AI flips random legal cards with equal probability independent of past while the good AI plays with perfect memory of past flipped cards, always scoring 2n or better.

To run the program in the command terminal, navigate to the `src` folder:
```
$ cd .../Match-Cards-Game/src
```
Compile the source files:
```
$ javac hw2/*.java
```

Run the following file containing the `main` function and follow the prompts:
```
$ java hw2.PlayCard
```

Here is a sample 16-card game where the program asks users to specify pairs of cards to flip:

```
_____________________________

0: *	1: *	2: *	3: *	
4: *	5: *	6: *	7: *	
8: *	9: *	10: *	11: *	
12: *	13: *	14: *	15: *	
_____________________________

Pick the first card:
0

_____________________________

0: A	1: *	2: *	3: *	
4: *	5: *	6: *	7: *	
8: *	9: *	10: *	11: *	
12: *	13: *	14: *	15: *	
_____________________________

Pick the second card:
3

_____________________________

0: A	1: *	2: *	3: D	
4: *	5: *	6: *	7: *	
8: *	9: *	10: *	11: *	
12: *	13: *	14: *	15: *	
_____________________________

That was not a match.


...


_____________________________

0: *	1: *	2: *	3: *	
4: *	5: *	6: *	7: *	
8: *	9: *	10: *	11: *	
12: *	13: *	14: *	15: *	
_____________________________

Pick the first card:
2

_____________________________

0: *	1: *	2: D	3: *	
4: *	5: *	6: *	7: *	
8: *	9: *	10: *	11: *	
12: *	13: *	14: *	15: *	
_____________________________

Pick the second card:
3
You got a match!


...


_____________________________

0: A	1: B	2: D	3: D	
4: D	5: C	6: B	7: A	
8: C	9: *	10: B	11: C	
12: B	13: C	14: D	15: *	
_____________________________

Pick the first card:
15

_____________________________

0: A	1: B	2: D	3: D	
4: D	5: C	6: B	7: A	
8: C	9: *	10: B	11: C	
12: B	13: C	14: D	15: A	
_____________________________

Pick the second card:
9
You got a match!
The game took 28 flips.
```