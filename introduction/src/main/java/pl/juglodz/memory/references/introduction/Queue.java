package pl.juglodz.memory.references.introduction;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

public class Queue {
    private static void createRefs(List<Reference<String>> list, ReferenceQueue<String> weakqueue, ReferenceQueue<String> softqueue,
	    ReferenceQueue<String> phantomqueue) {
	String random = RandomStringUtils.random(65535);
	final Reference<String> weak = new WeakReference<String>(random, weakqueue);
	final Reference<String> soft = new SoftReference<String>(random, softqueue);
	final Reference<String> phant = new PhantomReference<String>(random, phantomqueue);
	list.add(weak);
	list.add(soft);
	list.add(phant);
    }

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
	final ReferenceQueue<String> weakqueue = new ReferenceQueue<String>();
	final ReferenceQueue<String> softqueue = new ReferenceQueue<String>();
	final ReferenceQueue<String> phantomqueue = new ReferenceQueue<String>();

	String random = RandomStringUtils.random(65535);
	final Reference<String> weak = new WeakReference<String>(random, weakqueue);
	final Reference<String> soft = new SoftReference<String>(random, softqueue);
	final Reference<String> phant = new PhantomReference<String>(random, phantomqueue);

	random = null;

	new Thread(new CleanupRunnable(weakqueue)).start();
	new Thread(new CleanupRunnable(softqueue)).start();
	new Thread(new CleanupRunnable(phantomqueue)).start();

	Thread thread = new Thread(new Runnable() {
	    public void run() {
		ArrayList<Reference<String>> arrayList = new ArrayList<Reference<String>>();
		while (true) {
		    createRefs(arrayList, weakqueue, softqueue, phantomqueue);
		    System.gc();
		}
	    }
	});
	thread.setDaemon(true);
	thread.start();
    }

    private static class CleanupRunnable implements Runnable {
	private ReferenceQueue<String> q;

	public CleanupRunnable(ReferenceQueue<String> q) {
	    this.q = q;
	}

	public void run() {
	    try {
		Reference<? extends String> remove = q.remove();
		System.out.println("Removed " + remove.getClass().getSimpleName());
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}

    }

}
