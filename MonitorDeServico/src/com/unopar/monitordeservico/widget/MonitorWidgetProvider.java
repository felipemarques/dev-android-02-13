package com.unopar.monitordeservico.widget;

import com.unopar.monitordeservico.R;
import com.unopar.monitordeservico.svc.MonitorService;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MonitorWidgetProvider extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int count = appWidgetIds.length;

		for (int i = 0; i < count; i++) {
			int appWidgetId = appWidgetIds[i];

			RemoteViews views = new RemoteViews(
					context.getPackageName(),
					R.layout.widget_layout);			
			
			appWidgetManager.updateAppWidget(appWidgetId, views);
			
			Intent intentCallService = new Intent(context, 
					MonitorService.class);
			intentCallService.setAction(
					MonitorService.ACTION_REGISTER_WIDGET);
			intentCallService.putExtra(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					appWidgetId);
			
			context.startService(intentCallService);
		}

		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
}
