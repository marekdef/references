package com.mobica.references.finalizers;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;

public class TrackingClosable extends DataInputStream {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrackingClosable.class);
    private String stackTraceAsString;

    public TrackingClosable(InputStream in) {
	super(in);

	stackTraceAsString = Throwables.getStackTraceAsString(new Exception());
    }

    private boolean isClosed = false;

    public void close() throws IOException {
	isClosed = true;
	super.close();
    }

    @Override
    protected void finalize() throws Throwable {
	if (!isClosed) {
	    LOGGER.error("You forgot to close stream instantiated here {}", stackTraceAsString);
	}

	super.finalize();
    }

    public static void main(String[] args) {
	TrackingClosable trackingClosable;
	try {
	    trackingClosable = new TrackingClosable(new FileInputStream("/etc/hosts"));
	    String readUTF = trackingClosable.readLine();
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	System.gc();
	System.runFinalization();
	System.gc();
    }
}
