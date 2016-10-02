package in.innovatehub.ankita_mehta.savingdataapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LeaderBoard_Activity extends AppCompatActivity {

    EditText mEditId;
    EditText mEditName;
    EditText mEditScore;
    Button mSaveButton;
    Button mShowButton;
    TextView mShowText;
    Button mUpdateButton;
    Button mRemoveButton;
    Button mDropButton;

    final String TAG="Lead";
    private SQLiteDatabase db;

    final String l_id="id";
    final String l_name="name";
    final String l_score="score";
    final String[] from = new String[]{l_id, l_name, l_score};
    final int[] to = new int[]{R.id.textView_id, R.id.textView_name, R.id.textView_score};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board_);

        createDatabase();

        mEditId = (EditText) findViewById(R.id.editText11);
        mEditName = (EditText) findViewById(R.id.editText2);
        mEditScore = (EditText) findViewById(R.id.editText3);


        mSaveButton = (Button) findViewById(R.id.saveInSQL);

        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                insertIntoDB();
            }
        });

        mShowButton = (Button) findViewById(R.id.showDbFromSQL);
        mShowButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                readFromDBB();
            }
        });

        mUpdateButton = (Button) findViewById(R.id.updateInSQL);
        mUpdateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                updateInDB();
            }
        });

        mRemoveButton = (Button) findViewById(R.id.DeleteFromSQL);
        mRemoveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                deleteInDB();
            }
        });

        mDropButton = (Button) findViewById(R.id.DropDBFromSQL);
        mDropButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dropInDB();
            }
        });

    }

    protected void createDatabase(){
        db=openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS persons(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, leader_id VARCHAR, name VARCHAR,score VARCHAR);");
    }

    protected void insertIntoDB(){

        String l_id = mEditId.getText().toString().trim();
        String name = mEditName.getText().toString().trim();
        String score = mEditScore.getText().toString().trim();
        if(name.equals("") || score.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }
        String query = "INSERT INTO persons (leader_id,name,score) VALUES('"+l_id+"', '"+name+"', '"+score+"');";
        db.execSQL(query);
        Toast.makeText(getApplicationContext(),"Saved Successfully", Toast.LENGTH_LONG).show();
    }

    protected void readFromDBB(){
        Cursor resultSet = db.rawQuery("Select * from persons",null);

        try {
            String fin = "";
            while (resultSet.moveToNext()) {
                String l_id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String score = resultSet.getString(3);

                fin += l_id+", "+name+", "+score+"\n";
            }
            mShowText = (TextView) findViewById(R.id.textView_result);
            mShowText.setText(fin);

        } finally {
            resultSet.close();
        }
    }

    protected void updateInDB(){

        String l_id = mEditId.getText().toString().trim();
        String name = mEditName.getText().toString().trim();
        String score = mEditScore.getText().toString().trim();
        if(name.equals("") || score.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }else {
            String Query = "Select * from persons where name = '" + name + "';";
            Log.d(TAG, Query);
            Cursor cursor = db.rawQuery(Query, null);
            if (cursor.getCount() <= 0) {
                //not in db
                Toast.makeText(getApplicationContext(), "Data not found in DB. Kindly register yourself", Toast.LENGTH_LONG).show();
            } else {
                //in db
                Toast.makeText(getApplicationContext(), "Data found.", Toast.LENGTH_LONG).show();
                String query = "UPDATE persons SET score = '" + score + "' WHERE name = '" + name + "';";
                db.execSQL(query);
            }
            cursor.close();
        }
    }

    private void deleteInDB() {
        String l_id = mEditId.getText().toString().trim();
        String name = mEditName.getText().toString().trim();
        String score = mEditScore.getText().toString().trim();
        if(name.equals("") || score.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }else {
            String Query = "Select * from persons where name = '" + name + "';";
            Log.d(TAG, Query);
            Cursor cursor = db.rawQuery(Query, null);
            if (cursor.getCount() <= 0) {
                //not in db
                Toast.makeText(getApplicationContext(), "Data not found in DB. Kindly register yourself", Toast.LENGTH_LONG).show();
            } else {
                //in db
                Toast.makeText(getApplicationContext(), "Data found.", Toast.LENGTH_LONG).show();
                String query = "DELETE FROM persons WHERE name = '" + name + "';";
                db.execSQL(query);
            }
            cursor.close();
        }
    }
    private void dropInDB() {
    }
}
