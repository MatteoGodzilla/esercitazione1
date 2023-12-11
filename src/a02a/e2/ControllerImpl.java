package a02a.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;

import javax.swing.text.html.Option;

public class ControllerImpl implements Controller {

    private enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    private List<List<Optional<Integer>>> matrix;
    private int count = -1;
    private int rowPosition = -1;
    private int columnPosition = -1;
    private Direction dir = Direction.UP;
    // this should be a list
    private List<BiConsumer<Pair<Integer, Integer>, Optional<Integer>>> listeners;

    public ControllerImpl() {
        listeners = new ArrayList<>();
    }

    @Override
    public void setDimentions(int rows, int columns) {
        matrix = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<Optional<Integer>> row = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                row.add(Optional.empty());
            }
            matrix.add(row);
        }
    }

    @Override
    public void step() {
        Optional<Integer> result;
        if (count == -1) {
            // generate first number
            var random = new Random();
            rowPosition = random.nextInt(matrix.size());
            columnPosition = random.nextInt(matrix.get(0).size());
            count = 0;
            result = Optional.of(count);
        } else {
            result = Optional.empty();
            switch (this.dir) {
                case DOWN:
                    if(advance(1, 0, Direction.LEFT)){
                        count++;
                        result = Optional.of(count);
                    } 
                    break;
                case LEFT:
                    if(advance(0, -1, Direction.UP)){
                        count++;
                        result = Optional.of(count);
                    } 
                    break;
                case RIGHT:
                    if(advance(0, 1, Direction.DOWN)){
                        count++;
                        result = Optional.of(count);
                    } 
                    break;
                case UP:
                    if(advance(-1, 0, Direction.RIGHT)){
                        count++;
                        result = Optional.of(count);
                    } 
                    break;
            }
        }
        matrix.get(rowPosition).set(columnPosition, Optional.of(count));
        for (var listener : listeners) {
            listener.accept(new Pair<>(columnPosition, rowPosition), result);
        }
    }

    @Override
    public void onStep(BiConsumer<Pair<Integer, Integer>, Optional<Integer>> listener) {
        listeners.add(listener);
    }

    private boolean isMatrixEmptyAt(int row, int col) {
        if (row < 0 || col < 0) {
            return false;
        }
        if (row >= matrix.size() || col >= matrix.get(0).size()) {
            return false;
        }
        return matrix.get(row).get(col).isEmpty();
    }

    private boolean advance(int deltaRow, int deltaCol, Direction newDirection) {
        if (isMatrixEmptyAt(rowPosition + deltaRow, columnPosition + deltaCol)) {
            this.rowPosition += deltaRow;
            this.columnPosition += deltaCol;
            return true;
        } else if (isMatrixEmptyAt(rowPosition + deltaCol, columnPosition - deltaRow)) {
            this.rowPosition += deltaCol;
            this.columnPosition -= deltaRow;
            this.dir = newDirection;
            return true;
        } 
        return false;
    }

}
