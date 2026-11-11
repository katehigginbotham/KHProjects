import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private Comparator<T> comp;

    public MaxArrayDeque61B(Comparator<T> c) {
        this.comp = c;
    }

    public T max() {
        return max(comp);
    }

    public T max(Comparator<T> items) {
        if (items == null) {
            return null;
        }
        T maxItem = get(0);
        for (int i = 0; i < size(); i++) {
            T curr = get(i);
            if (curr != null & comp.compare(curr, maxItem) > 0) {
                maxItem = curr;
            }
        }
        return maxItem;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
