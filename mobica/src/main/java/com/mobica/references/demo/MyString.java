package com.mobica.references.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyString {
    public static final Logger LOGGER = LoggerFactory.getLogger(MyString.class);
    private final String randomAlphanumeric;
    public MyString(String randomAlphanumeric) {
	this.randomAlphanumeric = randomAlphanumeric;
    }
    
//    @Override
//    protected void finalize() throws Throwable {
//	LOGGER.debug("MyString finalized");
//    }

}
