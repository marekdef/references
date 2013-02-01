package com.mobica.references;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	List<Reference<Object>> list = new ArrayList<Reference<Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		findViewById(R.id.buttonSoft).setOnClickListener(this);
		findViewById(R.id.buttonWeak).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private String getName() {
		return MainActivity.class.getName();
	}

	@Override
	public void onClick(View v) {
		if (v.equals(findViewById(R.id.buttonSoft))) {
			SoftReference<Object> ref = new SoftReference<Object>(new Object());
			list.add(ref);
		}
		if (v.equals(findViewById(R.id.buttonWeak))) {
			WeakReference<Object> ref = new WeakReference<Object>(new Object());
			list.add(ref);
		}
		if(v.equals(findViewById(R.id.buttonGC))) {
			System.gc();
			
		}
		recalculate();
	}
	
	private void recalculate() {
		int soft = 0;
		int weak = 0;
		for(Reference<Object> r : list) {
			if(r.get() == null)
				continue;
			
			if (r instanceof SoftReference)
				soft++;
			
			else if (r instanceof WeakReference)
				weak++;
		}
		((TextView)findViewById(R.id.textView)).setText(String.format("Soft %d Weak %d",soft,weak));
	}
	

}
