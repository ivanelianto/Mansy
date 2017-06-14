package com.droidapp.ivanelv.mansy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.droidapp.ivanelv.mansy.data.ParticipantContract;
import com.droidapp.ivanelv.mansy.data.ParticipantDbHelper;

public class ParticipantActivity extends AppCompatActivity
{
    private ParticipantDbHelper mDbHelper;

    long _id;

    EditText editTextName, editTextEmail, editTextLink;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);

        editTextName = (EditText) findViewById(R.id.input_participant_name);
        editTextEmail = (EditText) findViewById(R.id.input_participant_email);
        editTextLink = (EditText) findViewById(R.id.input_participant_link);

        mDbHelper = new ParticipantDbHelper(this);

        Intent intent = getIntent();
        _id = intent.getLongExtra("participant_id", 0);

        if (_id > 0)
        {
            editTextName.setText(intent.getStringExtra("participant_name"));
            editTextEmail.setText(intent.getStringExtra("participant_email"));
            editTextLink.setText(intent.getStringExtra("participant_link"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_participant, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.save:
                manipulateData(editTextName.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextLink.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void manipulateData(String name, String email, String link)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ParticipantContract.ParticipantEntry.NAME, name);
        contentValues.put(ParticipantContract.ParticipantEntry.EMAIL, email);
        contentValues.put(ParticipantContract.ParticipantEntry.FINAL_PROJECT_LINK, link);

        if (_id == 0)
        {
            db.insert(ParticipantContract.ParticipantEntry.TABLE_NAME, null, contentValues);
            Toast.makeText(ParticipantActivity.this, "New Data Added Successfully!", Toast.LENGTH_SHORT).show();
        }
        else if (_id > 0)
        {
            db.update(ParticipantContract.ParticipantEntry.TABLE_NAME,
                    contentValues,
                    ParticipantContract.ParticipantEntry._ID + "= ?",
                    new String[] { _id + "" });
            Toast.makeText(ParticipantActivity.this, "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
