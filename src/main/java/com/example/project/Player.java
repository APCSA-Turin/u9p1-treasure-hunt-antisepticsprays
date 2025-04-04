package com.example.project;

//DO NOT DELETE ANY METHODS BELOW
public class Player extends Sprite{
    private int treasureCount;
    private int numLives;
    private boolean win;

    // constructor
    public Player(int x, int y) { //set treasureCount = 0 and numLives = 2 
        super(x, y);
        treasureCount = 0;
        numLives = 2;
        win = false;
    }

    // getter methods
    public int getTreasureCount(){return treasureCount;}
    public int getLives(){return numLives;}
    public boolean getWin(){return win;}
  
    // returns the row and column of player in a formatted way
    public String getRowCol(int size){
        return "Player:" + super.getRowCol(size);
    }

    // return the coordinates of the player in a formatted way
    public String getCoords(){
        return "Player:" + super.getCoords();
    }

    //move method should override parent class, sprite
    public void move(String direction) { //move the (x,y) coordinates of the player
        // based off wasd, it sets the sprite to a new coodinate on the grid 

        // w moves the sprite up one unit
        if (direction.equals("w")) {
            setY(getY() + 1);
        }
        // a moves the sprite left one unit
        if (direction.equals("a")) {
            setX(getX() - 1);
        }
        // s moves the sprite down one unit
        if (direction.equals("s")) {
            setY(getY() - 1);
        }
        // d moves the sprite right one unit
        if (direction.equals("d")) {
            setX(getX() + 1);
        }
    }


    public void interact(int size, String direction, int numTreasures, Object obj) { // interact with an object in the position you are moving to 
    //numTreasures is the total treasures at the beginning of the game
        // isValid is put in place to make sure the sprite in is bounds to begin with
        if (isValid(size, direction) == true) {
            // player interact with enemy --> one life lost
            if (obj instanceof Enemy) {
                numLives--;
            // player interacts with treasure --> number of treasures go up by one
            // note: has to make sure it's not a trophy object because trophy is a child of treasure
            }else if (obj instanceof Treasure && !(obj instanceof Trophy)) {
                treasureCount++;
            // player interacts with trophy -- > win is only set to true if all the treasures are collected
            }else if(obj instanceof Trophy && treasureCount == numTreasures) {
                win = true;
            }
        }
    }


    public boolean isValid(int size, String direction){ //check grid boundaries
        // boundaries on the left
        if (getX() == 0 && direction.equals("a")) {
            return false;
        // boundaries on the right
        }else if (getX() == (size - 1) && direction.equals("d")) {
            return false;
        // boundaries on the top
        }else if (getY() == (size - 1) & direction.equals("w")) {
            return false;
        // boundaries on the bottom
        }else if (getY() == 0 && direction.equals("s")) {
            return false;
        }else{
            return true;
        }
    }
}