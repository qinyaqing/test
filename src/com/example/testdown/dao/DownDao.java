package com.example.testdown.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.testdown.downloder.DownloadInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;



/**
 * 下载数据库业务类
 */
public class DownDao {
	private static DownDao dao;
	private Context context;

	private DownDao(Context context) {
		this.context = context;
	}

	public static synchronized DownDao getInstance(Context context) {
		if (dao == null) {
			dao = new DownDao(context);
		}
		return dao;
	}

	private synchronized SQLiteDatabase getConnection() throws SQLiteException {
		return new DBHelper(context).getReadableDatabase();
	}

	/**
     * 查看数据库中是否有该下载记录
     * 
     * @param id 应用/插件ID
     * @return 记录是否大于0
     */
    public synchronized boolean hasInfo(final int id) {
	    SQLiteDatabase database = null;
	    int count = -1;
	    Cursor cursor = null;
	    try {
	        database = getConnection();
	        String sql = "select count(*)  from " + DBHelper.DOWNLOAD_TABLE + " where appid=?";
	        cursor = database.rawQuery(sql, new String[] { String.valueOf(id) });
	        if (cursor.moveToFirst()) {
	            count = cursor.getInt(0);
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    finally {
	        if (null != cursor) {
	            cursor.close();
	        }
	        if (null != database) {
	            database.close();
	        }
	    }
	    return count > 0;
	}

	/**
	 * 保存 下载的具体信息
	 */
	public synchronized void saveInfos(final List<DownloadInfo> infos) {
		SQLiteDatabase database = null;
		try {
		    database = getConnection();
			for (DownloadInfo info : infos) {
                String sql = "insert into " + DBHelper.DOWNLOAD_TABLE
                        + "(app_id, down_state, thread_id, start_pos, end_pos, compelete_size, url,"
                        + "app_name, app_size, icon_path, appid, version_code) values (?,?,?,?,?,?,?,?,?,?,?,?)";
                Object[] bindArgs = { info.getuseId(), info.getDownState(), info.getThreadId(),
                        info.getStartPos(), info.getEndPos(), info.getCompeleteSize(), info.getUrl(),
                        info.getAppName(), info.getAppSize(), info.getIconPath(), info.getAppId(),
                        info.getVersionCode() };
                database.execSQL(sql, bindArgs);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (null != database) {
				database.close();
			}
		}
	}

	/**
	 * 得到下载具体信息
	 */
	public synchronized List<DownloadInfo> getInfos(int id) {
		List<DownloadInfo> list = new ArrayList<DownloadInfo>();
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
		    database = getConnection();
			String sql = "select police_id, down_state, thread_id, start_pos, end_pos, compelete_size, url, app_name, "
			        + "app_size, icon_path, appid,version_code from " + DBHelper.DOWNLOAD_TABLE + " where appid=?";
			cursor = database.rawQuery(sql, new String[] { String.valueOf(id) });
			while (cursor.moveToNext()) {
				DownloadInfo info = new DownloadInfo(cursor.getString(0), cursor.getInt(1), cursor.getInt(2),
				        cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6),
				        cursor.getString(7), cursor.getDouble(8), cursor.getString(9),
				        cursor.getInt(10), cursor.getInt(11));
				list.add(info);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		    if (null != cursor) {
		        cursor.close();
		    }
			if (null != database) {
				database.close();
			}
		}
		return list;
	}
	/**
	 * 更新数据库中的下载信息
	 */
	public synchronized void updataInfos(int threadId, int compeleteSize, int id) {
		SQLiteDatabase database = null;
		try {
		    database = getConnection();
			String sql = "update " + DBHelper.DOWNLOAD_TABLE + " set compelete_size=? where thread_id=? and appid=?";
			Object[] bindArgs = { compeleteSize, threadId, String.valueOf(id) };
			database.execSQL(sql, bindArgs);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (null != database) {
				database.close();
			}
		}
	}
	/**
	 * 更新数据库中的下载状态
	 */
	public synchronized void updataState(int threadId, int downState, int id) {
		SQLiteDatabase database = null;
		try {
		    database = getConnection();
			String sql = "update " + DBHelper.DOWNLOAD_TABLE + " set down_state=? where thread_id=? and appid=?";
			Object[] bindArgs = { downState, threadId, String.valueOf(id) };
			database.execSQL(sql, bindArgs);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (null != database) {
				database.close();
			}
		}
	}
	/**
	 * 根据id删除数据库中的下载记录
	 * @return 删除行数
	 */
	public synchronized int deleteById(int appId) {
		SQLiteDatabase database = null;
        int counts = 0;
		try {
		    database = getConnection();
            counts = database.delete(DBHelper.DOWNLOAD_TABLE, "appid = ? ", new String[] { Integer.toString(appId) });
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (null != database) {
				database.close();
			}
		}
		return counts;
	}


	/**
	 *  查询所有的信息
	 */
	public synchronized ArrayList<DownloadInfo> selectAll(int threadId, String policeId) {
        SQLiteDatabase db = null;
        Cursor c = null;
		ArrayList<DownloadInfo> list = new ArrayList<DownloadInfo>();
        try {
            db = getConnection();
            c = db.rawQuery("select * from " + DBHelper.DOWNLOAD_TABLE + " where thread_id=?",
                    new String[] { Integer.toString(threadId) /* and police_id=?, policeId */ });
            
            DownloadInfo bean;
            while (c.moveToNext()) {
                bean = new DownloadInfo();
                bean.setuseId(c.getString(c.getColumnIndex("police_id")));
                bean.setDownState(c.getInt(c.getColumnIndex("down_state")));
                bean.setThreadId(c.getInt(c.getColumnIndex("thread_id")));
                bean.setStartPos(c.getInt(c.getColumnIndex("start_pos")));
                bean.setEndPos(c.getInt(c.getColumnIndex("end_pos")));
                bean.setCompeleteSize(c.getInt(c.getColumnIndex("compelete_size")));
                bean.setUrl(c.getString(c.getColumnIndex("url")));
                bean.setAppName(c.getString(c.getColumnIndex("app_name")));
                bean.setAppSize(c.getInt(c.getColumnIndex("app_size")));
                bean.setIconPath(c.getString(c.getColumnIndex("icon_path")));
                bean.setAppId(c.getInt(c.getColumnIndex("appid")));
                bean.setVersionCode(c.getInt(c.getColumnIndex("version_code")));
                list.add(bean);
            }
        }
        catch (SQLiteException e) {
            e.printStackTrace();
        }
        finally {
            if (null != c) {
                c.close();
            }
            if (null != db) {
                db.close();
            }
        }
		return list;
	}
	
	/**
	 * 查询指定id的应用/插件下载记录
	 * @return 应用/插件或null
	 */
    public synchronized DownloadInfo selectById(int appid) {
        SQLiteDatabase db = null;
        Cursor c = null;
        DownloadInfo bean = null;
        try {
            db = getConnection();
            c = db.rawQuery("select * from " + DBHelper.DOWNLOAD_TABLE + " where appid=?", new String[] { Integer.toString(appid) });
            while (c.moveToNext()) {
                bean = new DownloadInfo();
                bean.setDownState(c.getInt(c.getColumnIndex("down_state")));
                bean.setThreadId(c.getInt(c.getColumnIndex("thread_id")));
                bean.setStartPos(c.getInt(c.getColumnIndex("start_pos")));
                bean.setEndPos(c.getInt(c.getColumnIndex("end_pos")));
                bean.setCompeleteSize(c.getInt(c.getColumnIndex("compelete_size")));
                bean.setUrl(c.getString(c.getColumnIndex("url")));
                bean.setAppName(c.getString(c.getColumnIndex("app_name")));
                bean.setAppSize(c.getInt(c.getColumnIndex("app_size")));
                bean.setIconPath(c.getString(c.getColumnIndex("icon_path")));
                bean.setAppId(c.getInt(c.getColumnIndex("appid")));
                bean.setVersionCode(c.getInt(c.getColumnIndex("version_code")));
            }
        }
        catch (SQLiteException e) {
            e.printStackTrace();
        }
        finally {
            if (null != c) {
                c.close();
            }
            if (null != db) {
                db.close();
            }
        }
        return bean;
    }

	/**
	 *清空数据库中的数据表中的数据-->download_info
    public synchronized void deleteAll(int threadId) {
        SQLiteDatabase database = getConnection();
        try {
            database.delete(DBHelper.DOWNLOAD_TABLE, "thread_id=?", new String[] { Integer.toString(threadId) });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (null != database) {
                database.close();
            }
        }
    }
     */
	
    /**
	 * 删除集合中的下载项
	 */
	public synchronized int deleteAll(ArrayList<DownloadInfo> list) {
		SQLiteDatabase database = null;
        int rs = 0;
		try {
		    database = getConnection();
			if (database != null && list.size() > 0) {
				String[] ids = new String[list.size()];
				StringBuffer str = new StringBuffer();
				ids[0] = String.valueOf(list.get(0).getAppId());
				str.append("appid = ?");
				for (int i = 1; i < list.size(); i++) {
					str.append(" or appid = ?");
					ids[i] = String.valueOf(list.get(i).getAppId());
				}
				rs = database.delete(DBHelper.DOWNLOAD_TABLE, str.toString(), ids);

                // rs=database.delete("download_info", "appid IN (?)", Arrays.asList(ids));//list.toArray(ids)
                // Log.e("DownDao", ids.length + "-deleteAll-" + rs);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (null != database) {
				database.close();
			}
		}
		return rs;
	}

	/*
    public synchronized int countAll() {
        SQLiteDatabase db = getConnection();
        int counts = 0;
        if (null != db) {
            Cursor c = db.rawQuery("select * from " + DBHelper.DOWNLOAD_TABLE, null);
            counts = c.getCount();
            if (null != c) {
                c.close();
            }
            db.close();
        }
        return counts;
    }*/

//    public int countState(int state) {
//        SQLiteDatabase db = getConnection();
//        int counts = 0;
//        if (null != db) {
//            Cursor c = db.rawQuery("select * from " + DBHelper.DOWNLOAD_TABLE + " where down_state=?",
//                    new String[] { String.valueOf(state) });
//            counts = c.getCount();
//            if (null != c) {
//                c.close();
//            }
//            db.close();
//        }
//        return counts;
//    }

}
