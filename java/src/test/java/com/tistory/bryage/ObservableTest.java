package com.tistory.bryage;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.*;

@Slf4j
public class ObservableTest {

    @Test
    public void singleObserver() throws InterruptedException {

        Observer o1 = (o, arg) ->
                log.debug("{} / arg={}", Thread.currentThread().getName(), arg);

        IntObservable intObservable = new IntObservable();
        intObservable.addObserver(o1);

        ExecutorService executors = Executors.newSingleThreadExecutor();
        executors.submit(intObservable);

        log.debug("{} / EXIT", Thread.currentThread().getName());
        executors.awaitTermination(1, TimeUnit.SECONDS);
        executors.shutdown();
    }

    @Test
    public void multipleObserver() throws InterruptedException {

        Observer o1 = (o, arg) ->
                log.debug("{} / arg={}", Thread.currentThread().getName(), arg);

        Observer o2 = (o, arg) ->
                log.debug("{} just hello", Thread.currentThread().getName());

        IntObservable intObservable = new IntObservable();
        intObservable.addObserver(o1);
        intObservable.addObserver(o2);

        ExecutorService executors = Executors.newSingleThreadExecutor();
        executors.submit(intObservable);

        log.debug("{} / EXIT", Thread.currentThread().getName());
        executors.awaitTermination(1, TimeUnit.SECONDS);
        executors.shutdown();
    }

    static class IntObservable extends Observable implements Runnable {

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {
                setChanged();
                notifyObservers(i);
            }
        }
    }
}
