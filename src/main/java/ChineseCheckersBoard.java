public class ChineseCheckersBoard {
    private int[][] board;
    private int size;
    int height;
    int width;

    public void setBoard(int[][] board, int size){
        this.board=board;
        this.size = size;
        height = size+3*(size-1);
        width = 2*(size+2*(size-1))-1;
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
        boolean isInTarget = isInTarget(width,height,width,height);
        ChineseCheckersBoard LogicBoard;
        try {
            LogicBoard = new ChineseCheckersBoardBuilder().setNumberOfPlayers(0).setSize(size).build();
            if(board[height][width] == 0) return LogicBoard;
            for(int i = 0; i<6; i++){
                try {
                    int[] pointaftermove = moveOneField(height, width, i);
                    if (board[pointaftermove[0]][pointaftermove[1]] == 0){
                        if(isInTarget){
                            if(isInTarget(width,height,pointaftermove[1],pointaftermove[0])){
                                LogicBoard.board[pointaftermove[0]][pointaftermove[1]] = 1;
                            }
                        }else {
                            LogicBoard.board[pointaftermove[0]][pointaftermove[1]] = 1;
                        }
                    }else if(board[pointaftermove[0]][pointaftermove[1]] > 0){
                        int[] pointafterjump = moveOneField(pointaftermove[0],pointaftermove[1],i);
                        //jump validation
                        if(board[pointafterjump[0]][pointafterjump[1]] == 0){
                            if(isInTarget){
                                if(isInTarget(width,height,pointafterjump[1],pointafterjump[0])){
                                    LogicBoard.board[pointafterjump[0]][pointafterjump[1]] = 1;
                                }
                            }else {
                                LogicBoard.board[pointafterjump[0]][pointafterjump[1]] = 1;
                            }
                            validateAfterJump(LogicBoard,pointafterjump[0],pointafterjump[1], height, width, width, height);
                        }
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

    private void validateAfterJump(ChineseCheckersBoard logic, int heightAfterJump, int widthAfterJump, int heightBeforeJump, int widthBeforeJump, int pawnX,int pawnY){
        boolean isInTarget = isInTarget(pawnX,pawnY,pawnX,pawnY);
        for(int i = 0; i<6; i++){
            try {
                int[] pointaftermove = moveOneField(heightAfterJump, widthAfterJump, i);
                if(board[pointaftermove[0]][pointaftermove[1]] > 0){
                    int[] pointafterjump = moveOneField(pointaftermove[0],pointaftermove[1],i);
                    if(pointafterjump[0]==heightBeforeJump && pointafterjump[1]==widthBeforeJump) continue;
                    if(board[pointafterjump[0]][pointafterjump[1]] == 0){
                        if(logic.board[pointafterjump[0]][pointafterjump[1]] == 1) continue;
                        if(isInTarget){
                            if(isInTarget(pawnX,pawnY,pointafterjump[1],pointafterjump[0])){
                                logic.board[pointafterjump[0]][pointafterjump[1]] = 1;
                            }
                        }else {
                            logic.board[pointafterjump[0]][pointafterjump[1]] = 1;
                        }
                        validateAfterJump(logic,pointafterjump[0],pointafterjump[1], heightAfterJump, widthAfterJump, pawnX, pawnY);
                    }
                }
            }catch (Exception ignored){}
        }
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
    public boolean isInTarget(int pawnX, int pawnY, int moveX, int moveY){
        switch(board[pawnY][pawnX]){
            case 1 -> {return fourthTriangle(moveX,moveY);}
            case 2 -> {return fifthTriangle(moveX,moveY);}
            case 3 -> {return sixthTriangle(moveX,moveY);}
            case 4 -> {return firstTriangle(moveX,moveY);}
            case 5 -> {return secondTriangle(moveX,moveY);}
            case 6 -> {return thirdTriangle(moveX,moveY);}
        }
        return false;
    }
    private boolean firstTriangle(int X, int Y){
        boolean val = false;
        int goodPoint = width/2;
        for(int i = 1; i<size; i++){
            for(int j = 0; j<i; j++){
                if(X == goodPoint+2*j && Y == height-i ){
                    val = true;
                    break;
                }
            }
            goodPoint = goodPoint - 1;
        }
        return val;
    }
    private boolean secondTriangle(int X, int Y){
        boolean val = false;
        int goodPoint = 0;
        for(int i = size; i<2*size-1; i++){
            for(int j = 0; j<size-(i-size)-1; j++){
                if(X == goodPoint+2*j && Y == height-i){
                    val = true;
                    break;
                }
            }
            goodPoint = goodPoint + 1;
        }
        return val;
    }
    private boolean thirdTriangle(int X, int Y){
        boolean val = false;
        int goodPoint = 0;
        for(int i = size; i<2*size-1; i++){
            for(int j = 0; j<size-(i-size)-1; j++){
                if(X == goodPoint+2*j && Y == i-1){
                    val = true;
                    break;
                }
            }
            goodPoint = goodPoint + 1;
        }
        return val;
    }
    private boolean fourthTriangle(int X, int Y){
        boolean val = false;
        int goodPoint = width/2;
        for(int i = 1; i<size; i++){
            for(int j = 0; j<i; j++){
                if(X == goodPoint+2*j && Y == i-1 ){
                    val = true;
                    break;
                }
            }
            goodPoint = goodPoint - 1;
        }
        return val;
    }
    private boolean fifthTriangle(int X, int Y){
        boolean val = false;
        int goodPoint = 0;
        for(int i = size; i<2*size-1; i++){
            for(int j = 0; j<size-(i-size)-1; j++){
                if(X == width-goodPoint-2*j-1 && Y == i-1){
                    val = true;
                    break;
                }
            }
            goodPoint = goodPoint + 1;
        }
        return val;
    }
    private boolean sixthTriangle(int X, int Y){
        boolean val = false;
        int goodPoint = 0;
        for(int i = size; i<2*size-1; i++){
            for(int j = 0; j<size-(i-size)-1; j++){
                if(X == width-goodPoint-2*j-1 && Y == height-i){
                    val = true;
                    break;
                }
            }
            goodPoint = goodPoint + 1;
        }
        return val;
    }

}
