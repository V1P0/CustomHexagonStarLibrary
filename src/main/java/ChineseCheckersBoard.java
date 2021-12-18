import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ChineseCheckersBoard {
    private int[][] board;
    private int size;

    public void setBoard(int[][] board, int size){
        this.board=board;
        this.size = size;
    }

    public int[][] getBoard(){
        return board;
    }


    public void printInTerminal(){
        for(int[] a :board){
            for(int b :a){
                if(b<0) {
                    System.out.print("   ");
                }else{
                    System.out.print("[" + b + "]");
                }
            }
            System.out.println();
        }
    }

    public ChineseCheckersBoard setValidMoves(int height, int width) {
        /*
        move left width - 2
        move right width + 2
        move up-left width - 1 height - 1
        move up-right width + 1 height - 1
        move down-left width - 1 height + 1
        move down-right width + 1 height + 1
         */
        ChineseCheckersBoard LogicBoard;
        try {
            LogicBoard = new ChineseCheckersBoardBuilder().setNumberOfPlayers(0).setSize(size).build();
            if(board[height][width] == 0) return LogicBoard;
                for(int i = 0; i<6; i++){
                try {
                    int[] pointaftermove = moveOneField(height, width, i);
                    if (board[pointaftermove[0]][pointaftermove[1]] == 0){
                        LogicBoard.board[pointaftermove[0]][pointaftermove[1]] = 1;
                    }
                }catch (Exception ignored){}
            }
            return LogicBoard;
        }catch (Exception ignored){}
        return null;
    }

    /**
     * return coordinates after move
     * @param height current hight
     * @param width current width
     * @param direction 0 - right, 1 - down-right, 2 - down-left, 3 - left, 4 - up-left, 5 - up-right
     * @return coordinates after move
     * @throws Exception throws if point is outside of the board
     */
    public int[] moveOneField(int height, int width, int direction) throws Exception{
        switch(direction){
            case 0 -> width+=2;
            case 1 -> {width++; height++;}
            case 2 -> {width--; height++;}
            case 3 -> width-=2;
            case 4 -> {width--; height--;}
            case 5 -> {width++; height--;}
        }
        if(width<0||width==board[0].length||height<0||height== board.length) throw new Exception("point is outside of the board");
        if(board[height][width]==-1) throw new Exception("point is outside of the board");
        return new int[]{height, width};
    }

    public void move(int pawnX, int pawnY, int moveX, int moveY) throws Exception{
        ChineseCheckersBoard Logic = setValidMoves(pawnY, pawnX);
        if(Logic.getBoard()[moveY][moveX] == 1){
            board[moveY][moveX] = board[pawnY][pawnX];
            board[pawnY][pawnX] = 0;
        }else{
            throw new Exception("invalid move");
        }
    }
}
