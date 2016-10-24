package org.openexchange.jmm;


public class LockTest {

    private final Lock lock = new Lock();

    private void tryLock() throws InterruptedException {
        lock.waitFor();
    }

    public static void main(String... args) throws InterruptedException {
        LockTest lockTest = new LockTest();
        lockTest.tryLock();
    }
}
