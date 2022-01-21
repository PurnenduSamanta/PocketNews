package com.purnendu.PocketNews.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.purnendu.PocketNews.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class NotificationHelper {
    private final Context context;
    private final String title,url;
    private final Intent intent;
    private final int reqCode;
    private PendingIntent pendingIntent = null;

    public NotificationHelper(Context context, String title,String url, Intent intent, int reqCode) {
        this.context = context;
        this.title = title;
        this.intent = intent;
        this.reqCode = reqCode;
        this.url=url;
        ShowNotification();
    }
    private  void  ShowNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_IMMUTABLE);
        }
        if(pendingIntent==null)
            return;
        String CHANNEL_ID = "NewsUpdate";// The id of the channel.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.communication).setContentTitle("PocketNews")
                .setContentText(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(title))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "PocketNews";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }


        Picasso.get()
                .load(url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
                        notificationManager.notify(reqCode, notificationBuilder.build());
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        notificationBuilder.setLargeIcon( BitmapFactory.decodeResource(context.getResources(),R.mipmap.icon));
                        notificationManager.notify(reqCode, notificationBuilder.build());
                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

    }

}
