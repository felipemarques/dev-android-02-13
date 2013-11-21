package com.unopar.monitordeservico.svc;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.unopar.monitordeservico.MainActivity;
import com.unopar.monitordeservico.R;
import com.unopar.monitordeservico.data.CentralMonitor;
import com.unopar.monitordeservico.task.TaskBase;

public class MonitorService extends Service {
	public class LocalBinder extends Binder {
		public MonitorService getService() {
			return MonitorService.this;
		}
	}
	
	private final String LOG_TAG = "Svc";
	private LocalBinder _binder = new LocalBinder();
	private CentralMonitor _centralMonitor;
	private NotificationManager _notificationManager;
	
	private static final int NOTIFICATION_ID = 0x1000;
	public static final String ACTION_REGISTER_WIDGET = 
		"com.unopar.monitordeservico.svc.monitorservice.action_registre_widget";
	
	private TaskBase _taskUpdateWidgets;
	
	@Override
	public IBinder onBind(Intent intent) {		
		return _binder;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(LOG_TAG, "onStartCommand");
		
		if(intent != null) {
			processaIntent(intent);
		}
		
		return START_STICKY;
	}
	
	@Override
	public void onCreate() {		
		super.onCreate();
		
		Log.i(LOG_TAG, "onCreate");
		
		_centralMonitor = new CentralMonitor();
		
		_taskUpdateWidgets = new TaskBase(
				new TimerTask() {					
					@Override
					public void run() {	
						_centralMonitor.refresh();
						updateWidgets();
					}
				}, 1000);
		
		_taskUpdateWidgets.start();
		
		_notificationManager = (NotificationManager) 
				getSystemService(Context.NOTIFICATION_SERVICE);	
		
		addNotificacao();
	}
	
	
@Override
	public void onDestroy() {		
		super.onDestroy();
		
		_taskUpdateWidgets.stop();
		
		_notificationManager.cancel(NOTIFICATION_ID);
		
		Log.i(LOG_TAG, "onDestroy");
	}

	public CentralMonitor getCentralMonitor() {
		return _centralMonitor;
	}
	
	private void updateWidgets() {
		Integer[] widgetIds = _widgets.toArray(new Integer[0]);
		for(Integer widgetId : widgetIds) {
			updateWidget(widgetId);
		}
	}
	
	private void processaIntent(Intent intent) {
		String action = intent.getAction();
		if(action != null && action.equals(ACTION_REGISTER_WIDGET)) {
			int widgetId = intent.getIntExtra(
					AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
			if(widgetId == -1) {
				return;
			}
			
			updateWidget(widgetId);
			_widgets.add(widgetId);
		}
	}
	
	private List<Integer> _widgets = new ArrayList<Integer>();
	
	private void updateWidget(int widgetId) {
		AppWidgetManager appWidgetMan = AppWidgetManager.getInstance(this);
		RemoteViews views = new RemoteViews(
				getPackageName(), R.layout.widget_layout);
		
		views.setTextViewText(R.id.txvListaOnline, 
				_centralMonitor.getOnlineAsString());	
		views.setTextViewText(R.id.txvListaOffline, 
				_centralMonitor.getOfflineAsString());
		appWidgetMan.updateAppWidget(widgetId, views);
	}

	private void addNotificacao() {
		PendingIntent contentIntent = 
				PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class),
					PendingIntent.FLAG_CANCEL_CURRENT);
		
		Notification nt = new NotificationCompat
				.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentText("Serviço iniciado..")
				.setWhen(System.currentTimeMillis())
				.setAutoCancel(false)	
				.setContentIntent(contentIntent)
				.build(); 
		
		_notificationManager.notify(NOTIFICATION_ID, nt);
		
	}
}
