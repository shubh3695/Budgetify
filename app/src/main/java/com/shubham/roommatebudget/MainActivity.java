package com.shubham.roommatebudget;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
public class MainActivity extends AppCompatActivity {
    EditText i1, i2, c1, c2;
    String item1, item2, cost1, cost2, name1, name2, pass;
    long total1, total2;
    ImageButton p1, p2, b1, b2,result;
    TextView t1, t2;
    DatabaseHelper db;
    Boolean password=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();
        testShare();
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/fontone.otf");
        if(name1.trim().contains(" "))
        t1.setText((name1.split(" ")[0])+"\n"+((name1.split(" ")[1])));
        else t1.setText(name1);
        if(name2.trim().contains(" "))
            t2.setText((name2.split(" ")[0])+"\n"+((name2.split(" ")[1])));
        else
        t2.setText(name2);
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
        db = new DatabaseHelper(MainActivity.this);
        final SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("valuestore",MODE_PRIVATE);
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "sss", Toast.LENGTH_LONG).show();
                item1 = i1.getText().toString().trim();
                cost1 = c1.getText().toString().trim();
               // Toast.makeText(MainActivity.this, item1, Toast.LENGTH_LONG).show();
                if (!item1.isEmpty() && !cost1.isEmpty())
                {
                    if(password)
                    showChangeLangDialog(1);
                    else
                    {
                        db.addItem(new DataModel("Roommate1", item1, (cost1 + " " + String.valueOf(System.currentTimeMillis() / 1000))));
                        long add = (sharedPreferences.getLong("total1", 0));
                        add += Long.parseLong(cost1);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putLong("total1", add);
                        editor.commit();
                        testShare();
                        i1.setText("");
                        c1.setText("");
                        Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(MainActivity.this, "Item/Cost cannot be empty", Toast.LENGTH_LONG).show();
                //Toast.makeText(MainActivity.this,String.valueOf(System.currentTimeMillis()/1000),Toast.LENGTH_LONG).show();
            }
        });
        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"sss",Toast.LENGTH_LONG).show();
                item2 = i2.getText().toString();
                cost2 = c2.getText().toString().trim();
                // Toast.makeText(MainActivity.this,item2+" "+cost2,Toast.LENGTH_LONG).show();
                if (!item2.isEmpty() && !cost2.isEmpty())
                {
                    if(password)
                    showChangeLangDialog(2);
                    else
                    {
                        db.addItem(new DataModel("Roommate2", item2, (cost2 + " " + String.valueOf(System.currentTimeMillis() / 1000))));
                        long add = (sharedPreferences.getLong("total2", 0));
                        add += Long.parseLong(cost2);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putLong("total2", add);
                        editor.apply();
                        testShare();
                        i2.setText("");
                        c2.setText("");
                        Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(MainActivity.this, "Item/Cost cannot be empty", Toast.LENGTH_LONG).show();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cost = 0;
                Intent i = new Intent(MainActivity.this, DetailView.class);
                i.putExtra("ops", 1);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
             /*   for(DataModel dm:list)
                {
                    if(dm.getName().equals("Roommate1"))
                        cost+=Integer.parseInt(dm.getCost());
                }
                Toast.makeText(MainActivity.this,cost,Toast.LENGTH_LONG).show(); */
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cost = 0;
                Intent i = new Intent(MainActivity.this, DetailView.class);
                i.putExtra("ops", 2);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long c = total1 - total2;
                String s = "";
                if (c == 0) {
                    s = "You both are monetarily balanced!";
                }
                if (c > 0) {
                    s = name2 + " owes you Rs." + String.valueOf(c);
                }
                if (c < 0) {
                    s = "You owe " + name2 + " Rs." + String.valueOf(Math.abs(c));
                }
                Toast.makeText(getApplicationContext(), "Your Expense: " + String.valueOf(total1) + "\n" + name2 + "'s " + "Expense: " +
                        String.valueOf(total2), Toast.LENGTH_LONG).show();
                Snackbar.make(v, s, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private void testShare()
    {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("valuestore", MODE_PRIVATE);
        String s = "";
        name1 = sharedPreferences.getString("name1", "");
        name2 = sharedPreferences.getString("name2", "");
        pass = sharedPreferences.getString("pass1", "");
        total1 = (sharedPreferences.getLong("total1", 0));
        total2 = (sharedPreferences.getLong("total2", 0));
        password=sharedPreferences.getBoolean("passwordoption",true);

    }
    public void showChangeLangDialog(final int type) {
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("valuestore", MODE_PRIVATE);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
        dialogBuilder.setTitle("Verify yourself!");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (edt.getText().toString().compareTo(pass) == 0) {
                    if (type == 1) {
                        db.addItem(new DataModel("Roommate1", item1, (cost1 + " " + String.valueOf(System.currentTimeMillis() / 1000))));
                        long add = (sharedPreferences.getLong("total1", 0));
                        add += Long.parseLong(cost1);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putLong("total1", add);
                        editor.commit();
                        i1.setText("");
                        c1.setText("");
                        Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_LONG).show();
                    } else {
                        db.addItem(new DataModel("Roommate2", item2, (cost2 + " " + String.valueOf(System.currentTimeMillis() / 1000))));
                        long add = (sharedPreferences.getLong("total2", 0));
                        add += Long.parseLong(cost2);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putLong("total2", add);
                        editor.apply();
                        i2.setText("");
                        c2.setText("");
                        Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_LONG).show();
                    }
                    testShare();
                } else
                    Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    public void initialize() {
        i1 = (EditText) findViewById(R.id.item1st);
        i2 = (EditText) findViewById(R.id.item2nd);
        c1 = (EditText) findViewById(R.id.cost1st);
        c2 = (EditText) findViewById(R.id.cost2nd);
        p1 = (ImageButton) findViewById(R.id.person1);
        p2 = (ImageButton) findViewById(R.id.person2);
        b1 = (ImageButton) findViewById(R.id.button);
        b2 = (ImageButton) findViewById(R.id.button2);
        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.roommate);
        result=(ImageButton)findViewById(R.id.imageButton);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        testShare();
        MenuItem menuItem=menu.findItem(R.id.password123);
        if(password)
            menuItem.setTitle("Disable Password");
        else menuItem.setTitle("Enable Password");
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
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Are you sure?");
            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    db.deleteTable();
                    Intent i = new Intent(MainActivity.this, KindofLogin.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    //  Toast.makeText(getApplicationContext(),editt,Toast.LENGTH_LONG).show();
                }
            });
            dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //pass
                }
            });
            AlertDialog b = dialogBuilder.create();
            b.show();

        }
        if (id == R.id.action_resetmonth) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Reset History?");
            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("valuestore", MODE_PRIVATE);
                    total1 = 0;
                    total2 = 0;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("total1", total1);
                    editor.putLong("total2", total2);
                    editor.commit();
                    db.deleteTable();
                    testShare();
                    //  Toast.makeText(getApplicationContext(),editt,Toast.LENGTH_LONG).show();
                }
            });
            dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //pass
                }
            });
            AlertDialog b = dialogBuilder.create();
            b.show();
            //also delete the entire list of data in SQLite
        }
        if (id == R.id.exit) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Are you sure?");
            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
finish();
                    //  Toast.makeText(getApplicationContext(),editt,Toast.LENGTH_LONG).show();
                }
            });
            dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //pass
                }
            });
            AlertDialog b = dialogBuilder.create();
            b.show();
        }
        if (id == R.id.password123)
        {
            SharedPreferences shared=getApplicationContext().getSharedPreferences("valuestore",MODE_PRIVATE);
           final SharedPreferences.Editor editor=shared.edit();
            if (String.valueOf(item.getTitle()).compareTo("Disable Password")==0)
            {
                final MenuItem menuItem=item;
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                dialogBuilder.setView(dialogView);
                final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
                dialogBuilder.setTitle("Verify yourself!");
                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (edt.getText().toString().compareTo(pass) == 0) {
                            menuItem.setTitle("Enable Password");
                            editor.putBoolean("passwordoption",false);
                            editor.commit();
                            testShare();
                        } else
                            Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();
            }
            else
            {
                item.setTitle("Disable Password");
                editor.putBoolean("passwordoption",true);
                editor.commit();
                testShare();
            }
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
            return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume()
    {
       // Toast.makeText(getApplicationContext(), "Resumed", Toast.LENGTH_LONG).show();
       //  testShare();
        super.onResume();
    }
}
