class C {
	public void foo(){
		synchronized(this){
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void bar() {
		synchronized(this){
			notifyAll();
		}
	}
}
public class WaitSetExample {

	public static void main(String[] args) throws InterruptedException {

		final BlockingQueue<String> queue = new BlockingQueue<>(3);
						
		new Thread(new Runnable(){
			public void run() {
				Thread.currentThread().setName("Thread 1");
				System.out.println(queue.take());
			}}).start();
		
		new Thread(new Runnable(){
			public void run() {
				Thread.currentThread().setName("Thread 2");
				System.out.println(queue.take());
			}}).start();
		
		new Thread(new Runnable(){
			public void run() {
				Thread.currentThread().setName("Thread 3");
				System.out.println(queue.take());
			}}).start();
		
		
		Thread.sleep(1000);
		
		queue.put("hello");
		
//		c.bar();
//		c.bar();
//		c.bar();
	}

}
