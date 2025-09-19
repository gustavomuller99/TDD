import org.example.TicTacToe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeSpec {

    private TicTacToe game;

    @BeforeEach
    public void setup() {
        game = new TicTacToe();
    }

    @Test
    public void whenXOutsideBoardThenRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            game.play(0, 1);
        });
        assertThrows(RuntimeException.class, () -> {
            game.play(4, 1);
        });
    }

    @Test
    public void whenYOutsideBoardThenRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            game.play(1, 0);
        });
        assertThrows(RuntimeException.class, () -> {
            game.play(1, 4);
        });
    }

    @Test
    public void whenOccupiedPieceThenRuntimeException() {
        game.play(1, 1);
        assertThrows(RuntimeException.class, () -> {
            game.play(1, 1);
        });
    }

    @Test
    public void whenStartThenXTurn() {
        assertEquals('X', game.nextPlayer());
    }

    @Test
    public void whenXPlaysThenYTurn() {
        game.play(1, 1);
        assertEquals('O', game.nextPlayer());
    }

    @Test
    public void whenYPlaysThenXTurn() {
        game.play(1, 1);
        game.play(2, 1);
        assertEquals('X', game.nextPlayer());
    }

    @Test
    public void whenPlayThenNoWinner() {
        game.play(1, 1);
        game.play(2, 1);
        var ended = game.play(3, 1);
        assertEquals("Not ended", ended);
    }

    @Test
    public void whenPlayWholeHorizontalThenWinner() {
        game.play(1, 1);
        game.play(1, 2);
        game.play(2, 1);
        game.play(1, 3);
        var result = game.play(3, 1);
        assertEquals("Winner is X", result);
    }

    @Test
    public void whenPlayWholeVerticalThenWinner() {
        game.play(1, 1);
        game.play(3, 3);
        game.play(1, 2);
        game.play(2, 2);
        var result = game.play(1, 3);
        assertEquals("Winner is X", result);
    }

    @Test
    public void whenPlayWholeDiagonalThenWinner() {
        game.play(1, 1);
        game.play(1, 2);
        game.play(2, 2);
        game.play(1, 3);
        var result = game.play(3, 3);
        assertEquals("Winner is X", result);
    }

    @Test
    public void whenPlayWholeDiagonalThenWinner2() {
        game.play(1, 2);
        game.play(1, 3);
        game.play(2, 3);
        game.play(3, 1);
        game.play(3, 3);
        var result = game.play(2, 2);
        assertEquals("Winner is O", result);
    }

    @Test
    public void whenBoardFullThenDraw() {
        game.play(1, 1);
        game.play(1, 2);
        game.play(1, 3);
        game.play(2, 1);
        game.play(2, 3);
        game.play(2, 2);
        game.play(3, 1);
        game.play(3, 3);
        String actual = game.play(3, 2);
        assertEquals("The result is draw", actual);
    }
}
