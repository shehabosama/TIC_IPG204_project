package com.example.tic_ipg204_project.common.sqlhleper;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import com.example.tic_ipg204_project.common.model.Material;
import com.example.tic_ipg204_project.common.model.OutLay;
import com.example.tic_ipg204_project.common.model.Owner;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        contentValues.put(myDbHelper.O_NAME, ownerName);
        contentValues.put(myDbHelper.O_DESCRIPTION,ownerDescription);
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
    public long insertOutLayData( int materialId, int ownerId,String price , String date , String description)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.OUTLAY_M_ID,materialId);
        contentValues.put(myDbHelper.OUTLAY_O_ID,ownerId);
        contentValues.put(myDbHelper.OUT_DESCRIPTION,description);
        contentValues.put(myDbHelper.PRICE,price);
        contentValues.put(myDbHelper.DATE,date);
        long id = dbb.insert(myDbHelper.OUTLAY, null , contentValues);
        return id;
    }
    @SuppressLint("Range")
    public List<Material> getMaterialsData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.M_ID, myDbHelper.M_NAME,myDbHelper.M_DESCRIPTION, myDbHelper.IS_SERVICE};
        Cursor cursor =db.query(myDbHelper.MATERIAL,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        List<Material> materials = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int id =cursor.getInt(cursor.getColumnIndex(myDbHelper.M_ID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.M_NAME));
            String description =cursor.getString(cursor.getColumnIndex(myDbHelper.M_DESCRIPTION));
            boolean iService =cursor.getInt(cursor.getColumnIndex(myDbHelper.IS_SERVICE))>0;

            materials.add(new Material(id,name,description,iService));
        }
        return materials;
    }
    @SuppressLint("Range")
    public List<OutLay> getOutlayData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.OUT_ID, myDbHelper.OUT_DESCRIPTION, myDbHelper.OUTLAY_M_ID,myDbHelper.OUTLAY_O_ID, myDbHelper.PRICE , myDbHelper.DATE};
        Cursor cursor =db.query(myDbHelper.OUTLAY,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        List<OutLay> outLays = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int id =cursor.getInt(cursor.getColumnIndex(myDbHelper.OUT_ID));
            int materialId =cursor.getInt(cursor.getColumnIndex(myDbHelper.OUTLAY_M_ID));
            int ownerId =cursor.getInt(cursor.getColumnIndex(myDbHelper.OUTLAY_O_ID));
            double price =cursor.getDouble(cursor.getColumnIndex(myDbHelper.PRICE));
            String date =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE));
            String description =cursor.getString(cursor.getColumnIndex(myDbHelper.OUT_DESCRIPTION));

            outLays.add(new OutLay(id,ownerId,materialId , price,date,description));
        }
        return outLays;
    }
    @SuppressLint("Range")
    public List<Owner> getOwnersData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.O_ID, myDbHelper.O_NAME,myDbHelper.O_DESCRIPTION};
        Cursor cursor =db.query(myDbHelper.OWNER,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        List<Owner> owners = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int id =cursor.getInt(cursor.getColumnIndex(myDbHelper.O_ID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.O_NAME));
            String description =cursor.getString(cursor.getColumnIndex(myDbHelper.O_DESCRIPTION));


            owners.add(new Owner(id,name,description));
        }
        return owners;
    }
    @SuppressLint("Range")
    public boolean userIsExist(String email , String password)
    {
        boolean userChecker = false;
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.EMAIL,myDbHelper.PASSWORD};
        Cursor cursor =db.query(myDbHelper.USER_TABLE,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        List<Owner> owners = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int id =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String _email =cursor.getString(cursor.getColumnIndex(myDbHelper.EMAIL));
            String _password =cursor.getString(cursor.getColumnIndex(myDbHelper.PASSWORD));
            if(email.equals(_email) && password.equals(_password)){
                userChecker = true;
            }else{
                userChecker = false;
            }

        }
        return userChecker;
    }
    @SuppressLint("Range")
    public Owner getOwnerById(int ownerId)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        Cursor cursor=null;
        Owner owner = null;
        cursor =  dbb.rawQuery("select * from " + myDbHelper.OWNER + " where " + myDbHelper.O_ID + "=" + ownerId  , null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                int id =cursor.getInt(cursor.getColumnIndex(myDbHelper.O_ID));
                String name =cursor.getString(cursor.getColumnIndex(myDbHelper.O_NAME));
                String description =cursor.getString(cursor.getColumnIndex(myDbHelper.O_DESCRIPTION));
                owner=new Owner(id,name,description);

            }
            cursor.close();
        }
        return owner;
    }
    @SuppressLint("Range")
    public Material getMaterialById(int materialId)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        Cursor cursor=null;
        Material material = null;
        cursor =  dbb.rawQuery("select * from " + myDbHelper.MATERIAL + " where " + myDbHelper.M_ID + "=" + materialId  , null);
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                int id =cursor.getInt(cursor.getColumnIndex(myDbHelper.M_ID));
                String name =cursor.getString(cursor.getColumnIndex(myDbHelper.M_NAME));
                String description =cursor.getString(cursor.getColumnIndex(myDbHelper.M_DESCRIPTION));
                boolean iService =cursor.getInt(cursor.getColumnIndex(myDbHelper.IS_SERVICE))>0;
                material=new Material(id,name,description,iService);

            }
            cursor.close();
        }
        return material;
    }

    @SuppressLint("Range")
    public List<OutLay> getOwnerOutlays(int ownerId)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        Cursor cursor;
        cursor =  dbb.rawQuery("select * from " + myDbHelper.OUTLAY + " where " + myDbHelper.OUTLAY_O_ID + "=" + ownerId  , null);
        List<OutLay> outLays = new ArrayList<>();
        while (cursor.moveToNext())
        {

            int id =cursor.getInt(cursor.getColumnIndex(myDbHelper.OUT_ID));
            int materialId =cursor.getInt(cursor.getColumnIndex(myDbHelper.OUTLAY_M_ID));
            int _ownerId =cursor.getInt(cursor.getColumnIndex(myDbHelper.OUTLAY_O_ID));
            double price =cursor.getDouble(cursor.getColumnIndex(myDbHelper.PRICE));
            String date =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE));
            String description =cursor.getString(cursor.getColumnIndex(myDbHelper.OUT_DESCRIPTION));
            outLays.add(new OutLay(id,_ownerId,materialId , price,date,description));
        }
            cursor.close();
        return outLays;
    }

    @SuppressLint("Range")
    public List<OutLay> getIsServiceMaterialOutlays(int isService)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        Cursor cursor=null;
        Cursor cursor2=null;
        Material material = null;
        cursor =  dbb.rawQuery("select * from " + myDbHelper.OUTLAY , null);
        List<OutLay> outLays = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int _materialId =cursor.getInt(cursor.getColumnIndex(myDbHelper.OUTLAY_M_ID));
            cursor2 =  dbb.rawQuery("select * from " + myDbHelper.MATERIAL + " where " + myDbHelper.M_ID + "=" + _materialId  , null);
            while (cursor2.moveToNext())
            {
                boolean _isService =cursor2.getInt(cursor2.getColumnIndex(myDbHelper.IS_SERVICE))>0;
                if(_isService){
                    int id =cursor.getInt(cursor.getColumnIndex(myDbHelper.OUT_ID));

                    int _ownerId =cursor.getInt(cursor.getColumnIndex(myDbHelper.OUTLAY_O_ID));
                    double price =cursor.getDouble(cursor.getColumnIndex(myDbHelper.PRICE));
                    String date =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE));
                    String description =cursor.getString(cursor.getColumnIndex(myDbHelper.OUT_DESCRIPTION));

                    outLays.add(new OutLay(id,_ownerId,_materialId , price,date,description));
                }
            }

        }
        cursor.close();
        cursor2.close();

        return outLays;
    }

    @SuppressLint("Range")
    public List<OutLay> getIsNotServiceMaterialOutlays(int isService)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        Cursor cursor=null;
        Cursor cursor2=null;
        Material material = null;
        cursor =  dbb.rawQuery("select * from " + myDbHelper.OUTLAY , null);
        List<OutLay> outLays = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int _materialId =cursor.getInt(cursor.getColumnIndex(myDbHelper.OUTLAY_M_ID));
            cursor2 =  dbb.rawQuery("select * from " + myDbHelper.MATERIAL + " where " + myDbHelper.M_ID + "=" + _materialId  , null);
            while (cursor2.moveToNext())
            {
                boolean _isService =cursor2.getInt(cursor2.getColumnIndex(myDbHelper.IS_SERVICE))>0;
                if(!_isService){
                    int id =cursor.getInt(cursor.getColumnIndex(myDbHelper.OUT_ID));

                    int _ownerId =cursor.getInt(cursor.getColumnIndex(myDbHelper.OUTLAY_O_ID));
                    double price =cursor.getDouble(cursor.getColumnIndex(myDbHelper.PRICE));
                    String date =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE));
                    String description =cursor.getString(cursor.getColumnIndex(myDbHelper.OUT_DESCRIPTION));

                    outLays.add(new OutLay(id,_ownerId,_materialId , price,date,description));
                }
            }

        }
        cursor.close();
        cursor2.close();

        return outLays;
    }
    @SuppressLint("Range")
    public List<OutLay> getOutlaysByYear(int year)
    {
      List<OutLay> outLays = getOutlayData();
      List<OutLay> filteredOutLays =new ArrayList<>();
      String yourDateString;
        for (OutLay o:outLays) {
            yourDateString = o.getDate();
            SimpleDateFormat yourDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date yourDate = yourDateFormat.parse(yourDateString);
                SimpleDateFormat formatNowYear = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    formatNowYear = new SimpleDateFormat("yyyy");
                }
                int currentYear = Integer.parseInt(formatNowYear.format(yourDate));
                if (year == currentYear){
                    filteredOutLays.add(o);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return filteredOutLays;
    }

    @SuppressLint("Range")
    public List<OutLay> getOutlaysByMonth(int month)
    {
        List<OutLay> outLays = getOutlayData();
        List<OutLay> filteredOutLays =new ArrayList<>();
        String yourDateString;
        for (OutLay o:outLays) {
            yourDateString = o.getDate();
            SimpleDateFormat yourDateFormat = new SimpleDateFormat("MM/dd/yyyy");

            try {
                Date yourDate = yourDateFormat.parse(yourDateString);
                SimpleDateFormat formatNowMonth = new SimpleDateFormat("MM");
                int currentMonth = Integer.parseInt(formatNowMonth.format(yourDate));
                if (month==currentMonth){
                    filteredOutLays.add(o);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return filteredOutLays;
    }

    public  int deleteOwner(String ownerId)
    {
        boolean checkIfExists = false;
        List<OutLay> outLays = getOutlayData();
        for(OutLay outLay: outLays){
            if(ownerId.equals(String.valueOf(outLay.getOwnerId()))){
                checkIfExists = true;
            }
        }
        if(checkIfExists){
            return -2;
        }else{
            SQLiteDatabase db = myhelper.getWritableDatabase();
            String[] whereArgs ={ownerId};

            int count =db.delete(myDbHelper.OWNER, myDbHelper.O_ID+" = ?",whereArgs);
            Log.e(TAG, "delete: "+count );
            return  count;
        }
    }
    public  int deleteOutlay(String outlayId)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={outlayId};

        int count =db.delete(myDbHelper.OUTLAY, myDbHelper.OUT_ID+" = ?",whereArgs);

        Log.e(TAG, "delete: "+count );
        return  count;
    }
    public  int deleteMaterial(String materialId)
    {
        boolean checkIfExists = false;
        List<OutLay> outLays = getOutlayData();
        for(OutLay outLay: outLays){
            if(materialId.equals(String.valueOf(outLay.getOwnerId()))){
                checkIfExists = true;
            }
        }
        if(checkIfExists){
            return -2;
        }else{
            SQLiteDatabase db = myhelper.getWritableDatabase();
            String[] whereArgs ={materialId};

            int count =db.delete(myDbHelper.MATERIAL, myDbHelper.M_ID+" = ?",whereArgs);
            Log.e(TAG, "delete: "+count );
            return  count;
        }

    }
    public int updateOwner(String id , String newName, String description)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.O_NAME,newName);
        contentValues.put(myDbHelper.O_DESCRIPTION,description);

        String[] whereArgs= {id};
        int count =db.update(myDbHelper.OWNER,contentValues, myDbHelper.O_ID+" = ?",whereArgs );
        return count;
    }
    public int updateMaterial(String id , String newName, String description,boolean isService)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.M_NAME,newName);
        contentValues.put(myDbHelper.M_DESCRIPTION,description);
        contentValues.put(myDbHelper.IS_SERVICE,isService);

        String[] whereArgs= {id};
        int count =db.update(myDbHelper.MATERIAL,contentValues, myDbHelper.M_ID+" = ?",whereArgs );
        return count;
    }

    public int updateOutlay(String id , int materialId, int ownerId,String price , String date , String description)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.OUTLAY_O_ID,ownerId);
        contentValues.put(myDbHelper.PRICE,price);
        contentValues.put(myDbHelper.DATE,date);
        contentValues.put(myDbHelper.OUTLAY_M_ID,materialId);
        contentValues.put(myDbHelper.OUT_DESCRIPTION,description);

        String[] whereArgs= {id};
        int count =db.update(myDbHelper.OUTLAY,contentValues, myDbHelper.OUT_ID+" = ?",whereArgs );
        return count;
    }

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

        // Outlay Table Columns
        private static final String OUT_ID ="out_id";
        private static final String OUTLAY_M_ID="outlay_m_Id";
        private static final String OUTLAY_O_ID="outlay_o_Id";
        private static final String PRICE="price";
        private static final String DATE="date";
        private static final String OUT_DESCRIPTION="out_description";

        private static final String CREATE_USER_TABLE = "CREATE TABLE "+ USER_TABLE +
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ EMAIL +" VARCHAR(255) not null unique ,"+ PASSWORD +" VARCHAR(255));";


        private static final String CREATE_MATERIAL_TABLE = "CREATE TABLE "+ MATERIAL +
                " ("+M_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ M_NAME +" VARCHAR(255) ,"+ M_DESCRIPTION+" VARCHAR(255),"+IS_SERVICE+" BOOLEAN);";

        private static final String CREATE_OWNER_TABLE = "CREATE TABLE "+ OWNER +
                " ("+O_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ O_NAME +" VARCHAR(255) ,"+ O_DESCRIPTION+" VARCHAR(255));";

        private static final String CREATE_OUTLAY_TABLE = "CREATE TABLE "+ OUTLAY +
                " ("+OUT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+  OUTLAY_M_ID+" INTEGER,"+ OUTLAY_O_ID+" INTEGER,"+PRICE+" REAL,"+DATE+" TEXT,"+ OUT_DESCRIPTION +" VARCHAR(255));";


        private static final String DROP_USER_TABLE ="DROP TABLE IF EXISTS "+ USER_TABLE;
        private static final String DROP_MATERIAL_TABLE ="DROP TABLE IF EXISTS "+ MATERIAL;
        private static final String DROP_OWNER_TABLE ="DROP TABLE IF EXISTS "+ OWNER;
        private static final String DROP_OUTLAY_TABLE ="DROP TABLE IF EXISTS "+ OUTLAY;
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
                db.execSQL(CREATE_OUTLAY_TABLE);
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
                db.execSQL(DROP_OUTLAY_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

