import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.wildfly.example.threads.ReentrantLockTest;

public class DeadlockExample {
	
	@SuppressWarnings("static-access")
	static void sleep(long time){
		try {
			Thread.currentThread().sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static void simple() {
		final Object a = new Object();
		final Object b = new Object();

		new Thread(new Runnable(){
			public void run() {
				Thread.currentThread().setName("Thread 1");
				synchronized(a){
					sleep(1000);
					synchronized(b){
					}
				}
				
			}}).start();
		
		new Thread(new Runnable(){
			public void run() {
				Thread.currentThread().setName("Thread 2");
				synchronized(b){
					sleep(1000);
					synchronized(a){
					}
				}
			}}).start();
	}

	public static void main(String[] args) {
		
		final Lock a = new ReentrantLock();
		final Lock b = new ReentrantLock();
		
		new Thread(new Runnable(){
			public void run() {
				Thread.currentThread().setName("Thread 1");
				a.lock();
				sleep(1000);
				b.lock();
				
			}}).start();
		
		new Thread(new Runnable(){
			public void run() {
				Thread.currentThread().setName("Thread 2");
				b.lock();
				sleep(1000);
				a.lock();
			}}).start();
		
	}

}
