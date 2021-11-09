package by.varyvoda.matvey.client.observable;

import java.util.ArrayList;
import java.util.List;

public class Observable<V>{

    private interface Callback<V> {
        void call(V value);
    }

    private final List<Callback<V>> subscribers = new ArrayList<>();
    private boolean isPresent = false;
    private V value;

    public void setValue(V value) {
        isPresent = true;
        this.value = value;
    }

    private void subscribe(Callback<V> callback) {
        subscribers.add(callback);
        if(isPresent) callback.call(value);
    }

    private void unsubscribe(Callback<V> callback) {
        subscribers.remove(callback);
    }
}
