package com.digicode.dodobattery.provider

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.*
import android.graphics.Bitmap
import android.widget.RemoteViews
import androidx.core.graphics.drawable.toBitmap
import com.digicode.dodobattery.R
import com.digicode.dodobattery.battery.BatteryReceiver
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.data.model.WidgetsFactory
import com.digicode.dodobattery.data.model.enums.LockedStatus
import com.digicode.dodobattery.data.preferences.PreferencesFacade
import com.digicode.dodobattery.graphic.ImageBuilder
import com.digicode.dodobattery.view.ui.activity.SingleActivity


open class BaseWidgetProvider :AppWidgetProvider(){

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        WidgetRemoteViewsFactory.updateWidget(context)
    }
}


open class WidgetRemoteViewsFactory(){


    companion object{

        fun updateWidget(context: Context){

            BatteryReceiver().getBatteryStatus(context) { level: Int, isCharging: Boolean ->

                var widget = PreferencesFacade(context.applicationContext).selectedWidget.getValue()

                if(widget == null){
                    widget = WidgetsFactory.createDefaultWidget()
                }

                val appWidgetManager = AppWidgetManager.getInstance(context)

                val componentLarge = ComponentName(
                    context.applicationContext,
                    LargeWidgetProvider::class.java
                )

                val componentSmall = ComponentName(
                    context.applicationContext,
                    SmallWidgetProvider::class.java
                )

                val remoteViews = createRemoteViews(context,widget, Pair(level, isCharging))
                appWidgetManager.updateAppWidget(componentSmall, remoteViews)
                appWidgetManager.updateAppWidget(componentLarge, remoteViews)
            }

        }

        private fun createRemoteViews(context: Context, widget: Widget, batteryStatus: Pair<Int, Boolean>) : RemoteViews {


            val views = RemoteViews(context?.packageName, R.layout.app_widget)

            val intent = Intent(context, SingleActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            views.setOnClickPendingIntent(R.id.widgetImageView, pendingIntent)

            val widgetImage = ImageBuilder()
                .setContext(context)
                .setWidget(widget)
                .setBatteryIndicators(batteryStatus)
                .showWatermark(widget.lockedStatus == LockedStatus.LOCKED)
                .build()

            val bitmap = widgetImage.toBitmap()
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, (bitmap.width * 3).toInt(),
                (bitmap.height * 3).toInt(),false);

            views.setImageViewBitmap(R.id.widgetImageView, scaledBitmap)

            return views
        }
    }
}

