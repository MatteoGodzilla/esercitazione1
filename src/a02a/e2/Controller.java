package a02a.e2;

import java.util.Optional;
import java.util.function.BiConsumer;

//rinomina in model
public interface Controller {
    void setDimentions(int rows, int columns);

    void step();

    void onStep(BiConsumer<Pair<Integer, Integer>, Optional<Integer>> listener);
}
