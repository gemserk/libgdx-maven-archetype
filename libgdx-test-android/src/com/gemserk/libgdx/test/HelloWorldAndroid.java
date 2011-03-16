package com.gemserk.libgdx.test;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class HelloWorldAndroid extends AndroidApplication {
	@Override public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize(new HelloWorld(), false);		
	}
}