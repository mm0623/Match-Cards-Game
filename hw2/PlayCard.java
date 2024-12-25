package hw2;

public class PlayCard {

//Bad Game AI

  /* playRandom(): 
  Plays by flipping random legal cards with equal probability independent of past.
  Plays until game over and returns the number of flips performed. 
  */
  public  static  int  playRandom(MatchCardGame g){

    while(!g.gameOver()){
  
      //flip 2 random legal cards between [0,n-1]
      while(!g.flip((int)(Math.random()*g.n))){};
      while(!g.flip((int)(Math.random()*g.n))){};

      //flip the 2 cards back if they don't match
      if (g.wasMatch()==false)
      {
        g.flipMismatch();
      }
    }
    return g.getFlips();
  }

//Good Game AI

  private static final char DEFAULT = Character.MIN_VALUE;//empty char ''

  /* playGood():
  Plays with perfect memory of past flipped cards.
  Will always score 2n or better.
  */
  public  static  int  playGood(MatchCardGame g){
    char[] memory = new char[g.n];//log played cards
    boolean[] status = new boolean[g.n];//log if cards are faced up or down (true = face-up, false = face down)
    int[] prevTwo = {-1,-1};//index of last two played cards ([0]=last card, [1]=second last card); initialize with placeholders

    //play until game is over
    while(!g.gameOver()){

      //if even flip count (next card is odd)
      if (g.getFlips()%2 == 0){
        //play a known face-down matching pair
        if (!playGoodPair(g,memory,status, prevTwo)){
          //if there's no face-down match, play a random unflipped face-down card
          playRandomOnce(g,memory,status, prevTwo);
        }
      }

      //if odd flip count (next card is even)
      if (g.getFlips()%2 == 1){
        //play a known face-down matching card
        if (!playGoodMatch(g,memory,status, prevTwo)){
          //if there's no face-down match, play a random unflipped face-down card
          playRandomOnce(g,memory,status, prevTwo);
        }
      }
      
      //if two cards are flipped and they don't match
      if(g.getFlips()%2 == 0 && !g.wasMatch()){
        //flip them back down
        g.flipMismatch();
        //update card status
        status[prevTwo[1]]=false;
        status[prevTwo[0]]=false;
      }
    }
    return g.getFlips();
  }

  /* playGoodPair(): 
  A helper function for playGood()
  Plays a pair of cards that match in memory, if any, and returns true.
  If not, return false. 
  */
  private static boolean playGoodPair(MatchCardGame g, char[] memory, boolean[] status, int[] prevTwo){
    for(int i =0;i<g.n;i++){

      //skip iteration if card i haven't been played (memory is empty) or it's faced up
      if (memory[i]==DEFAULT ||status[i]==true){
        continue;
      }

      //if i and j are not the same int
      //and card j haven't been played (memory is empty)
      //and card j is face-down
      //and cards i and j match in memory
      for(int j=0; j<g.n;j++){
        if(i!=j && memory[j]!=DEFAULT && status[j]==false && memory[i]==memory[j]){
          //play the matching pair
          g.flip(i);
          g.flip(j);

          //update memory
          updateMemory(g,memory,status,prevTwo,i);
          updateMemory(g,memory,status,prevTwo,j);
          return true;
        }
      }
    }
    return false;
  }

  /* playRandomOnce():
  A helper function for playGood()
  Plays a random legal card
  */
  private static void playRandomOnce(MatchCardGame g, char[] memory, boolean[] status, int[] prevTwo){
    int rand = -1;//initalize with placeholder
    
    //keep generating a random int between [0,n) until reaching a face-down and unplayed card
    while(rand==-1 ||(memory[rand]!=DEFAULT && status[rand]==true)){
      rand = (int)(Math.random()*g.n);
    }
    
    //flip the random legal card
    g.flip(rand);
    //update memory
    updateMemory(g, memory,status, prevTwo, rand);
  }

  /* updateMemory():
  A helper function for playGood()
  Update the last 2 cards played.
  Update the flipped status of the card at index i.
  Add the card at index i to memory.
  */
  private static void updateMemory(MatchCardGame g, char[] memory, boolean[] status, int[] prevTwo, int i){
    prevTwo[1]=prevTwo[0];//update second last card played
    prevTwo[0]=i;//update lastest card played
    status[i] = true;//making card i face up
    memory[i] = g.previousFlipIdentity();//update the memory
  }

