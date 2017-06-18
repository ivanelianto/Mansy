package com.droidapp.ivanelv.mansy.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ivanelv on 13/06/2017.
 */

// This class only contain constants
public final class ParticipantContract
{
    private ParticipantContract(){}

    public static final String CONTENT_AUTHORITY = "com.droid.ivanelv.mansy";

    // Result :
    // content://com.droid.ivanelv.mansy/
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PARTICIPANTS = "participants";

    public static class ParticipantEntry implements BaseColumns
    {
        // Result :
        // content://com.droid.ivanelv.mansy/participants
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PARTICIPANTS);

        public final static String TABLE_NAME = "participant";

        public final static String _ID = BaseColumns._ID;
        public final static String NAME = "name";
        public final static String EMAIL = "email";
        public final static String FINAL_PROJECT_LINK = "link";
    }
}
