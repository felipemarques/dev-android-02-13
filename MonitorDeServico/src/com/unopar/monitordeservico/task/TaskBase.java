package com.unopar.monitordeservico.task;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;

public class TaskBase {
	private Timer _timer;
	private TimerTask _task;
	private long _elapseTime;
	
	private boolean _active;
	private Object _sync = new Object();
	
	private Handler _handler;
	
	public TaskBase(TimerTask task, long elapseTime) {
		_task = task;
		_elapseTime = elapseTime;
		
		_handler = new Handler();
	}
	
	public void start() {
		synchronized (_sync) {
			if(_active){
				return;
			}
			
			_active = true;
			_timer = new Timer();
			_timer.schedule(new TimerTask() {				
				@Override
				public void run() {
					_handler.post(_task);					
				}
			}, _elapseTime, _elapseTime);
		}
	}
	
	public void stop() {
		synchronized (_sync) {
			if(!_active) {
				return;
			}
			
			_active = false;
			_timer.cancel();
		}
	}

}
