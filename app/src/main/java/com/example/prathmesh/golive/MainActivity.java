package com.example.prathmesh.golive;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button golive = (Button)findViewById(R.id.golive);

        golive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateMobileLiveIntent(getApplicationContext());
            }
        });

    }


    // To check if the device can Resolve Mobile Live Intent
    private boolean canResolveMobileLiveIntent(Context context){
        Intent intent = new Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM")
                .setPackage("com.google.android.youtube");
        PackageManager pm = context.getPackageManager();
        List resolveInfo = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }


    // If canResolveMobileLiveIntent returns True, launches the Live Stream Activity else prompts the user to install or Upgrade YouTube
    private void validateMobileLiveIntent(Context context) {
        if (canResolveMobileLiveIntent(context)) {
            // Launch the live stream Activity
           startMobileLive(getApplicationContext());
        }

        else Toast.makeText(context, "Install YouTube or Upgrade !", Toast.LENGTH_LONG).show();
    }

    private void startMobileLive(Context context){
        Intent mobileLiveIntent = createMobileLiveIntent(context, "Streaming via ...");
        startActivity(mobileLiveIntent);
    }

    private Intent createMobileLiveIntent(Context context, String description) {
        Intent intent = new Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM")
                .setPackage("com.google.android.youtube");
        Uri referrer = new Uri.Builder()
                .scheme("android-app")
                .appendPath(context.getPackageName())
                .build();

        intent.putExtra(Intent.EXTRA_REFERRER, referrer);
        if (!TextUtils.isEmpty(description)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, description);
        }
        return intent;
    }

}
