package com.shubham.roommatebudget;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/**
 * Created by admin on 7/9/2016.
 */
public class DetailView extends AppCompatActivity
{
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    static View.OnClickListener myOnClickListener;
    SharedPreferences sharedPreferences;
    DatabaseHelper db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_pagerecycler);
        myOnClickListener = new MyOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Bundle bundle=getIntent().getExtras();
         db=new DatabaseHelper(this);
        sharedPreferences=getApplicationContext().getSharedPreferences("valuestore",MODE_PRIVATE);
        String ops="";
        int op=bundle.getInt("ops");
        if(op==1)
        {
            ops="Roommate1";
            String name=sharedPreferences.getString("name1","");
            getSupportActionBar().setTitle(name+"'s List");

        }
        else if(op==2)
        {
            ops="Roommate2";
            String name=sharedPreferences.getString("name2","");
                getSupportActionBar().setTitle(name+"'s List");
        }
        List<DataModel>list=db.getAllData();
        ArrayList<CardDataModel> finalList=new ArrayList<>();
       for(DataModel dm:list)
       {
           if(dm.getName().equals(ops))
           { String s[]=dm.getCost().split(" ");
               long timestampString =  Long.parseLong(s[1]);
               String value = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).
                       format(new java.util.Date(timestampString * 1000));
               finalList.add(new CardDataModel(dm.getItem(),("Rs "+s[0]),"Added: "+value));
           }
       }
        adapter=new CustomAdapter(finalList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    private  class MyOnClickListener implements View.OnClickListener
    {
        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v)
        {
            //removeItem(v);
           // printView(v);
        }
      /*  private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < MyData.nameArray.length; i++) {
                if (selectedName.equals(MyData.nameArray[i])) {
                    selectedItemId = MyData.id_[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem=menu.findItem(R.id.password123);
        Boolean password=sharedPreferences.getBoolean("passwordoption",true);
        if(password)
            menuItem.setTitle("Disable Password");
        else menuItem.setTitle("Enable Password");
        menuItem=menu.findItem(R.id.exit);
        menuItem.setTitle("Home");
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
                    Intent i = new Intent(DetailView.this, KindofLogin.class);
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
                    long total1 = 0;
                    long total2 = 0;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("total1", total1);
                    editor.putLong("total2", total2);
                    editor.commit();
                    db.deleteTable();
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
            Intent intent = new Intent(DetailView.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
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
        if (id == R.id.password123)
        {
            final SharedPreferences.Editor editor=sharedPreferences.edit();
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
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        String pass=sharedPreferences.getString("pass1","");
                        if (edt.getText().toString().compareTo(pass) == 0) {
                            menuItem.setTitle("Enable Password");
                            editor.putBoolean("passwordoption",false);
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "Password Disabled", Toast.LENGTH_LONG).show();

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
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
