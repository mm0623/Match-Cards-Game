package hw2;

public class MatchCardGame {
  public final int n;//size of the game
  private final char[] CardValues;//array containing the char values of n cards of the game.
  
  //custom fields: 
  private boolean[] showCard;//log whether cards are facing up or down, false (face-down) by default
  private int flips;//number of total flips, useful for getFlips() 
  private int firstcard;//index of the first flipped card
  private int secondcard;//index of the second flipped card
  private int prevcard;//index of the last flipped card, useful for previousFlipIdentity()

  /* MatchCardGame(): constructor
  Initializes a card game with a total of n cards without shuffling.
  (Given that n is a multiple of 4)
  */
  public MatchCardGame(int n) {
    
    this.n = n;//size of the game
    this.CardValues = new char[n];//char array of n cards from the game
    this.showCard = new boolean[n];//log whether cards are face-up (true) or face-down (false)
    this.flips = 0;//log number of flips in game
    int factor = 4;//initialize the factor 4

    //initialize variables for storing the first, second, and last played cards with placeholders
    this.firstcard = 0;
    this.secondcard = 0;
    this.prevcard = -1;

    //assign char values starting with 'A' to 4 values at a time
    for(int i = 0; i<n/factor; i++){//A,B,C...
      for(int k = 0; k<factor; k++){//add each char 4 times
        this.CardValues[k+i*factor] = (char)('A'+i); 
      }
    }
  }

  /* shuffleCards():
  Shuffles an array of n elements.
   */ 
  public void shuffleCards(){
    if(this.flips == 0){//before any card is flipped

      int random = (int)((Math.random())*this.n);//random int; initialize with placeholder
      char dummy = 'a';

        for (int i=0;i<n;i++){
          //geneate a random int between [0,n-1]
          random = (int)((Math.random())*this.n);
          
          //switch each card with the card with the random index
          dummy = this.CardValues[i];
          this.CardValues[i] = this.CardValues[random];
          this.CardValues[random] = dummy;
        }
    }
  }

  /* gameOver():
  Returns true if all cards have been matched.
  If not, returns false.
  */
  public boolean gameOver(){
    int faceup = 0;//number of cards facing up

    //iterate showCard and check if all cards are face up
    for (boolean i:this.showCard){
      if (i==true){
        faceup++;
      }
    }

    //if the number of face-up cards matches the game size, return true
    if (faceup==this.n){
      return true;
    }

    return false;
  }
  
  /* flip():
  Flips a legal card at index i and update the respective MatchCardGame fields, then, return true.
  If card is not legal, return false.
  */
  public boolean flip(int i) {   
    //return false if card index i is not within range or card i is already face-up
    if (i<0|| i>=n ||this.showCard[i] == true){
      return false;
    }

    //even count (flip first card)
    if (this.flips%2 == 0){
      this.firstcard = i;
    }

    //odd count (flip second card)
    else if (this.flips%2 == 1){
      this.secondcard = i;
    }

    this.prevcard = i;//update last flipped card
    this.flips++;//update flip count
    this.showCard[i] = true;//update this card as face-up

    return true;
  }
 
  /* wasMatch():
  Returns true if the last played pair was a match.
  If not, returns false.
  */
  public boolean wasMatch(){
    if(this.CardValues[this.firstcard] == this.CardValues[this.secondcard]){
      return true;
    }

	  return false;
  }
  
  /* previousFlipIdentity():
  Returns the char of the last flipped card.
  */
  public char previousFlipIdentity(){
      return this.CardValues[this.prevcard];
  }

  /* flipMismatch():
  Reverts a mismatched pair to face-down position.
  */
  public void flipMismatch(){
    if(this.wasMatch()==false){
      //update the showCard status of the mismatched cards to face-down
      this.showCard[this.firstcard]=false;
      this.showCard[this.secondcard]=false;
    }
  }
  
  /* boardToString():
  Returns the state of the board to as a String representation.
  Each gameboard is bounded by lines and each card corresponds to an index value.
  Unknown cards are represented by '*' and flipped cards display the card value.
  Matched cards stay face-up.
  */
  public String boardToString(){

    String s = "\n_____________________________\n\n";//initialize string s with a separation line for start of game board

    for(int i = 0; i<this.CardValues.length;i++){

      //if card is face-up, display the index value i and the card value
      if(this.showCard[i]==true){
        s += Integer.toString(i)+": "+this.CardValues[i]+"\t";
      }

      //if card is face down, display the index value i and '*'
      else if(this.showCard[i]==false){
        s += Integer.toString(i)+": *\t";
      }

      //start new line after every 4 cards
      if (i%4==3){
        s += "\n";
      }
    }

    s += "_____________________________\n"; //separation line for end of gameboard

    return s;
  }

  /* getFlips():
  returns total number of card flips performed so far.
  */
  public int getFlips(){
    return this.flips;
  }
}