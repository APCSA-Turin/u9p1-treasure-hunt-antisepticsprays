package com.example.project;
import java.util.Scanner;

public class Game {
    private Grid grid;
    private Player player;
    private Enemy[] enemies;
    private Treasure[] treasures;
    private Trophy trophy;
    private int size;

    public Game(int size) {
        initialize();
        play();
    }

    public static void clearScreen() {
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true; // if the player wants to play again

        while (playAgain) {
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clearScreen(); // clears the screen every turn
            grid.display(); // displays the current grid
            System.out.println("Treasure: " + player.getTreasureCount()); // treasure collected
            System.out.println("Lives: " + player.getLives()); // remaining lives

            String input = scanner.nextLine().toLowerCase(); // takes user input for movement

            // movement : w (up), a (left), s (down), d (right)
            if (input.equals("w") || input.equals("a") || input.equals("s") || input.equals("d")) {
                Sprite target = new Sprite(0, 0); // placeholder

                // checks if the movement is valid 
                if (player.isValid(size, input)) {
                    int nextX = player.getX();
                    int nextY = player.getY();

                    // updates next position based on input
                    if (input.equals("w")) nextY++;
                    else if (input.equals("s")) nextY--;
                    else if (input.equals("a")) nextX--;
                    else if (input.equals("d")) nextX++;

                    // gets the sprite at the next position
                    target = grid.getGrid()[size - 1 - nextY][nextX];
                }

                // interacts
                player.interact(size, input, treasures.length, target);

                // prevents the player from reaching the trophy too early
                if (!(target instanceof Trophy) || player.getTreasureCount() == treasures.length) {
                    if (player.isValid(size, input)) {
                        player.move(input); // updates player's coordinates
                    }
                }

                // player in new position, dot in old position
                grid.placeSprite(player, input);
            }

            // win or lose condition
            if (player.getLives() == 0 || player.getWin()) {
                if (player.getWin()) {
                    grid.win(); // winning screen
                } else {
                    grid.gameover(); // losing screen
                }

                System.out.println("Try again? y/n");
                playAgain = scanner.nextLine().toLowerCase().equals("y");

                if (playAgain) {
                    initialize(); // reinitialize the game if played again
                }
            }
        }
    }

    public void initialize() {
        Scanner scan = new Scanner(System.in);

        // difficulty level
        System.out.println("level:\n1. easy\n2. medium\n3. hard");
        int input = scan.nextInt();
        scan.nextLine(); // consume newline

        // 3 sets of difficulties
        if (input == 1) {
            size = 5;
            enemies = new Enemy[1];
            treasures = new Treasure[1];
        } else if (input == 2) {
            size = 8;
            enemies = new Enemy[2];
            treasures = new Treasure[2];
        } else if (input == 3) {
            size = 12;
            enemies = new Enemy[4];
            treasures = new Treasure[3];
        }

        grid = new Grid(size); // create specific sized grid
        player = new Player(0, 0); // create player at top left corner
        grid.placeSprite(player); // place player on the grid

        trophy = new Trophy(size - 1, size - 1); // trophy is always at bottom right
        grid.placeSprite(trophy);

        // randomly generated enemies
        for (int i = 0; i < enemies.length; i++) {
            int randX = 0;
            int randY = 0;
            // keep generating until an empty spot is found
            while (!(grid.getGrid()[size - 1 - randY][randX] instanceof Dot)) {
                randX = (int) (Math.random() * size);
                randY = (int) (Math.random() * size);
            }
            Enemy newEnemy = new Enemy(randX, randY);
            grid.placeSprite(newEnemy);
            enemies[i] = newEnemy;
        }

        // randomly generated treasures
        for (int i = 0; i < treasures.length; i++) {
            int randX = 0;
            int randY = 0;
            // keep generating until an empty spot is found
            while (!(grid.getGrid()[size - 1 - randY][randX] instanceof Dot)) {
                randX = (int) (Math.random() * size);
                randY = (int) (Math.random() * size);
            }
            Treasure newTreasure = new Treasure(randX, randY);
            grid.placeSprite(newTreasure);
            treasures[i] = newTreasure;
        }
    }
    public static void main(String[] args) {
        Game g = new Game(10);
    }
}