  /* playGoodMatch():
  If the last flipped card matches a face-down card in memory, flip that card and return true.
  If not, return false. 
  */
  private static boolean playGoodMatch(MatchCardGame g, char[] memory, boolean[] status, int[] prevTwo){
    //linear search through memory
    for(int i=0;i<g.n;i++){
      if (memory[i] == memory[prevTwo[0]] && status[i]==false){
        g.flip(i);
        updateMemory(g,memory,status,prevTwo,i);
        return true;
      }
    }
    return false;
  }

//Monte Carlo Method

  /* randomMC():
  Plays shuffled MatchCardGames of size 32 a total of N times using playRandom method.
  Returns the average number of flips to complete the games.
  */
   public  static  double  randomMC(int N){
    
    double totalFlips = 0.0;
    double avgFlips = 0.0;
    MatchCardGame g = new MatchCardGame(32); //initialize a game with placeholder

    
    for (int i=0; i<N; i++){
      //play a game of size 32 and update the total amount of flips used 
      g = new MatchCardGame(32);
      g.shuffleCards();
      totalFlips += playRandom(g);
    }

    //calculate and return the average flips
    avgFlips = totalFlips/N;
    return avgFlips;
  }

  /* goodMC():
  Plays shuffled MatchCardGames of size 32 a total of N times using playGood method.
  Returns the average number of flips to complete the games.
  */
  public  static  double  goodMC(int N){
    double totalFlips = 0.0;
    double avgFlips = 0.0;
    MatchCardGame g = new MatchCardGame(32); //initialize a game with placeholder
        
    for (int i=0; i<N; i++){
      //play a game of size 32 and update the total amount of flips used
      g = new MatchCardGame(32);
      g.shuffleCards();
      totalFlips += playGood(g);
    }

    //calculate and return the average flips
    avgFlips = totalFlips/N;
    return avgFlips;
  }

  public static void comparison_test(int n){


    //Using the AIs
    int count;
    MatchCardGame g2 = new MatchCardGame(n);
    g2.shuffleCards();
    count = playRandom(g2);
    System.out.println("The bad AI took " + count + " flips.");
    
    MatchCardGame g3 = new MatchCardGame(n);
    g3.shuffleCards();
    count = playGood(g3);
    System.out.println("The good AI took " + count + " flips.");
    
    //Using MCs
    int N = 1000;
    System.out.println("The bad AI took " + randomMC(N) + " flips on average.");
    System.out.println("The good AI took " + goodMC(N) + " flips on average.");
  }

  public static void singlePlayerGame(int n) {

    //set up reader to take inputs
    java.util.Scanner reader = new java.util.Scanner (System.in);

    //initialize game
    MatchCardGame g1 = new MatchCardGame(n);
    g1.shuffleCards();

    while(!g1.gameOver()) {
      //print board status
      System.out.println(g1.boardToString());
      
      //ask for a card to flip until we get a valid one
      System.out.println("Pick the first card:");
      while(!g1.flip(reader.nextInt())) {}
      
      //print board status
      System.out.println(g1.boardToString());
      
      //ask for a card to flip until we get a valid one
      System.out.println("Pick the second card:");
      while(!g1.flip(reader.nextInt())) {} //flip card 15
      // previousflipidentity -> carvalues[15]

      //say whether the 2 cards were a match
      if(g1.wasMatch()) {
        System.out.println("You got a match!");
      } else {
        //print board to show mismatched cards
        System.out.println(g1.boardToString());		
        System.out.println("That was not a match.");
        //flip back the mismatched cards
        g1.flipMismatch();
      }
    }
    reader.close();

    //Report the score
    System.out.println("The game took " + g1.getFlips() + " flips.");
  }

  public static void main(String[] args) {

    //set up reader to take inputs
    java.util.Scanner main_reader = new java.util.Scanner (System.in);

    //ask for a valid game size
    System.out.println("Specify the game size (at least 2 cards):");
    int n = main_reader.nextInt();

    // play game or compare AIs
    System.out.println("Enter \'PLAY\' to try the game yourself or enter \'COMPARE\'  see how the two AIs compare:");
    String choice = main_reader.next();
    while(!choice.equals("PLAY") && !choice.equals("COMPARE"))
    {
      System.out.println("Please enter a valid option");
      choice = main_reader.next();
    }

    if (choice.equals("PLAY")) {singlePlayerGame(n);}
    else if (choice.equals("COMPARE")) {comparison_test(n);}

    main_reader.close();
  }
}