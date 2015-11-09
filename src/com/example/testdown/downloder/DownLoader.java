package com.example.testdown.downloder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.testdown.MyApplication;
import com.example.testdown.dao.DownDao;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/***
 * 异步下载类
 * @author 宋玉金
 *
 */
public class DownLoader {
	private static final String TAG = DownLoader.class.getSimpleName();
	private String urlstr;// 下载的地址
    private String localfile;// 保存路径
    private int threadcount;// 线程数
    private Handler mHandler;// 消息处理器
    private int fileSize;// 所要下载的文件的大小
    private Context context;
    private List<DownloadInfo> infos;// 存放下载信息类的集合
    /* 定义三种下载的状态：初始化，正在下载，暂停，完成 */
    public static final int INIT = 1;
    public static final int DOWNLOADING = 2;
    public static final int DOWNPAUSE = 3;
    public static final int DOWNSUCCESS = 4;
    private static final int DOWNERROR = 5;
    /** 下载状态，初始0。请确保仅通过set方法赋值和修改 */
    private int state = INIT;// 0;
    private String appName;
    private double appSize;
    private String iconPath;
    private int curPrecent = 0;
    private int tempPrecent = -1;
    private int appid = 0;
    private int versionCode;
    /**
	 * 根据传递的数据进行下载的下载器方法
	 */
    public DownLoader(String urlstr, String localfile, int threadcount, Context context,
	        Handler mHandler, String appName, double appSize, String iconPath, int appid,
	        int versionCode) {
		this.urlstr = urlstr;
		this.localfile = localfile;
		this.threadcount = threadcount;
		this.mHandler = mHandler;
		this.context = context;
		this.appName = appName;
        this.appSize = appSize;
		this.iconPath = iconPath;
        this.fileSize = (int) appSize;//TODO 冗余
		this.appid = appid;
		this.versionCode = versionCode;
	}
	public DownLoader(String url, String localfile2, int i, Context context2) {
		this.urlstr = url;
		this.localfile = localfile2;
		this.threadcount = i;
		this.context = context2;
	}
	/**
	 * 判断是否正在下载
	 */
	public boolean isDownloading() {
        Log.w(TAG, appid + " isDownloading:" + state);
		return state == DOWNLOADING;
	}
	public LoaderInfo getDownLoaderInfos(){
		LoaderInfo loaderinfos=null;
		if (DownDao.getInstance(context).hasInfo(appid)){
			// 得到数据库中已有的urlstr的下载器的具体信息
	        infos = DownDao.getInstance(context).getInfos(appid);
	        int size = 0;
	        int compeleteSize = 0;	
	        for (DownloadInfo info : infos) {
	            compeleteSize += info.getCompeleteSize();
	            size += info.getEndPos() - info.getStartPos() + 1;
	        }
	       loaderinfos=new LoaderInfo(size, compeleteSize, urlstr); 
		}else{
			if(fileSize<1){
				loaderinfos=null;//信息异常,下载失败
			}else{
				int range=fileSize/threadcount;
				infos=new ArrayList<DownloadInfo>();
				 for (int i = 0; i < threadcount - 1; i++) { 
				DownloadInfo info = new DownloadInfo(MyApplication.useId,state, i, i * range, (i + 1) * range - 1, 0,
                         urlstr, appName, appSize, iconPath, appid, versionCode);
                 infos.add(info);
			}
				 DownloadInfo info = new DownloadInfo(MyApplication.useId, state, threadcount - 1,
	                        (threadcount - 1) * range, fileSize - 1, 0, urlstr, appName, appSize, iconPath, appid,
	                        versionCode);
				 infos.add(info);
				 // 保存infos中的数据到数据库
	                DownDao.getInstance(context).saveInfos(infos);
	                loaderinfos = new LoaderInfo(fileSize, 0, urlstr);
			}
		}
		 if (mHandler != null /*&& !wait*/) {
		        // 开始前统一在此发送一次// 读取下载文件的数据库信息,发消息给上层更新
	            if (loaderinfos != null) {
	                // setState(INIT);
	                Message msg = mHandler.obtainMessage(3, urlstr);
	                msg.arg1 = loaderinfos.getComplete();
	                msg.arg2 = loaderinfos.getFileSize();
	                // Bundle b = new Bundle();
	                // b.putString("msg", "下载开始！");
	                // msg.setData(b);
	                msg.sendToTarget();
	            }
	            else {
	                setState(DOWNERROR);
	                Message msg = mHandler.obtainMessage(101, urlstr);
	                Bundle b = new Bundle();
	                b.putString("msg", "下载错误！文件长度异常");
	                msg.setData(b);
	                mHandler.sendMessage(msg);
	            }
	        }
		return loaderinfos;
		
		
	}
	/**
	 * 利用线程开始下载数据
	 */
	public void download() {
		if (infos != null) {
//			if (state == DOWNLOADING)
//				return;
			setState(DOWNLOADING);
			for (DownloadInfo info : infos) {
			    DownDao.getInstance(context).updataState(info.getThreadId(), state, appid);
				new MyThread(info.getThreadId(), info.getStartPos(), info.getEndPos(),
				        info.getCompeleteSize(), info.getUrl()).start();
			}
		}
	}
	public class MyThread extends Thread {
		private int threadId;
		private int startPos;
		private int endPos;
		private int complete;
		private String urlstr;

