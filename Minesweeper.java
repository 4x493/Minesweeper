public class Minesweeper
{
    private int row;
    private int col;
    private int mineN;
    private int difficulty;
    private Boolean[][] mines;
    private int[][] numBoard;
    private String[][] fhrBoard;

    public Minesweeper(int difficulty){
        if(difficulty == 1){
            row = 9;
            col = 9;
            mineN = 10;
        }
        else if(difficulty == 2){
            row = 16;
            col = 16;
            mineN = 40;
        }
        else if(difficulty == 3){
            row = 16;
            col = 30;
            mineN = 99;
        }
        mines = new Boolean[row][col];
        numBoard = new int[row][col];
        fhrBoard = new String[row][col];
    }

    public Minesweeper(int width, int height, int mineN){
        row = height;
        col = width;
        this.mineN = mineN;
        mines = new Boolean[row][col];
        numBoard = new int[row][col];
        fhrBoard = new String[row][col];
    }

    public void generateMines(){
        for(int i=0; i<mineN; i++){
            int row = Math.random() * this.row + 1;
            int col = Math.random() * this.col + 1;
            while(mines[row][col]){
                row = Math.random() * this.row + 1;
                col = Math.random() * this.col + 1;
            }
            mines[row][col] = true;
        }
    }

    public void generateNumBoard(){
        for(int r=0; r<row; r++){
            for(int c=0; c<col; c++){
                for(int rs=r-1; rs<=r+1; rs++){
                    for(int cs=c-1; cs<=c+1; cs++){
                        if(0 <= rs && rs < row && 0 <= cs && cs < col){
                            if(mines[rs][cs]){
                                numBoard[r][c] += 1;
                            }
                        }
                    }
                }
            }
        }
    }

    public void generatefhrBoard(){
        for(String[] r : fhrBoard){
            for(String fhr : r){
                fhr = "H";
            }
        }
    }

    public void revealCell(int r, int c){
        if(fhrBoard[r][c] == "H"){
            fhrBoard[r][c] = "R";
        }
        else if(fhrBoard[r][c] == "R" && numBoard[r][c] != 0){
            int flagCount;
            for(int rs=r-1; rs<=r+1; rs++){
                for(int cs=c-1; cs<=c+1; cs++){
                    if(0 <= rs && rs < row && 0 <= cs && cs < col){
                        if(fhrBoard[rs][cs] == "F"){
                            flagCount += 1;
                        }
                    }
                }
            }
            if(flagCount == numBoard[r][c]){
                for(int rs=r-1; rs<=r+1; rs++){
                    for(int cs=c-1; cs<=c+1; cs++){
                        if(0 <= rs && rs < row && 0 <= cs && cs < col){
                            fhrBoard[rs][cs] = "R";
                        }
                    }
                }
            }
        }
    }

    public Boolean checkChunkReveal(){
        for(int r=0; r<row; r++){
            for(int c=0; c<col; c++){
                if(fhrBoard[r][c] == "R" && numBoard[r][c] == 0){
                    for(int rs=r-1; rs<=r+1; rs++){
                        for(int cs=c-1; cs<=c+1; cs++){
                            if(0 <= rs && rs < row && 0 <= cs && cs < col){
                                if(fhrBoard[rs][cs] == "H"){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void revealChunk(){
        while(checkChunkReveal()){
            for(int r=0; r<row; r++){
                for(int c=0; c<col; c++){
                    if(fhrBoard[r][c] == "R" && numBoard[r][c] == 0){
                        for(int rs=r-1; rs<=r+1; rs++){
                            for(int cs=c-1; cs<=c+1; cs++){
                                if(0 <= rs && rs < row && 0 <= cs && cs < col){
                                    fhrBoard[rs][cs] = "R";
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void flagCell(int r, int c){
        if(fhrBoard[r][c] == "H"){
            fhrBoard[r][c] = "F";
        }
    }

    public Boolean checkGameOver(){
        for(int r=0; r<row; r++){
            for(int c=0; c<col; c++){
                if(fhrBoard[r][c] == "R" && mines[r][c]){
                    return true;
                }
            }
        }
        return false;
    }

    public String toString(){
        Strinng board;
        board += "  ";
        for(int c=0; c<col; c++){
            board += "  " + c + " ";
        }
        board += "\n";
        for(int r=0; r<row; r++){
            board += "  ";
            for(int c=0; c<col; c++){
                board += "+---"
            }
            board += "+\n";
            board += r + " ";
            for(int c=0; c<col; c++){
                if(fhrBoard[r][c] == "R"){
                    if(numBoard[r][c] == 0){
                        board += "|   ";
                    }
                    else{
                        board += "| " + numBoard[r][c] + " ";
                    }
                }
                else if(fhrBoard[r][c] == "H"){
                    board += "| : ";
                }
                else{
                    board += "| F ";
                }
            }
            board += "|\n";
            Board += "  ";
            for(int c=0; c<col; c++){
                board += "+---"
            }
            board += "+\n";
        }
    }
}

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
        Minesweeper game;

        if(1 <= difficulty && difficulty <= 3){
            game = new Minesweeper(difficulty);
        }
        else if(difficulty == 4){
            System.out.println("Input the board dimensions you desire in the form of [width]x[height] without the brakets.");
            String dims = input.nextLine();
            int col = (int)(dims.substring(0, dims.indexOf("x")));
            int row = (int)(dims.substring(dims.indexOf("x") + 1));

            System.out.println("Input the total number of mines you want in your board.");
            int mineN = input.nextInt();
            game = new Minesweeper(row, col, mineN);
        }

        game.generateMines();
        game.generateNumBoard();
        game.generatefhrBoard();

        while(game.checkGameOver == false){
            System.out.println(game);
            System.out.println("Set of actions you can do:");
            System.out.println("[1] Reveal a cell. Input: R, x, y");
            System.out.println("[2] Flag a cell. Input: F, x, y");
            System.out.println("[3] End the game. Input: 3");
            String command = input.nextLine();
            if(command == "3"){
                break;
            }
            String action = command.substring(0, 1);
            int row = command.substring(command.indexOf(",", command.indexOf(",") + 1) + 1);
            int col = command.substring(command.indexOf(",") + 1, command.indexOf(",", command.indexOf(",") + 1));
            if(action == "R"){
                game.revealCell(row, col);
                game.revealChunk();
            }
            else if(action == "F"){
                game.flagCell(row, col);
            }
            System.out.println(game);
        }
        System.out.println("Game Over");
    }
}