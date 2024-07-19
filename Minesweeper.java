public class Minesweeper
{
    private int row;
    private int col;
    private int mineN;
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

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public void generateMines(){
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                mines[i][j] = false;
            }
        }
        for(int i=0; i<mineN; i++){
            int row = (int)(Math.random() * this.row);
            int col = (int)(Math.random() * this.col);
            while(mines[row][col]){
                row = (int)(Math.random() * this.row);
                col = (int)(Math.random() * this.col);
            }
            mines[row][col] = true;
        }
    }

    public void generateNumBoard(){
        for(int r=0; r<row; r++){
            for(int c=0; c<col; c++){
                for(int rs=r-1; rs<=r+1; rs++){
                    if(0<= rs && rs < row){
                        for(int cs=c-1; cs<=c+1; cs++){
                            if(0 <= cs && cs < col){
                                if(mines[rs][cs]){
                                    numBoard[r][c] += 1;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void generatefhrBoard(){
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                fhrBoard[i][j] = "H";
            }
        }
    }

    public void revealCell(int r, int c){
        if(fhrBoard[r][c].equals("H")){
            fhrBoard[r][c] = "R";
        }
        else if(fhrBoard[r][c].equals("R") && numBoard[r][c] != 0){
            int flagCount = 0;
            for(int rs=r-1; rs<=r+1; rs++){
                if(0 <= rs && rs < row){
                    for(int cs=c-1; cs<=c+1; cs++){
                        if(0 <= cs && cs < col){
                            if(fhrBoard[rs][cs].equals("F")){
                                flagCount += 1;
                            }
                        }
                    }
                }
            }
            if(flagCount == numBoard[r][c]){
                for(int rs=r-1; rs<=r+1; rs++){
                    if(0 <= rs && rs < row){
                        for(int cs=c-1; cs<=c+1; cs++){
                            if(0 <= cs && cs < col && !fhrBoard[rs][cs].equals("F")){
                                fhrBoard[rs][cs] = "R";
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if cells must be revealed in chunks.
     * Precondition: fhrBoard is initialized.
     * Postcondition: Function does not alter variables outside itself.
     * @return int array {row, column} of the cell that needs a chunk reveal. {-1, -1} if there is no such cell.
     */
    public int[] checkChunkReveal(){
        for(int r=0; r<row; r++){
            for(int c=0; c<col; c++){
                if(fhrBoard[r][c].equals("R") && numBoard[r][c] == 0){
                    for(int rs=r-1; rs<=r+1; rs++){
                        if(0 <= rs && rs < row){
                            for(int cs=c-1; cs<=c+1; cs++){
                                if(0 <= cs && cs < col && fhrBoard[rs][cs].equals("H")){
                                    return new int[] {r, c};
                                }
                            }
                        }
                    }
                }
            }
        }
        return new int[] {-1, -1};
    }

    /**
     * Reveals all chunks that must be revealed, if any.
     * Precondition: fhrBoard is initialized.
     * Postcondition: All "H" items in fhrBoard that must be changed to "R" are changed in chunks.
     */
    public void revealChunk(){
        int[] coor = checkChunkReveal();
        while(coor[0] != -1){
            for(int rs=coor[0]-1; rs<=coor[0]+1; rs++){
                if(0 <= rs && rs < row){
                    for(int cs=coor[1]-1; cs<=coor[1]+1; cs++){
                        if(0 <= cs && cs < col){
                            fhrBoard[rs][cs] = "R";
                        }
                    }
                }
            }
            coor = checkChunkReveal();
        }
    }

    /*
     * Flags and unflags a cell at a specified point.
     * Precondition: fhrBoard is initialized.
     * Postcondition: The fhrBoard cell at the specified row and column is flagged. If it is already flagged, the cell is unflagged.
     */
    public void flagCell(int r, int c){
        if(fhrBoard[r][c].equals("H")){
            fhrBoard[r][c] = "F";
        }
        else if(fhrBoard[r][c].equals("F")){
            fhrBoard[r][c] = "H";
        }
    }

    /**
     * Checks if the game is over.
     * Precondition: fhrBoard and mines is initialized.
     * Postcondition: Function does not alter variables outside itself.
     * @return Boolean true if game is over. Boolean false otherwise.
     */
    public Boolean checkGameOver(){
        for(int r=0; r<row; r++){
            for(int c=0; c<col; c++){
                if(fhrBoard[r][c].equals("R") && mines[r][c]){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the game is cleared.
     * Precondition: fhrBoard and mineN is initialized.
     * Postcondition: Function does not alter variables outside itself.
     * @return Boolean true if game is cleared. Boolean false otherwise.
     */
    public Boolean checkGameClear(){
        int flagCount = 0;
        for(int r=0; r<row; r++){
            for(int c=0; c<col; c++){
                if(fhrBoard[r][c].equals("H")){
                    return false;
                }
                else if(fhrBoard[r][c].equals("F")){
                    flagCount += 1;
                }
            }
        }
        if(flagCount == mineN){
            return true;
        }
        return false;
    }

    /*
     * Returns the string version of the board that must be displayed.
     */
    public String toString(){
        String board = "";

        board += "   ";
        for(int c=0; c<col; c++){
            if(c + 1 > 9){
                board += "  " + (c+1);
            }
            else{
                board += "  " + (c+1) + " ";
            }
        }
        board += "\n";

        for(int r=0; r<row; r++){
            board += "   ";
            for(int c=0; c<col; c++){
                board += "+---";
            }
            board += "+\n";

            if(r + 1 > 9){
                board += (r+1) + " ";
            }
            else{
                board += " " + (r+1) + " ";
            }
            for(int c=0; c<col; c++){
                if(fhrBoard[r][c].equals("R")){
                    if(numBoard[r][c] == 0){
                        board += "|   ";
                    }
                    else{
                        if(mines[r][c]){
                            board += "| B ";
                        }
                        else{
                            board += "| " + numBoard[r][c] + " ";
                        }
                    }
                }
                else if(fhrBoard[r][c].equals("H")){
                    board += "| . ";
                }
                else{
                    board += "| X ";
                }
            }
            board += "|\n";
        }

        board += "   ";
        for(int c=0; c<col; c++){
            board += "+---";
        }
        board += "+\n";

        return board;
    }
}