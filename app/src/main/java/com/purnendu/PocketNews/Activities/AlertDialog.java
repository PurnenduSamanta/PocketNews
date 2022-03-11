package com.purnendu.PocketNews.Activities;

import android.content.Context;
import android.content.Intent;


public class AlertDialog  {
    Context context;
    String message;

    public AlertDialog(Context context, String message) {
        this.context = context;
        this.message = message;

        android.app.AlertDialog.Builder abuilder = new android.app.AlertDialog.Builder(context);
        abuilder.setTitle(message);
        abuilder.setCancelable(false);
        abuilder.setPositiveButton("Refresh", (dialog, i) -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);

        });
        abuilder.setNegativeButton("Exit", (dialog, i) -> {
            Intent intent = new Intent(context, QuitApplicationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        });
        android.app.AlertDialog dialog = abuilder.create();
        dialog.show();
    }
}
