package tw.edu.ntub.imd.plearnet.util.function;

@FunctionalInterface
public interface TripleConsumer<F, S, T> {
    void accept(F f, S s, T t);
}
