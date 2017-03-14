package com.shubham.roommatebudget;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by admin on 7/10/2016.
 */
public class KindofLogin extends AppCompatActivity
{
    private Boolean first_password=null;
    TextView tv;
    EditText name1,name2,pass1,pass2; String n1,n2,pa1,pa2; Button next; long total1=0,total2=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_reset);
        initialize();
        tv=(TextView)findViewById(R.id.label);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/fontone.otf");
        tv.setText("Credentials");
        tv.setTypeface(typeface);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n1=name1.getText().toString();
                n2=name2.getText().toString();
                pa1=pass1.getText().toString();
                if(n1.isEmpty() || n2.isEmpty() || pa1.isEmpty() )
                {
                    Toast.makeText(getApplicationContext(),"Possible Empty Fields",Toast.LENGTH_LONG).show();
                }
                else
                {
                 if(pa1.length()<=5 )
                 {
                     Toast.makeText(getApplicationContext(),"Password Length must be above 5 characters",Toast.LENGTH_LONG).show();
                 }
                    else
                 {
                       isFirstTime();
                     Intent i=new Intent(KindofLogin.this,MainActivity.class);
                     i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(i);
                     overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                 }
                }
            }
        });
    }
    private void isFirstTime()
    {
     SharedPreferences sharedPreferences=this.getSharedPreferences("valuestore",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("name1",n1);
        editor.putString("name2",n2);
        editor.putString("pass1",pa1);
        editor.putLong("total1",total1);
        editor.putLong("total2",total2);
        editor.putBoolean("passwordoption",true);
        editor.commit();
    }
    public void  initialize()
    {
        name1=(EditText)findViewById(R.id.name1);
        name2=(EditText)findViewById(R.id.name2);
        pass1=(EditText)findViewById(R.id.pass1);
        next=(Button)findViewById(R.id.buttonnext);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        if (id == R.id.reset_namedetails) {

        }
        if (id == R.id.action_resetmonth) {
           Toast.makeText(getApplicationContext(),"First Login to Proceed",Toast.LENGTH_LONG).show();
            //also delete the entire list of data in SQLite
        }
        if(id==R.id.rate)
        {
            Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
            }
            Toast.makeText(getApplicationContext(), "Thank You..", Toast.LENGTH_LONG).show();
        }
        if (id == R.id.exit) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Are you sure?");
            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    finish();
                }
            });
            dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
            AlertDialog b = dialogBuilder.create();
            b.show();
        }
        if (id == R.id.password123)
        {
            Toast.makeText(getApplicationContext(),"First Login to Proceed",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
