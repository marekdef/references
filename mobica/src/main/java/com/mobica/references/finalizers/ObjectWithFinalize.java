package com.mobica.references.finalizers;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 public class ObjectWithFinalize {
	public static final Logger LOGGER = LoggerFactory.getLogger(ObjectWithFinalize.class);
	public static final AtomicInteger counter = new AtomicInteger(0);
	private String random;
	private ObjectWithFinalize child;
	
	public ObjectWithFinalize() {
	    int addAndGet = counter.addAndGet(1);
	    random = RandomStringUtils.randomAlphanumeric(65535);
	    if(!Character.isDigit(random.charAt(0))) {
		child = new ObjectWithFinalize();
	    }
	    
	    LOGGER.debug("Created {} Number of objects {}", toString(), addAndGet);
	}
	
	@Override
	protected void finalize() throws Throwable {
	    LOGGER.debug("Finalized {} Number of objects {}", toString(), counter.getAndDecrement());
	}
	
	public int getCount() {
	    return counter.get();
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder(random.subSequence(0, 1));
	    if(child != null)
		sb.append(child.toString());
	    return sb.toString();
	}
    }