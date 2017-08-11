package ru.otus.observable;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 */
public class ObservableTest {
    @Test
    public void test() {
        Map<String, Object> results1 = new HashMap<>();

        ObserverManager<Object> observerManager = new ObserverManagerImpl<>();
        ObservableVariable<Object> o1 = observerManager.createNewObservableVariable("o1", "o1val");
        ObservableVariable<Object> o2 = observerManager.createNewObservableVariable("o2", "o2val");

        o1.setValue("o1value_changed");

        Listener<Object> listener1 = results1::put;
        observerManager.addListener(listener1);

        o2.setValue("o2value_changed");

        ObservableVariable<Object> o3 = observerManager.createNewObservableVariable("o3", "o3val");
        o3.setValue("o3value_changed");

        Map<String, Object> results2 = new HashMap<>();
        observerManager.addListener(results2::put);

        ObservableVariable<Object> o4 = observerManager.createNewObservableVariable("o4", "o4val");
        o4.setValue("o4value_changed");

        observerManager.removeListener(listener1);

        o3.setValue("o3value_changed_again");

        assert results1.size() == 3
                && results1.get("o2").equals("o2value_changed")
                && results1.get("o3").equals("o3value_changed")
                && results1.get("o4").equals("o4value_changed");

        assert results2.size() == 2
                && results2.get("o3").equals("o3value_changed_again")
                && results2.get("o4").equals("o4value_changed");
    }
}
