package com.unopar.projetoteste;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class MinhaAplicacao extends Application {
	
	private static MinhaAplicacao _instance;
	
	public static MinhaAplicacao get() {
		return _instance;
	}	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Log.i("Info", "Aplicação foi criada");
	}
	
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	public boolean emSessao() {
		return true;
	}

}
