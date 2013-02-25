package com.mobica.references.finalizers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.Memory;


public class CrashingMemory extends Memory {
    public static final Logger LOGGER = LoggerFactory.getLogger(CrashingMemory.class);
    public CrashingMemory(int i) {
	super(i);
    }

    @Override
    protected void finalize() {
	LOGGER.debug("CrashingMemory is finalized");
	super.finalize();
    }
}
