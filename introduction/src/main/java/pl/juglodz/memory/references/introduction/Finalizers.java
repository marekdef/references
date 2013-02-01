package pl.juglodz.memory.references.introduction;

public class Finalizers {

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
	new Finalize();
	Thread.sleep(1000);
	System.gc();
	Thread.sleep(1000);
	System.gc();
	Thread.sleep(1000);
    }

    private static class Finalize {
	@Override
	protected void finalize() throws Throwable {
	    System.out.println("I am being finalized " + System.identityHashCode(this));
	    super.finalize();
	}
    }
}
