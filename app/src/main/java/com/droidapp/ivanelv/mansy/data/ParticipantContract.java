package com.droidapp.ivanelv.mansy.data;

import android.provider.BaseColumns;

/**
 * Created by ivanelv on 13/06/2017.
 */

// This class only contain constants
public final class ParticipantContract
{
    private ParticipantContract(){}

    public static class ParticipantEntry implements BaseColumns
    {
        public final static String TABLE_NAME = "participant";

        public final static String _ID = BaseColumns._ID;
        public final static String NAME = "name";
        public final static String EMAIL = "email";
        public final static String FINAL_PROJECT_LINK = "link";
    }
}
