package ait.mediation;

import java.util.LinkedList;

public class BlkQueueImpl<T> implements BlkQueue<T> {
    private LinkedList<T> queue = new LinkedList<>();
    private int maxSize;

    public BlkQueueImpl(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public synchronized void push(T message) {
        while (queue.size() >= maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.add(message);
        notify();
    }

    @Override
    public synchronized T pop() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        T message = queue.poll();
        notifyAll();
        return message;
    }
}
