package pl.juglodz.memory.references.introduction;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

public class Phantom {
    public static void main(String[] args) throws InterruptedException {
	List<PhantomReference<Foo>> phantoms = new ArrayList<PhantomReference<Foo>>();
	ReferenceQueue<Foo> queue = new ReferenceQueue<Foo>();
	Thread thread = new Thread(new CleanupThread(phantoms, queue));
	
	for (int i = 0; i < 100; ++i) {
	    Foo foo = new Foo();
	    phantoms.add(new PhantomReference<Foo>(foo, queue));
	}
	
	thread.start();
    }

    private static class Foo {
	@Override
	protected void finalize() throws Throwable {
	    System.out.println("Foonalized");
	    super.finalize();
	}
    }

    private static class CleanupThread implements Runnable {
	private ReferenceQueue<Foo> queue;
	private List<PhantomReference<Foo>> phantoms;

	public CleanupThread(List<PhantomReference<Foo>> phantoms, ReferenceQueue<Foo> queue) {
	    this.queue = queue;
	    this.phantoms = phantoms;
	}

	public void run() {
	    Reference<? extends Foo> removedFoo = null;
	    while (!phantoms.isEmpty()) {
		try {
		    removedFoo = queue.remove(500);
		} catch (IllegalArgumentException e) {
		} catch (InterruptedException e) {
		}
		System.gc();
		this.phantoms.remove(removedFoo);
		System.out.println("Removed " + removedFoo);
	    }

	}
    }
}