		public MyThread(int threadId, int startPos, int endPos, int compeleteSize, String urlstr) {
			this.threadId = threadId;
			this.startPos = startPos;
			this.endPos = endPos;
			this.complete = compeleteSize;
			this.urlstr = urlstr;
		}

		@Override
		public void run() {
			HttpURLConnection connection = null;
			RandomAccessFile randomAccessFile = null;
			InputStream is = null;
			try {
				URL url = new URL(urlstr);
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(5000);
				connection.setRequestMethod("GET");
				// 设置范围，格式为Range：bytes x-y;
                connection.setRequestProperty("Range", "bytes=" + (startPos + complete) + "-" + endPos);

                Log.e(TAG, localfile + " createNewFile: " + new File(localfile).createNewFile());
				randomAccessFile = new RandomAccessFile(localfile, "rwd");
				randomAccessFile.seek(startPos + complete);

                Log.w(TAG, startPos + "|" + complete + "|" + endPos);
                Log.w(TAG, connection.getResponseCode() + " :rsp length: " + connection.getContentLength());
                if ((connection.getResponseCode() == 200 || connection.getResponseCode() == 206)
                        && connection.getContentLength() > 0) {
					// 将要下载的文件写到保存在保存路径下的文件中
					is = connection.getInputStream();
					byte[] buffer = new byte[4096];// 下载慢,增大缓冲区??
					int length = -1;
					long spend = System.currentTimeMillis();
					// 注：通知的修改要同步到DownloadTask和SettingFragment中的handleMessage中！
					while ((length = is.read(buffer)) != -1) {
						randomAccessFile.write(buffer, 0, length);
						complete += length;
                        // Log.i(TAG, "end="+endPos+" compelete="+complete+" precent/tid="+threadId);

						if (endPos + 1 > complete) {
						    // 更新数据库中的下载信息(应用中心自更新时threadId为-1,不写入数据库)
                            if (threadId > -1) {
						        DownDao.getInstance(context).updataInfos(threadId, complete, appid);
                                // 提前到download()处,只改一次 // DownDao.getInstance(context).updataState(threadId, state, appid);
                            }
							curPrecent = (int) (100 * complete / endPos);
							// 采用按变化刷新,不用按进度间隔刷新和按时刷新
							/* curPrecent % 2 == 0 &&  && curPrecent <= 100*/
							if (tempPrecent < curPrecent && mHandler != null ) {
								tempPrecent = curPrecent;
                                // 用消息将下载信息传给进度条，对进度条进行更新
								mHandler.obtainMessage(1, complete, endPos, urlstr).sendToTarget();
								// 统一到DownloadTask中handleMessage通知// 动态更新设置中角标、 下载管理中TextView
                                // if (SettingFragment.setHandler != null)
                                //  SettingFragment.setHandler.sendEmptyMessage(56);
							}
						}
                        else if (endPos + 1 == complete) {
                            setState(DOWNSUCCESS);
                            DownDao.getInstance(context).updataState(threadId, state, appid);
                            mHandler.obtainMessage(4, urlstr).sendToTarget();
                        }
                        else {
                            setState(DOWNERROR);
                            if (mHandler != null) {
                                Message msg = mHandler.obtainMessage(101, urlstr);
                                Bundle b = new Bundle();
                                b.putString("msg", appName + "下载错误！读写文件错误 " + (endPos > complete));
                                msg.setData(b);
                                mHandler.sendMessage(msg);
                            }
                        }

						//TODO 可以移到run()最后??
						if (state == DOWNPAUSE || state == DOWNERROR) {
							DownDao.getInstance(context).updataState(threadId, state, appid);
							Thread.currentThread().interrupt();//退出线程
							break;//停止读写文件
                        }
                        // Thread.sleep(1000);// for debug
					}
					Log.w(TAG, fileSize+ "B download spend TIME: " + (System.currentTimeMillis() - spend));
				}
				else if (mHandler != null) {
				    setState(DOWNERROR);
					Message msg = mHandler.obtainMessage(101, urlstr);
					Bundle b = new Bundle();
					b.putString("msg", appName + "下载错误！连接失败" + connection.getResponseCode());
					msg.setData(b);
					mHandler.sendMessage(msg);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				if (mHandler != null) {
				    setState(DOWNERROR);
					Message msg = mHandler.obtainMessage(101, urlstr);
					Bundle b = new Bundle();
					b.putString("msg", appName + "下载错误！请稍后重试");
					msg.setData(b);
					mHandler.sendMessage(msg);
				}
			}
			finally {
				try {
					if (randomAccessFile != null)
						randomAccessFile.close();
					if (is != null)
						is.close();
					if (connection != null)
						connection.disconnect();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void pause() {
		setState(DOWNPAUSE);

        // if (SettingFragment.setHandler != null)
        //  SettingFragment.setHandler.sendEmptyMessage(56);
		mHandler.sendMessage(mHandler.obtainMessage(2, urlstr));//TODO 移到run()中
	}
	public int getState() {
        return state;
    }
	  public void setState(int status) {
		    state = status;
		}
	  @Override
	    public String toString() {
	        return "Downloader [appId=" + appid + ", name=" + appName + ", state=" + state+ "]";
	    }
	
}
