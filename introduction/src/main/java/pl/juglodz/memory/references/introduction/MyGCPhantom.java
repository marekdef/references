package pl.juglodz.memory.references.introduction;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

    public class MyGCPhantom {
        public static void main(String[] args) throws InterruptedException {
            ReferenceQueue<GCPhantomObject> phantomQueue = new ReferenceQueue<GCPhantomObject>();
            new GCPhantomThread(new PhantomReference<GCPhantomObject>(new GCPhantomObject(), phantomQueue), phantomQueue, "Phantom").start();
            System.gc();
            System.out.println("main thread done ...");
        }
    }

    class GCPhantomObject {
        @Override
        protected void finalize() {
            System.out.println("GCPhantom finalized at " + System.nanoTime());
        }
    }

    class GCPhantomThread extends Thread {
        private ReferenceQueue<GCPhantomObject> referenceQueue;
        private String name;
        private PhantomReference<GCPhantomObject> pr;

        GCPhantomThread(PhantomReference<GCPhantomObject> pr, ReferenceQueue<GCPhantomObject> referenceQueue, String name) {
            this.referenceQueue = referenceQueue;
            this.name = name;
            this.pr = pr;
        }

        @Override
        public void run() {
            try {
                while (referenceQueue.remove(5000) == null) {
                    System.gc();
                }
                System.out.println(name + " found at " + System.nanoTime());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }