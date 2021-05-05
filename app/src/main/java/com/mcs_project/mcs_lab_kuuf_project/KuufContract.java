package com.mcs_project.mcs_lab_kuuf_project;

import android.provider.BaseColumns;

public class KuufContract {
    private KuufContract(){}

    public static class UsersEntry implements BaseColumns {
        public static final String TABLE_NAME = "users_entry";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_PHONE = "phone_number";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_WALLET = "wallet";
        public static final String COLUMN_DOB = "dob";
    }

    public static class TransEntry implements BaseColumns {
        public static final String TABLE_NAME = "trans_entry";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_PRODUCT_ID = "product_id";
        public static final String COLUMN_TRANS_DATE = "trans_date";
    }

    public static class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "product_entry";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MIN_PLAYER = "min_player";
        public static final String COLUMN_MAX_PLAYER = "max_player";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
    }
}
