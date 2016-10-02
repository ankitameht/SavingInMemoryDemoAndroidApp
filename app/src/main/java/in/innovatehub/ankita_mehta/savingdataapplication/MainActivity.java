package in.innovatehub.ankita_mehta.savingdataapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainAcitivity";
    public static final String PREFS_NAME = "MyPrefsFile";
    //User puts phone on silent mode
    private boolean sharedPrefValue = false;
    private Button mRegisterButton;
    private Button mMemoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegisterButton = (Button) findViewById(R.id.button2);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LeaderBoard_Activity.class);
                startActivity(intent);
            }
        });

        Log.d("TEST", "Value " + sharedPrefValue);
        SharedPreferences settings = getSharedPreferences(getString(R.string.prefs_file_name), Context.MODE_PRIVATE);
        sharedPrefValue = settings.getBoolean("sharedPrefValue", false);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, "Current Preference is " + sharedPrefValue, duration);
        toast.show();

        mRegisterButton = (Button) findViewById(R.id.button2);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LeaderBoard_Activity.class);
                startActivity(intent);
            }
        });

        mMemoryButton = (Button) findViewById(R.id.button3);
        mMemoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InternalExternalActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStop(){
        super.onStop();

        SharedPreferences settings = getSharedPreferences("PREFS_FILE", Context.MODE_APPEND);
        SharedPreferences.Editor editor  = settings.edit();
        editor.putBoolean("sharedPrefValue", true);
        editor.commit();

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, "Current Preference is "+sharedPrefValue, duration);
        toast.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onresume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onrestart");
    }
}
