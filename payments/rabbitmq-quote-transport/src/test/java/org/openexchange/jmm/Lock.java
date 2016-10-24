package org.openexchange.jmm;

public class Lock {
    private final Object LOCK = new Object();

    public void waitFor() throws InterruptedException {
        synchronized (LOCK){
            LOCK.wait();
        }
    }
}
