import java.util.Scanner;

public class runGame
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        System.out.println("Select a difficulty level:");
        System.out.println("[1] Beginner");
        System.out.println("[2] Intermediate");
        System.out.println("[3] Expert");
        System.out.println("[4] Custom");
        System.out.println("Input the difficulty number you desire.");

        int difficulty = input.nextInt();
        input.nextLine();

        Minesweeper game;

        /*
        if(1 <= difficulty && difficulty <= 3){
            game = new Minesweeper(difficulty);
        }
        else if(difficulty == 4){
            System.out.println("Input the board dimensions you desire in the form of [width]x[height] without the brakets.");
            String dims = input.nextLine();
            int col = input.nextInt();
            int row = input.nextInt();

            System.out.println("Input the total number of mines you want in your board.");
            int mineN = input.nextInt();
            game = new Minesweeper(row, col, mineN);
        }
        */

        game = new Minesweeper(difficulty);

        game.generateMines();
        game.generateNumBoard();
        game.generatefhrBoard();

        while(!game.checkGameOver() && !game.checkGameClear()){
            System.out.println(game);
            System.out.println("Set of actions you can do:");
            System.out.println("[1] Reveal a cell. Input: R x y");
            System.out.println("[2] Flag a cell. Input: F x y");
            System.out.println("[3] End the game. Input: 3");
            String command = input.next();
            if(command.equals("3")){
                break;
            }
            String action = command.substring(0, 1);
            int col = Integer.parseInt(input.next());
            int row = Integer.parseInt(input.next());
            if(col < 1 || col > game.getCol() || row < 1 || row > game.getRow()){
                System.out.println("Invalid Input");
                continue;
            }
            if(action.equals("R")){
                game.revealCell(row - 1, col - 1);
                game.revealChunk();
            }
            else if(action.equals("F")){
                game.flagCell(row - 1, col - 1);
            }
        }
        System.out.println(game);

        if(game.checkGameClear()){
            System.out.println("Game CLeared!");
        }
        else if(game.checkGameOver()){
            System.out.println("Game Over");
        }

        input.close();
    }
}