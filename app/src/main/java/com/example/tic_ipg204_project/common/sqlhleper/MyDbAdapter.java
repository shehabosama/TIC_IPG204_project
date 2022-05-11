package com.example.tic_ipg204_project.common.sqlhleper;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import com.example.tic_ipg204_project.common.model.UserVacationRequest;

import java.util.ArrayList;
import java.util.List;


public class MyDbAdapter {
    myDbHelper myhelper;
    public MyDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertOwnerData (String ownerName, String ownerDescription )
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.M_NAME, ownerName);
        contentValues.put(myDbHelper.M_DESCRIPTION,ownerDescription);
        long id = dbb.insert(myDbHelper.OWNER, null , contentValues);
        return id;
    }
    public long insertMaterialData (String MaterialName, String materialDescription, boolean isService)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.M_NAME, MaterialName);
        contentValues.put(myDbHelper.M_DESCRIPTION,materialDescription);
        contentValues.put(myDbHelper.IS_SERVICE,isService);
        long id = dbb.insert(myDbHelper.MATERIAL, null , contentValues);
        return id;
    }

    public long insertUserData( String email, String password)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.EMAIL,email);
        contentValues.put(myDbHelper.PASSWORD,password);
        long id = dbb.insert(myDbHelper.USER_TABLE, null , contentValues);
        return id;
    }

    /*public List<UserVacationRequest> getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.EMAIL,myhelper.NUMBER_OF_DAYS, myDbHelper.START_DATE, myDbHelper.MANAGER_NAME , myDbHelper.PASSWORD};
        Cursor cursor =db.query(myDbHelper.USER_TABLE,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        List<UserVacationRequest> userVacationRequests = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.EMAIL));
            int numberOfDays =cursor.getInt(cursor.getColumnIndex(myDbHelper.NUMBER_OF_DAYS));
            String startDate =cursor.getString(cursor.getColumnIndex(myDbHelper.START_DATE));
            String reason =cursor.getString(cursor.getColumnIndex(myDbHelper.PASSWORD));
            String managerName =cursor.getString(cursor.getColumnIndex(myDbHelper.MANAGER_NAME));
            userVacationRequests.add(new UserVacationRequest(cid,numberOfDays,startDate,managerName,reason,name));
        }
        return userVacationRequests;
    }
*/
    public  int delete(String uId)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uId};

        int count =db.delete(myDbHelper.USER_TABLE, myDbHelper.UID+" = ?",whereArgs);
        Log.e(TAG, "delete: "+count );
        return  count;
    }

   /* public int updateName(String email , String newName, String numberOfDays, String startDate , String managerName , String reason)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.EMAIL,newName);
        contentValues.put(myDbHelper.NUMBER_OF_DAYS,numberOfDays);
        contentValues.put(myDbHelper.START_DATE,startDate);
        contentValues.put(myDbHelper.MANAGER_NAME,managerName);
        contentValues.put(myDbHelper.PASSWORD,reason);

        String[] whereArgs= {email};
        int count =db.update(myDbHelper.USER_TABLE,contentValues, myDbHelper.UID+" = ?",whereArgs );
        return count;
    }
*/
    public static class myDbHelper extends SQLiteOpenHelper
    {
        // Database Name
        private static final String DATABASE_NAME = "myDatabase";

        // Database Version
        private static final int DATABASE_Version = 1;

        // Tables Names
        private static final String USER_TABLE = "user";
        private static final String MATERIAL = "material";
        private static final String OWNER = "owner";
        private static final String OUTLAY = "outlay";

        // User Table Columns
        private static final String UID="_id";
        private static final String EMAIL = "email";
        private static final String PASSWORD = "password";

        // Material Table Columns
        private static final String M_ID="m_Id";
        private static final String M_NAME ="m_name" ;
        private static final String M_DESCRIPTION = "description";
        private static final String IS_SERVICE = "is_service";

        // Owner Table Columns
        private static final String O_ID="o_Id";
        private static final String O_NAME ="o_name" ;
        private static final String O_DESCRIPTION = "o_description";


        private static final String CREATE_USER_TABLE = "CREATE TABLE "+ USER_TABLE +
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ EMAIL +" VARCHAR(255) not null unique ,"+ PASSWORD +" VARCHAR(255));";


        private static final String CREATE_MATERIAL_TABLE = "CREATE TABLE "+ MATERIAL +
                " ("+M_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ M_NAME +" VARCHAR(255) ,"+ M_DESCRIPTION+" VARCHAR(255),"+IS_SERVICE+" BOOLEAN);";

        private static final String CREATE_OWNER_TABLE = "CREATE TABLE "+ OWNER +
                " ("+O_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ O_NAME +" VARCHAR(255) ,"+ O_DESCRIPTION+" VARCHAR(255));";
/*
        private static final String CREATE_OUTLAY_TABLE = "CREATE TABLE "+ USER_TABLE +
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ EMAIL +" VARCHAR(255) ,"+ NUMBER_OF_DAYS+" INTEGER,"+START_DATE+" VARCHAR(255),"+MANAGER_NAME+" VARCHAR(255),"+ PASSWORD +" VARCHAR(255));";
*/

        private static final String DROP_USER_TABLE ="DROP TABLE IF EXISTS "+ USER_TABLE;
        private static final String DROP_MATERIAL_TABLE ="DROP TABLE IF EXISTS "+ MATERIAL;
        private static final String DROP_OWNER_TABLE ="DROP TABLE IF EXISTS "+ OWNER;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_USER_TABLE);
                db.execSQL(CREATE_MATERIAL_TABLE);
                db.execSQL(CREATE_OWNER_TABLE);
            } catch (Exception e) {

                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {

                Toast.makeText(context, "OnUpgrade", Toast.LENGTH_SHORT).show();
                db.execSQL(DROP_USER_TABLE);
                db.execSQL(DROP_MATERIAL_TABLE);
                db.execSQL(DROP_OWNER_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

