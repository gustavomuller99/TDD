package org.example;

public class TicTacToe {

    private Character[][] board =
            {{'\0', '\0', '\0'},
             {'\0', '\0', '\0'},
             {'\0', '\0', '\0'}};

    private char player = 'X';

    public char nextPlayer() {
        return player;
    }

    public String play(int x, int y) {
        checkAxis(x);
        checkAxis(y);
        place(x, y);

        if (isDraw()) {
            return "The result is draw";
        }

        if (isWin(x, y)) {
            return "Winner is " + player;
        }

        switchPlayer();
        return "Not ended";
    }

    private void checkAxis(int k) {
        if (k < 1 || k > 3) {
            throw new RuntimeException("Out of bounds");
        }
    }

    private void place(int x, int y) {
        if  (board[x - 1][y - 1] != '\0') {
            throw new RuntimeException("Already placed!");
        }
        board[x - 1][y - 1] = player;
    }

    private void switchPlayer() {
        player = player == 'X' ? 'O' : 'X';
    }

    private boolean isWin(int x, int y) {
        int total = player * 3;
        char vertical, horizontal, diagonal1, diagonal2;
        vertical = horizontal = diagonal1 = diagonal2 = '\0';

        for (int i = 0; i < 3; i++) {
            vertical += board[x - 1][i];
            horizontal += board[i][y - 1];
            diagonal1 += board[i][i];
            diagonal2 += board[i][3 - i - 1];
        }

        if (vertical == total || horizontal == total || diagonal1 == total || diagonal2 == total) {
            return true;
        }
        return false;
    }

    private boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }
}
