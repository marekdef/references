package com.mobica.references.demo;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 * -Xmx2M -verbose:gc  -Dorg.slf4j.simpleLogger.defaultLogLevel=debug
 */
public class AllReferences {
    public static final Logger LOGGER = LoggerFactory.getLogger(AllReferences.class);
    public static void main(String[] args) throws InterruptedException {
	List<String> arrayList = new ArrayList<String>();

	WeakReference<String> weakRef = new WeakReference<String>(RandomStringUtils.randomAlphanumeric(65535));
	Reference<String> softRef = new SoftReference<String>(RandomStringUtils.randomAlphanumeric(65535));
	ReferenceQueue<MyString> phantomQueue = new ReferenceQueue<MyString>();
	PhantomReference<MyString> phantomRef = new PhantomReference<MyString>(new MyString(RandomStringUtils.randomAlphanumeric(65535)), phantomQueue);

	while (true) {
	    arrayList.add(RandomStringUtils.randomAlphanumeric(255));

	    long freeMemory = Runtime.getRuntime().freeMemory();
	    long totalMemory = Runtime.getRuntime().totalMemory();

	    LOGGER.debug("{}% free of total {}",  100 * freeMemory / totalMemory, freeMemory);
	    LOGGER.debug("Weak    " + (weakRef.get() != null ? "has" : "does not have") + " a referrent");
	    LOGGER.debug("Soft    " + (softRef.get() != null ? "has" : "does not have") + " a referrent");
	    LOGGER.debug("Phantom " + (phantomQueue.poll() != null ? "is" : "is not") + " on a queue");
	    
	    System.gc();
	}

    }
}
