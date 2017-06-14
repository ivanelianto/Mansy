package com.droidapp.ivanelv.mansy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.droidapp.ivanelv.mansy.data.ParticipantContract;
import com.droidapp.ivanelv.mansy.data.ParticipantDbHelper;

public class MainActivity extends AppCompatActivity
{
    ParticipantDbHelper mDbHelper;
    Cursor cursor;

    ListView listView;
    Menu menu;
    SwipeRefreshLayout srLayout;

    int checkedListViewItems = 0;

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {

            // Column index for _ID
            // = 0
            int idColumnIndex = cursor.getColumnIndex(ParticipantContract.ParticipantEntry._ID);

            // _ID Value for Current Cursor
            // *Cursor Move when click ListView item
            long _id = cursor.getLong(idColumnIndex);

            if (listView.getChoiceMode() == AbsListView.CHOICE_MODE_NONE)
            {
                TextView name = (TextView) view.findViewById(R.id.participant_name),
                        email = (TextView) view.findViewById(R.id.participant_email),
                        link = (TextView) view.findViewById(R.id.participant_link);

                Intent intent = new Intent(MainActivity.this, ParticipantActivity.class);
                intent.putExtra("participant_id", _id);
                intent.putExtra("participant_name", name.getText());
                intent.putExtra("participant_email", email.getText());
                intent.putExtra("participant_link", link.getText());
                startActivity(intent);
            }
            else
            {
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                View v = listView.getChildAt(position);
                if (checked.get(position))
                {
                    v.setBackgroundColor(0xFFEEEEEE);
                }
                else
                {
                    v.setBackgroundColor(0xFFFFFFFF);
                }

                if (checked.get(position))
                {
                    checkedListViewItems++;
                }
                else
                {
                    checkedListViewItems--;
                }
                getSupportActionBar().setTitle(checkedListViewItems + " Selected");
            }
        }
    };

    AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener()
    {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
        {
            if (listView.getChoiceMode() == AbsListView.CHOICE_MODE_NONE)
            {
                toggleEditMode();
            }
            listView.setItemChecked(position, true);

            View v = listView.getChildAt(position);
            v.setBackgroundColor(0xFFEEEEEE);
            checkedListViewItems = 1;
            getSupportActionBar().setTitle(checkedListViewItems + " Selected");
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        srLayout = (SwipeRefreshLayout) findViewById(R.id.srLayout);
        srLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                displayDatabaseInfo();
                srLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                toggleEditMode();
                int checkedItems = listView.getCheckedItemCount();
                for (int i = 0; i < checkedItems; i++)
                {
                    Log.d("SPARSE", i + " set false");
                    listView.setItemChecked(i, false);
                }
                break;
            case R.id.menu_delete:
                deleteParticipants();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // Method for Floating Action Button Click
    public void addNewParticipant(View v)
    {
        Intent intent = new Intent(MainActivity.this, ParticipantActivity.class);
        startActivity(intent);
    }

    private void deleteParticipants()
    {
        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        for (int i = 0; i < listView.getCount(); i++)
        {
            if (checkedItems.get(i))
            {
                cursor.moveToPosition(i);
                db.delete(
                        ParticipantContract.ParticipantEntry.TABLE_NAME,
                        ParticipantContract.ParticipantEntry._ID + "=?",
                        new String[] { cursor.getLong(0) + "" }
                );
            }
        }
        toggleEditMode();
        displayDatabaseInfo();
        Toast.makeText(MainActivity.this, "Data Deleted Successfully!", Toast.LENGTH_SHORT).show();
    }

    // Read Database
    public void displayDatabaseInfo()
    {
        // Create the database handler object
        mDbHelper = new ParticipantDbHelper(this);

        // .opens DATABASE_NAME
        // use DATABASE_NAME <- This is equivalent in MySQL / SQL
        // If exists, return Database Object, else create a new database file
        // and return Database Object.
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Select * from TABLE_NAME
        cursor = db.query(
                ParticipantContract.ParticipantEntry.TABLE_NAME,
                null, null, null, null, null, null);

        ParticipantAdapter adapter = new ParticipantAdapter(this, cursor);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(itemClickListener);
        listView.setOnItemLongClickListener(itemLongClickListener);
    }

    private void toggleEditMode()
    {
        if (listView.getChoiceMode() == AbsListView.CHOICE_MODE_NONE)
        {
            // Choose many views
            listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

            // Change ActionBar Behavior
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");

            // Accent color
            ColorDrawable cd = new ColorDrawable(0xFFFF4081);
            getSupportActionBar().setBackgroundDrawable(cd);

            menu.findItem(R.id.menu_delete).setVisible(true);
        }
        else if (listView.getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE)
        {
            listView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);

            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(getString(R.string.app_name));

            // Primary color
            ColorDrawable cd = new ColorDrawable(0xFF3F51B5);
            getSupportActionBar().setBackgroundDrawable(cd);

            menu.findItem(R.id.menu_delete).setVisible(false);
        }
    }
}
