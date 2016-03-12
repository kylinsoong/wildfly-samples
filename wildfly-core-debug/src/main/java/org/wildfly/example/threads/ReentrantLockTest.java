package org.wildfly.example.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
	
	 /** Lock held by take, poll, etc */
    private final ReentrantLock takeLock = new ReentrantLock();

    /** Wait queue for waiting takes */
    private final Condition notEmpty = takeLock.newCondition();
	
	private final ReentrantLock mainLock = new ReentrantLock();
	
	private void test() {

		final ReentrantLock mainLock = this.mainLock;
		
        mainLock.lock();
	}
	
	private ReentrantLock lock = new ReentrantLock();
    private Condition batchesFreed = lock.newCondition();

	public static void main(String[] args) {

		ReentrantLockTest test = new ReentrantLockTest();
		
		test.test();
	}

	

}
