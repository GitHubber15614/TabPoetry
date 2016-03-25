package com.hawk.poetry.util;

import android.os.Handler;

public class HandlerUtil {
	private static Handler handler=new Handler();
	
	public static void post(Runnable runnableObj){
		handler.post(runnableObj);
	}
	
	public static void postDelayed(Runnable runnableObj,int delayMillis){
		handler.postDelayed(runnableObj, delayMillis);
	}
}
