package conn4;

import java.util.ArrayList;

public class Board {

    private int[][] matrix;

    public Board() {
        /*
         * 0 if cell is empty 
         * 1 if user cell
         * -1 if ai cell
         */
        this.matrix = new int[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                this.matrix[i][j] = 0;
            }
        }
    }

    public Board(Board b) {
        int[][] m = b.getMatrix().clone();
        for (int i = 0; i < m.length; i++) {
            m[i] = m[i].clone();
        }
        this.matrix = m;
    }

    public int[][] getMatrix() {
        return matrix.clone();
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int setUserMove(int iColIndex) {
        putValue(iColIndex, 1);
        return checkBoard();
    }

    private int checkBoard() {
        // returns 1 if the user won
        //returns -1 if the ai won
        //returns 0 otherwise
        //row check
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                switch (matrix[i][j] + matrix[i][j + 1] + matrix[i][j + 2] + matrix[i][j + 3]) {
                    case 4:
                        return 1;
                    case -4:
                        return -1;
                }

            }
        }
        //column check
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                switch (matrix[i][j] + matrix[i + 1][j] + matrix[i + 2][j] + matrix[i + 3][j]) {
                    case 4:
                        return 1;
                    case -4:
                        return -1;
                }

            }
        }
        //diagonal check
        for (int i = 0; i < 3; i++) {
            for (int j = 6; j > 2; j--) {
                switch (matrix[i][j] + matrix[i + 1][j - 1] + matrix[i + 2][j - 2] + matrix[i + 3][j - 3]) {
                    case 4:
                        return 1;
                    case -4:
                        return -1;
                }
            }
            for (int j = 0; j < 4; j++) {
                switch (matrix[i][j] + matrix[i + 1][j + 1] + matrix[i + 2][j + 2] + matrix[i + 3][j + 3]) {
                    case 4:
                        return 1;
                    case -4:
                        return -1;
                }
            }
        }
        return 0;
    }

    public boolean isFullRow(int iCol) {
        if (matrix[0][iCol] == 0) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String sRes = "|";
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                switch (matrix[i][j]) {
                    case 0:
                        sRes += "_____|";
                        break;
                    case 1:
                        sRes += "__W__|";
                        break;
                    case -1:
                        sRes += "__B__|";
                        break;
                }
            }
            if (i != 5) {
                sRes += "\n|";
            }
        }
        return sRes;
    }

    private int putValue(int col, int type) {
        for (int i = 5; i >= 0; i--) {
            if (matrix[i][col] == 0) {
                matrix[i][col] = type;
                return i;
            }
        }
        return 0;
    }

    public int checkCriticalMove(int type) {
        /*
         * type= 1 if checking for user move
         * type= -1 if checking for ai move
         * returns the row of the critical move
         * or -1 if no such critical position
         */
        //row check
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] + matrix[i][j + 1] + matrix[i][j + 2] + matrix[i][j + 3] == 3 * type) {
                    if (matrix[i][j] == 0 && (i == 5 || matrix[i + 1][j] != 0)) {
                        return j;
                    }
                    if (matrix[i][j + 1] == 0 && (i == 5 || matrix[i + 1][j + 1] != 0)) {
                        return j + 1;
                    }
                    if (matrix[i][j + 2] == 0 && (i == 5 || matrix[i + 1][j + 2] != 0)) {
                        return j + 2;
                    }
                    if (matrix[i][j + 3] == 0 && (i == 5 || matrix[i + 1][j + 3] != 0)) {
                        return j + 3;
                    }
                }

            }
        }
        //column check
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (matrix[i][j] + matrix[i + 1][j] + matrix[i + 2][j] + matrix[i + 3][j] == 3 * type) {
                    if (matrix[i][j] == 0) {
                        return j;
                    }
                }
            }
        }
        //diagonal check
        for (int i = 0; i < 3; i++) {
            for (int j = 6; j > 2; j--) {
                if (matrix[i][j] + matrix[i + 1][j - 1] + matrix[i + 2][j - 2] + matrix[i + 3][j - 3] == 3 * type) {
                    if (matrix[i][j] == 0 && matrix[i + 1][j] != 0) {
                        return j;
                    }
                    if (matrix[i + 1][j - 1] == 0 && matrix[i + 1 + 1][j - 2] != 0) {
                        return j - 1;
                    }
                    if (matrix[i + 2][j - 2] == 0 && matrix[i + 2 + 1][j - 2] != 0) {
                        return j - 2;
                    }
                    if (matrix[i + 3][j - 3] == 0 && (i + 3 == 5 || matrix[i + 3 + 1][j] != 0)) {
                        return j - 3;
                    }
                }
            }
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] + matrix[i + 1][j + 1] + matrix[i + 2][j + 2] + matrix[i + 3][j + 3] == 3 * type) {
                    if (matrix[i][j] == 0 && matrix[i + 1][j] != 0) {
                        return j;
                    }
                    if (matrix[i + 1][j + 1] == 0 && matrix[i + 1 + 1][j + 1] != 0) {
                        return j + 1;
                    }
                    if (matrix[i + 2][j + 2] == 0 && matrix[i + 2 + 1][j + 2] != 0) {
                        return j + 2;
                    }
                    if (matrix[i + 3][j + 3] == 0 && (i + 3 == 5 || matrix[i + 3 + 1][j + 3] != 0)) {
                        return j + 3;
                    }
                }
            }
        }
        return -1;
    }

    public int checkXoxMove(int type) {
        /* check if there is a XOX move available (also OXX, XXO) 
         * returns the position(column) of this move
         * or -1 if no such position exists
         */
        for (int i = 0; i < 6; i++) {
            for (int j = 1; j < 4; j++) {
                if (matrix[i][j] == type
                        && matrix[i][j + 2] == type
                        && matrix[i][j - 1] == 0
                        && matrix[i][j + 1] == 0
                        && matrix[i][j + 3] == 0
                        && (i == 5 || matrix[i + 1][j + 3] != 0 || matrix[i + 1][j - 1] != 0)) {
                    return j + 1;
                }
                if (matrix[i][j] == type
                        && matrix[i][j + 1] == type
                        && matrix[i][j - 1] == 0
                        && matrix[i][j + 2] == 0
                        && matrix[i][j + 3] == 0
                        && (i == 5 || (matrix[i + 1][j + 2] != 0 && matrix[i + 1][j - 1] != 0))) {
                    return j + 2;
                }
                if (matrix[i][j + 1] == type
                        && matrix[i][j + 2] == type
                        && matrix[i][j - 1] == 0
                        && matrix[i][j] == 0
                        && matrix[i][j + 3] == 0
                        && (i == 5 || (matrix[i + 1][j + 3] != 0 && matrix[i + 1][j] != 0))) {
                    return j;
                }
            }
        }
        return -1;
    }

    public int[] checkThreeCells(int type) {
        /* returns a list of moves which can be made to gain 3 elements in a row
         * returns the position(column) of this moves
         * 
         */
        ArrayList<Integer> res = new ArrayList();
        //row
        for (int i = 0; i < 6; i++) {
            for (int j = 1; j < 5; j++) {//middle rows
                if ((matrix[i][j - 1] == 0 || matrix[i][j + 2] == 0)
                        && matrix[i][j] == type
                        && matrix[i][j + 1] == type) {
                    if (matrix[i][j - 1] == 0
                            && (i == 5 || matrix[i + 1][j - 1] != 0)
                            && res.indexOf(j - 1) == -1) {
                        res.add(j - 1);
                    }
                    if (matrix[i][j + 1] == 0
                            && (i == 5 || matrix[i + 1][j + 1] != 0)
                            && res.indexOf(j + 1) == -1) {
                        res.add(j + 1);
                    }
                }
            }

            if (matrix[i][0] == type
                    && matrix[i][1] == type
                    && matrix[i][2] == 0
                    && (i == 5 || matrix[i + 1][2] != 0)
                    && res.indexOf(2) == -1) {
                res.add(2);
            }
            if (matrix[i][6] == type
                    && matrix[i][5] == type
                    && matrix[i][4] == 0
                    && (i == 5 || matrix[i + 1][4] != 0)
                    && res.indexOf(4) == -1) {
                res.add(4);
            }
        }
        //column
        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                if (matrix[i][j] == type
                        && matrix[i + 1][j] == type
                        && matrix[i - 1][j] == 0
                        && res.indexOf(j) == -1) {
                    res.add(j);
                }
            }
        }
        //diagonal
        for (int i = 5; i > 1; i--) {
            for (int j = 6; j > 1; j--) {
                if (matrix[i][j] == type
                        && matrix[i - 1][j - 1] == type
                        && matrix[i - 2][j - 2] == 0
                        && matrix[i - 1][j - 2] != 0
                        && res.indexOf(j - 2) == -1) {
                    res.add(j - 2);
                }
            }
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == type
                        && matrix[i - 1][j + 1] == type
                        && matrix[i - 2][j + 2] == 0
                        && matrix[i - 1][j + 2] != 0
                        && res.indexOf(j + 2) == -1) {
                    res.add(j + 2);
                }
            }
        }
        int[] arr = new int[res.size()];
        int i = 0;
        for (Integer x : res) {
            arr[i++] = x;
        }
        return arr;
    }

    public int chooseMove() {
        //if a position is viable -> 0
        //if a positon may give favorable move -> 1
        //if the position will create a critical move -> -10
        //if the position may give a xox chance -> 
        int iCol;
        //intersect the adjacent rows
        int[] aUser = checkThreeCells(1);
        int[] aAi = checkThreeCells(1);

        int[] totalScore = {-1, -1, -1, -1, -1, -1, -1};//init cost for all rows 

        for (iCol = 0; iCol < 7; iCol++) {
            Board b = new Board(this);
            if (b.isFullRow(iCol)) {
                continue;
            }
            b.putValue(iCol, -1);
            if (b.checkCriticalMove(1) != -1) {
                //ai move creates critical move for player at next step
                totalScore[iCol] = -10;
            } else {
                for (int userCol = 0; userCol < 7; userCol++) {
                    //ai move may create xox move for player at next step
                    Board b2 = new Board(b);
                    b2.putValue(userCol, 1);
                    if (b2.checkXoxMove(1) != -1) {
                        totalScore[iCol] = -5;
                    }
                }
            }
        }

        int maxPos = 0;
        for (int i = 0; i < totalScore.length; i++) {
            for (int j = 0; j < aUser.length; j++) {
                if (totalScore[i] >= -5 && aUser[j] == i) {
                    totalScore[i] += 2;
                }
            }
            for (int j = 0; j < aAi.length; j++) {
                if (totalScore[i] >= -5 && aUser[j] == i) {
                    totalScore[i] += 1;
                }
            }

        }

        for (int i = 0; i < totalScore.length; i++) {
            if (totalScore[maxPos] <= totalScore[i] && !isFullRow(i)) {
                Board b = new Board(this);
                b.putValue(i, -1);
                if (i != b.checkCriticalMove(1)) {
                    maxPos = i;
                }
            }
//            System.out.print(totalScore[i] + "_");
        }
//        System.err.println("\n");
        return maxPos;
    }

    public int setAiMove() {
        int pos = checkCriticalMove(-1);  //AI winning move
        if (pos != -1) {
            putValue(pos, -1);
            return checkBoard();
        }
        pos = checkCriticalMove(1); //player winning move
        if (pos != -1) {
            putValue(pos, -1);
            return checkBoard();
        }
        pos = checkXoxMove(1);  //player xox move
        if (pos != -1) {
            putValue(pos, -1);
            return checkBoard();
        }
//        chooseMove();
        putValue(this.chooseMove(), -1);
        return checkBoard();
    }
}