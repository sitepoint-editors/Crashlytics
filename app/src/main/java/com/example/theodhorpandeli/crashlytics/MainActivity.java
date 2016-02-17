package com.example.theodhorpandeli.crashlytics;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
    private EditText userIdentifierEditText, userEmailEditText, userNameEditText;
    private String userIdentifier, userEmail, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void forceCrash(View view) {
        throw new RuntimeException("This is a simple crash");
    }

    public void logUser(String userIdentifier, String userEmail, String userName) {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier(userIdentifier);
        Crashlytics.setUserEmail(userEmail);
        Crashlytics.setUserName(userName);
    }

    public void showUserDialog(View view){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog_layout, null);
        userIdentifierEditText = (EditText) alertLayout.findViewById(R.id.userIdentifier);
        userEmailEditText = (EditText) alertLayout.findViewById(R.id.userEmail);
        userNameEditText = (EditText) alertLayout.findViewById(R.id.userName);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Information");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // code for matching password
                userIdentifier = userIdentifierEditText.getText().toString();
                userEmail = userEmailEditText.getText().toString();
                userName = userNameEditText.getText().toString();
                if(!userIdentifier.equals("") && !userEmail.equals("") && !userName.equals("")){
                    logUser(userIdentifier, userEmail, userName);
                    Toast.makeText(getBaseContext(), "Sending extra information...", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException("This is a crash with extra information");
                }
                Toast.makeText(getBaseContext(), "Information not send! Please complete all fields...", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
