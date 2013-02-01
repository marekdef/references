package pl.juglodz.memory.references.introduction;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class Soft {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		ReferenceQueue<SoftObject> queue = new ReferenceQueue<SoftObject>();
		SoftReference<SoftObject> soft = new SoftReference<SoftObject>(new SoftObject() , queue);
		
		while(queue.remove(5000) == null) {
			System.gc();
		}
		System.out.println("Get soft reference");
	}

	private static class SoftObject {
		
	}
}
