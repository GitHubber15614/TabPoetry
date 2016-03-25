package com.hawk.poetry.application;

import android.app.Application;

public class AppApplication extends Application {
	private static AppApplication mInstance;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance=this;
	}
	
	public static AppApplication getInstance(){
		return mInstance;
	}
}
