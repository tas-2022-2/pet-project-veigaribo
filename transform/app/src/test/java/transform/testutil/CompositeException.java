package transform.testutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CompositeException extends Exception implements Collection<Throwable> {
    private final List<Throwable> items;

    public CompositeException(List<Throwable> items) {
        this.items = items;
    }

    public CompositeException() {
        this(new ArrayList<Throwable>());
    }

    public Throwable get(int i) {
        return items.get(i);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return items.contains(o);
    }

    @Override
    public Iterator<Throwable> iterator() {
        return items.iterator();
    }

    @Override
    public Object[] toArray() {
        return items.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return items.toArray(a);
    }

    @Override
    public boolean add(Throwable e) {
        return items.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return items.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return items.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Throwable> c) {
        return items.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return items.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return items.retainAll(c);
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public String getMessage() {
        if (size() == 0)
            return "";

        final var header = "Errors:\n";
        final var builder = new StringBuilder(header);
        builder.append('\t');
        builder.append(get(0).getLocalizedMessage());

        for (var i = 1; i < size(); ++i) {
            final var error = get(i);

            builder.append("\n\t");
            builder.append(error.getLocalizedMessage());
        }

        return builder.toString();
    }
}
