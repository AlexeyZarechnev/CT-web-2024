package ru.itmo.wp.web.page;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import ru.itmo.wp.web.exception.RedirectException;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {

    public static class State {

        private final int size;
        private final String[][] cells;
        private Phase phase;
        private boolean crossesMove;
        private final int winCount;


        public static enum Phase {
            RUNNING, WON_X, WON_O, DRAW
        }

        public State(int size) {
            this(size, size);
        }

        public State(int size, int winCount) {
            this.size = size;
            this.winCount = winCount;
            this.cells = new String[size][size];
            this.phase = Phase.RUNNING;
            this.crossesMove = true;
        }

        public int getSize() {
            return size;
        }

        public String[][] getCells() {
            return cells;
        }

        public Phase getPhase() {
            return phase;
        }

        public boolean getCrossesMove() {
            return crossesMove;
        }

        private void doTurn(int row, int column) {
            if (row >= size || column >= size || cells[row][column] != null) {
                return;
            }

            cells[row][column] = crossesMove ? "X" : "O";
            updateState(row, column);
            crossesMove = !crossesMove;
            return;
        }

        private void updateState(int row, int column) {
            int verticalRight = count(row, column, 1, 0);
            int verticalLeft = count(row, column, -1, 0);
            int horizontalRight = count(row, column, 0, 1);
            int horizontalLeft = count(row, column, 0, -1);
            int diagonalRightUp = count(row, column, 1, 1);
            int diagonalLeftDown = count(row, column, -1, -1);
            int diagonalRightDown = count(row, column, 1, -1);
            int diagonalLeftUp = count(row, column, -1, 1);

            if (verticalRight + verticalLeft - 1 >= winCount || 
                horizontalRight + horizontalLeft - 1 >= winCount || 
                diagonalRightUp + diagonalLeftDown - 1 >= winCount || 
                diagonalRightDown + diagonalLeftUp - 1 >= winCount) {
                phase = crossesMove ? Phase.WON_X : Phase.WON_O;
            } else if (isFull()) {
                phase = Phase.DRAW;
            }
        }

        private boolean isFull() {
            return Arrays.stream(cells).filter(row -> !Arrays.stream(row).filter(cell -> cell == null)
                                                             .collect(Collectors.toList()).isEmpty())
                         .collect(Collectors.toList()).isEmpty();
        }

        private int count(int row, int column, int rowDelta, int columnDelta) {
            String current = cells[row][column];
            int result = 0;
            do {
                result++;
                row += rowDelta;
                column += columnDelta;
            } while (row >= 0 && row < size && column >= 0 && column < size && current.equals(cells[row][column]));
            return result;
        }
    }

    private static int SIZE = 5;
    private static int WIN = 4;

    private void action(Map<String, Object> view, HttpServletRequest request) {
        State state = (State) request.getSession().getAttribute("state");
        if (state == null) {
            state = new State(SIZE, WIN);
        }

        request.getSession().setAttribute("state", state);
        view.put("state", state);
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("state");
        if (state == null) {
            state = new State(SIZE, WIN);
        }

        if (state.getPhase() == State.Phase.RUNNING) {

            String cellNumber;
            for (String name : request.getParameterMap().keySet()) {
                if (name.startsWith("cell_")) {
                    String[] rowAndCol = name.substring("cell_".length()).split(";");
                    if (rowAndCol.length != 2) {
                        break;
                    }
                    int row;
                    int column;
                    try {
                        row = Integer.parseInt(rowAndCol[0]);
                        column = Integer.parseInt(rowAndCol[1]);
                    } catch (NumberFormatException e) {
                        break;
                    }
                    state.doTurn(row, column);
                }
            }
        }

        request.getSession().setAttribute("state", state);
        view.put("state", state);
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("state");
        if (state != null && state.getPhase() == State.Phase.RUNNING) {
            throw new RedirectException("/ticTacToe");
        }

        state = new State(SIZE, WIN);
        request.getSession().setAttribute("state", state);
        view.put("state", state);
    }
}
