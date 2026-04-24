import java.util.Random; // we want to use random class
import java.util.Scanner;
public class logic {
static int score = 0; // global vairable score
    static int SIZE = 4;
     private static int[][] board = new int[SIZE][SIZE]; //array of board

    static void printBoard() { //method named printboard
        System.out.println("Score :"   +score); // board should be above the board
        for (int i = 0; i < SIZE; i++) { //access rows
            for (int j = 0; j < SIZE; j++) { //access columns
                System.out.print(board[i][j] + " ");
            }
            System.out.println(); // move to next row

        }

    }

    static void moveleft(){ //shifting left (method)
        for(int i =0; i<SIZE ; i++){ // we need to go row by row thats why it outside the steps.
            // it takes one row then go steps when steps are done it takes another row and goes on

            // step 1:compress
        int []newRow = new int [SIZE]; //  1-D array =[0,0,0,0]
        int index= 0;
        for(int j=0; j<SIZE ; j++ ){ //column
            if(board[i][j]!=0){
                newRow[index]=board[i][j];

                index++; //index=0 ,1,2..
            }
           
        }
     // Step 2: Merge
     for (int j = 0; j < SIZE - 1; j++) {
        if (newRow[j] != 0 && newRow[j] == newRow[j + 1]) {
            newRow[j] *= 2;
            score += newRow[j];
            newRow[j + 1] = 0;
        }
    }
     // Step 3: Compress again
     int[] finalRow = new int[SIZE]; // 1-D array same as newRow 
     index = 0;

     for (int j = 0; j < SIZE; j++) {
         if (newRow[j] != 0) {
             finalRow[index] = newRow[j];
             index++;
         }
     }

  
for (int j=0; j<SIZE ; j++){ //copy to board
    board[i][j]= finalRow[j]; //row is the same j is the column whcih will traverse
}
    }
}

    static void reverseRow(int i){
    for(int j =0; j<SIZE/2 ; j++){
        int temp = board[i][j];
        board[i][j]= board[i][SIZE-1-j];
        board[i][SIZE-1-j]=temp;
    }
    }
static void  moveRight(){
    for (int i =0; i<SIZE ; i++){
        reverseRow(i);
    }
    moveleft();
    for (int i = 0; i < SIZE; i++) {
        reverseRow(i);
    }
}



static void transpose(){ // helper method so we wont call it in main method
    for(int i =0; i<SIZE ; i++){
        for (int j = i+1 ; j<SIZE ; j++){
            int temp = board[i][j];
            board[i][j]= board[j][i];
            board[j][i]= temp;
        }
    }
}

 static void moveUp(){
    transpose();
    moveleft();
    transpose();
 }

 static void moveDown(){
    transpose();
    moveRight();
    transpose();
}
  
static void addRandomTile(){
    Random rand = new Random();
    int rows = rand.nextInt(SIZE); // not needed
    int col = rand.nextInt(SIZE);//Because you are generating them again inside the loop anyway.

    if(isFull()) return; //stop conditon (when board is full)

    while(true){
        rows=rand.nextInt(SIZE);
        col=rand.nextInt(SIZE);

if (board[rows][col]==0){

    int randomnumber = rand.nextInt(10);

    if (randomnumber==0){
        board[rows][col]=4; // 10% chance of 4 appearing on random place 
    }
    else{
        board[rows][col]=2; // 90% chance of 2
    }
break;
}
    }
    
}
static  boolean isFull(){ // when board is full
for(int i=0 ; i<SIZE ; i++){
    for(int j=0 ; j<SIZE ; j++){
        if(board[i][j]==0){
             return false;
        }
        
    }
     
}
  return true;
}

static int[][] copyBoard(){ 
    int [][]newBoard  = new int [SIZE][SIZE];
    for(int i =0; i<SIZE ; i++){
        for(int j=0; j<SIZE ; j++){
            newBoard[i][j]= board [i][j]; //board[i][j]=old board

        }
    }
    return newBoard;
}

static boolean isSame(int[][]b1, int [][]b2){
     for(int i =0; i<SIZE ; i++){
        for(int j=0; j<SIZE ; j++){
            if(b1[i][j]!=b2 [i][j]){
                return false;
            }
}
     }
     return true;
    }

static void makeMove(String direction){
int [][] oldBoard = copyBoard(); // save the board before the game
switch (direction){
    case "left" :
    moveleft();
    break;

    case "right":
    moveRight();
    break ;

    case "up":
    moveUp();
    break;

    case "down":
    moveDown();
    break;

}
if(!isSame(board , oldBoard)){
    addRandomTile(); 
}
printBoard(); //show updated board
}

static boolean hasWon(){
    for(int i =0; i<SIZE ; i++){
        for(int j=0; j<SIZE ; j++){
            if(board[i][j]==2048){
return true;
            }

}
    }
    return false;
}

static boolean canMerge(){
    
for(int i=0 ; i<SIZE ;i++){

    for (int j=0; j<SIZE ; j++){
        
        
            if (i < SIZE - 1 && board[i][j] == board[i + 1][j]){
            return true;
          }
           if(j < SIZE - 1 && board[i][j] == board[i][j+1]){
                return true;
            }

    }
}
return false;
      
    
}


static boolean gameOver(){

    if(!isFull()){
        return false; 

    }
  if(canMerge()){
    return false;
  }
  return true;
}


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // take input from user what shift it wants

        addRandomTile(); // calling it two times for two tiles at start
        addRandomTile();
        printBoard(); // to see the board
 
while (true) { //taking input for makeMove
   char move = sc.next().charAt(0);
   String direction = " ";
   switch(move){

    case 'w': direction= "up";
    break ;

    case 's': direction ="down";
    break ;

    case 'a' : direction= "left";
    break ;

    case 'd': direction= "right";
    break ;
   }

   makeMove(direction); // this is where everyting happens

   if(hasWon()){
    System.out.println("YOU WON!");
    break; // to stop being in loop for (if) 
   }

   if(gameOver()){
    System.out.println("GAME OVER !");
    break ;
   }
    
}
}

}



          
