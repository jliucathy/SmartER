package com.example.ljx.smarter;

import android.provider.BaseColumns;
public class ERDBStructure {
    public static abstract class ResidentTable implements BaseColumns{
        public static final String Table_Name="residents";
        public static final String COLUMN_RESID="resid";
        public static final String COLUMN_FIRSTNAME="firstname";
        public static final String COLUMN_SURNAME="surname";
        public static final String COLUMN_DOB="dob";
        public static final String COLUMN_ADDRESS="address";
        public static final String COLUMN_POSTCODE="postcode";
        public static final String COLUMN_EMAIL="email";
        public static final String COLUMN_MOBILE="mobile";
        public static final String COLUMN_NOOFRESIDENTS="noofresidents";
        public static final String COLUMN_ENERGYPROVIDER="energyprovider";
    }

    public static abstract class CredentialTable implements BaseColumns{
        public static final String Table_Name="credential";
        public static final String COLUMN_USERNAME="username";
        public static final String COLUMN_RESID="resid";
        public static final String COLUMN_PASSWORD="password";
        public static final String COLUMN_REGISTRATIONDATE="registrationdate";
    }

    public static abstract class EleusageTable implements BaseColumns {
        public static final String TABLE_NAME = "hourlyusage";
        public static final String COLUMN_USAGEID = "usageid";
        public static final String COLUMN_RESID = "resid";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_TEMPERATURE = "temperature";
        public static final String COLUMN_AIRCON = "aircon";
        public static final String COLUMN_WASHINGMACHINE = "washingmachine";
        public static final String COLUMN_FRIDGE = "fridge";
    }

}
