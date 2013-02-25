package com.mobica.references.finalizers;


public class CrashingFinalizer {
    private final CrashingMemory memory = new CrashingMemory(512);
    public CrashingFinalizer() {
	memory.setString(0, "Hello world");
    }
    @Override
    protected void finalize() throws Throwable {
	memory.setString(0, "I am taking VM with me");
    }
    
    public static void main(String[] args) {
	for(int i = 0; i < 10; ++i) {
	    new CrashingFinalizer();
	    System.gc();
	    System.runFinalization();
	    System.gc();
	}
    }
}
