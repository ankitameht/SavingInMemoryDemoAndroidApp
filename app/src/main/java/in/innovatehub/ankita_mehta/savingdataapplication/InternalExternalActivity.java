package in.innovatehub.ankita_mehta.savingdataapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class InternalExternalActivity extends AppCompatActivity {
    EditText mSaveFileText;
    Button mSaveInInternal;
    Button mSaveInExternal;

    File loadFile;
    String filenameSample = "sampleExternal.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_external);

        mSaveFileText = (EditText) findViewById(R.id.FileInputText);
        final String text = String.valueOf(mSaveFileText.getText());

        mSaveInInternal = (Button) findViewById(R.id.SaveInInternal_Button);
        mSaveInInternal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String filename = "internalFile"+ SystemClock.currentThreadTimeMillis()+".txt";
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(text.getBytes());
                    outputStream.close();
                    Toast.makeText(InternalExternalActivity.this, "Wow! you just saved in Internal Memory!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mSaveInExternal = (Button) findViewById(R.id.SaveInExternal_Button);
        mSaveInExternal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String filename = "externalFile"+ SystemClock.currentThreadTimeMillis()+".txt";
                File file;
                try {
                    file = new File(filename);
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                filenameSample = filename;
                FileOutputStream outputStream;
                try {
                    outputStream = new FileOutputStream(filename, false);
                    outputStream.write(text.getBytes());
                    outputStream.close();
                    Toast.makeText(InternalExternalActivity.this, "Wow! you just saved in External Memory!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if(!checkSpace()) {
            Toast.makeText(InternalExternalActivity.this, "Space not available! :(", Toast.LENGTH_SHORT).show();
        } else {
            String filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            loadFile = new File(filepath + "/", filenameSample);//File(getExternalFilesDir(filepath), filename);
        }

    }
    public boolean checkSpace() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
