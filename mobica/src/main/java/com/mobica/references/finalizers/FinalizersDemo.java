package com.mobica.references.finalizers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FinalizersDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinalizersDemo.class);
    public static void main(String[] args) throws Exception {
	int max = 0;
	for(int i = 0; i < 1000; ++i) {
	    ObjectWithFinalize finalizer = new ObjectWithFinalize();
	    
	    int count = finalizer.getCount();
	    
	    if (count > max) {
		max = count;
		LOGGER.warn("New max {}", max);
	    }
	}
    }
}
