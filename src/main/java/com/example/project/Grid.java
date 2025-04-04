package com.example.project;


//DO NOT DELETE ANY METHODS BELOW
public class Grid{
    private Sprite[][] grid;
    public int size;
    
    public Grid(int size) { //initialize and create a grid with all DOT objects
        grid = new Sprite[size][size]; // grid created with a given size
        this.size = size; 
        //initializes each and every column and grid as a Dot object
        for (int row = 0; row < size; row++) {
            //column of the grid
            for (int column = 0; column < size; column++) {
                grid[row][column] = new Dot(row, column);
            }
        }
    }

    // getter methods
    public Sprite[][] getGrid(){return grid;}
    public int getSize(){return size;}
    public Sprite getSprite(int y, int x){return grid[y][x];}

    // places a sprite at a new spot in a grid
    public void placeSprite(Sprite s){ //place sprite in new spot
        //row position is inverted
        grid[(size - 1) - s.getY()][s.getX()] = s;
    }

    public void placeSprite(Sprite s, String direction) { 
        // moves sprite while replacing the position it moved from with a Dot object
        Dot trail = new Dot(0, 0);
        placeSprite(s);
    
        // y-axis is updated based off direction
        if (direction.equals("w")) {
            trail.setY(s.getY() - 1);
        } else if (direction.equals("s")) {
            trail.setY(s.getY() + 1);
        } else {
            // no movement
            trail.setY(s.getY());
        }
    
        // x-axis is updated based off direction
        if (direction.equals("a")) {
            trail.setX(s.getX() + 1);
        } else if (direction.equals("d")) {
            trail.setX(s.getX() - 1);
        } else {
            // no movement
            trail.setX(s.getX());
        }
    
        // places dot object here at the old position
        placeSprite(trail);
    }

    // game over screen
    public void display() { //print out the current grid to the screen 
        for(int row = 0; row < grid.length; row++){
            for(int column = 0; column < grid[0].length; column++){
                if(grid[row][column] instanceof Dot){
                    System.out.print("⬜");
                } else if(grid[row][column] instanceof Player){
                    System.out.print("🦄");
                } else if (grid[row][column] instanceof Enemy){
                    System.out.print("🦂");
                }else if(grid[row][column] instanceof Treasure && !(grid[row][column] instanceof Trophy)){
                    System.out.print("🌈");
                } else if(grid[row][column] instanceof Trophy){
                    System.out.print("🏆");
                }
            }
            System.out.println();
        }
    }
    
    // winning screen
    public void gameover(){
        String gameOverGrid = "";
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] instanceof Dot) {
                    gameOverGrid += "🦂";
                }else if (grid[row][column] instanceof Player) {
                    gameOverGrid += "🦄";
                }
            }
            gameOverGrid += "\n";
        }
        System.out.println(gameOverGrid);
    }

    // losing screen
    public void win(){ //use this method to display a win 
        String winningGrid = "";
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] instanceof Dot) {
                    winningGrid += "🌈";
                }else if (grid[row][column] instanceof Player) {
                    winningGrid += "🦄";
                }
            }
            winningGrid += "\n";
        }
        System.out.println(winningGrid);
    }
}