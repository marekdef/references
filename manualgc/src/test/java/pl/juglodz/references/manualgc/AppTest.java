package pl.juglodz.references.manualgc;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;

/**
 * Unit test for simple App.
 */
public class AppTest {
	@Rule
	public BenchmarkRule rule = new BenchmarkRule();

	@Test
	public void testWithGC() throws InterruptedException {
		Runnable runnable = new GCRunnable();
		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);
		Thread thread3 = new Thread(runnable);
		Thread thread4 = new Thread(runnable);
		Thread thread5 = new Thread(runnable);
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		
		thread1.join();
		thread2.join();
		thread3.join();
		thread4.join();
		thread5.join();
	}
	
	@Test
	public void testWithoutGC() throws InterruptedException {
		Runnable runnable = new NoGCRunnable();
		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);
		Thread thread3 = new Thread(runnable);
		Thread thread4 = new Thread(runnable);
		Thread thread5 = new Thread(runnable);
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		
		thread1.join();
		thread2.join();
		thread3.join();
		thread4.join();
		thread5.join();
	}

	public static class GCRunnable implements Runnable {

		public void run() {
			for (int i = 0; i < 1000; ++i) {
				String random = RandomStringUtils.random(65535);
				if(random.length() > 10)
					continue;
			}
			System.gc();
		}
	}
	
	public static class NoGCRunnable implements Runnable {

		public void run() {
			for (int i = 0; i < 1000; ++i) {
				String random = RandomStringUtils.random(65535);
				if(random.length() > 10)
					continue;
			}
		}
	}
}
