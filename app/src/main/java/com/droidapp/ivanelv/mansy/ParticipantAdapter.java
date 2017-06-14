package com.droidapp.ivanelv.mansy;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.droidapp.ivanelv.mansy.data.ParticipantContract;

import java.util.ArrayList;

/**
 * Created by ivanelv on 13/06/2017.
 */

public class ParticipantAdapter extends CursorAdapter
{
    private Context mContext;

    public ParticipantAdapter(Context context, Cursor c)
    {
        super(context, c);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.model_participant, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        // Bind Views
        TextView tvName = (TextView) view.findViewById(R.id.participant_name),
                tvEmail = (TextView) view.findViewById(R.id.participant_email),
                tvLink = (TextView) view.findViewById(R.id.participant_link);

        // Get Columns Index
        int nameColumnIndex = cursor.getColumnIndex(ParticipantContract.ParticipantEntry.NAME);
        int emailColumnIndex = cursor.getColumnIndex(ParticipantContract.ParticipantEntry.EMAIL);
        int linkColumnIndex = cursor.getColumnIndex(ParticipantContract.ParticipantEntry.FINAL_PROJECT_LINK);

        // Get Values
        String name = cursor.getString(nameColumnIndex);
        String email = cursor.getString(emailColumnIndex);
        String link = cursor.getString(linkColumnIndex);

        // Set The Values to Views
        tvName.setText(name);
        tvEmail.setText(email);
        tvLink.setText(link);
    }
}
