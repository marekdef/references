package pl.juglodz.memory.references.introduction;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Hello world!
 * 
 */
public class AllReferences {
    public static void main(String[] args) throws InterruptedException {
	List<String> arrayList = new ArrayList<String>();

	WeakReference<String> weakRef = new WeakReference<String>(RandomStringUtils.randomAlphanumeric(65535));
	Reference<String> softRef = new SoftReference<String>(RandomStringUtils.randomAlphanumeric(65535));
	ReferenceQueue<String> phantomQueue = new ReferenceQueue<String>();
	PhantomReference<String> phantomRef = new PhantomReference<String>(RandomStringUtils.randomAlphanumeric(65535), phantomQueue);

	int counter = 0;
	while (true) {
	    arrayList.add(RandomStringUtils.randomAlphanumeric(255));

	    long freeMemory = Runtime.getRuntime().freeMemory();
	    long totalMemory = Runtime.getRuntime().totalMemory();

	    counter++;
	    System.out.println(String.format("%d. Free %d total %d %.2f", counter, freeMemory, totalMemory, 100.0 * freeMemory / totalMemory));
	    System.out.println("Weak    " + (weakRef.get() != null ? "has" : "does not have") + " a referrent");
	    System.out.println("Soft    " + (softRef.get() != null ? "has" : "does not have") + " a referrent");
	    System.out.println("Phantom " + (phantomQueue.poll() != null ? "is" : "is not") + " on a queue");
	}

    }
}
