package com.example.testdown.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public static final String TAG = "DBHelper";

	// private Context context;// 上下文实体
	public static final String DATABASE_NAME = "data.db";
	public static final int DATABASE_VERSION = 6;

	public static final String INSTALLED_TABLE = "ALLINSTALLED_TABLE";
	public static final String DOWNLOAD_TABLE = "DOWNLOAD_TABLE";
	
	// 已安装应用和显示的应用类别表
    private static final String INSTALLED_TABLE_CREATEOR = "CREATE TABLE " + INSTALLED_TABLE
            + " (app_Id int, use_Id varchar, app_Version varchar(32), app_Iamge BLOB, app_Name nvarchar(32), "
            + "app_Type int, app_Floder varchar(32), app_Package text);";
 // 下载记录表
    private static final String DOWNLOAD_TABLE_CREATEOR = "create table " + DOWNLOAD_TABLE
            + " (_id integer PRIMARY KEY AUTOINCREMENT, use_id varchar, down_state integer, "
            + "thread_id integer, start_pos integer, end_pos integer, compelete_size integer, "
            + "url varchar, app_name nvarchar, app_size varchar, icon_path varchar, appid integer, version_code integer);";

	public DBHelper(Context context) {
		// TODO 单例
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// this.context = context;
	}

	/** 数据库创建 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			db.execSQL(INSTALLED_TABLE_CREATEOR);
            db.execSQL(DOWNLOAD_TABLE_CREATEOR);
			db.setTransactionSuccessful();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String appSql = "DROP TABLE IF EXISTS " + INSTALLED_TABLE;
		String downSql = "DROP TABLE IF EXISTS " + DOWNLOAD_TABLE;
		db.execSQL(appSql);
		db.execSQL(downSql);
		onCreate(db);
	}

}
