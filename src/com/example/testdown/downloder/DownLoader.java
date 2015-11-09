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
 * �첽������
 * @author �����
 *
 */
public class DownLoader {
	private static final String TAG = DownLoader.class.getSimpleName();
	private String urlstr;// ���صĵ�ַ
    private String localfile;// ����·��
    private int threadcount;// �߳���
    private Handler mHandler;// ��Ϣ������
    private int fileSize;// ��Ҫ���ص��ļ��Ĵ�С
    private Context context;
    private List<DownloadInfo> infos;// ���������Ϣ��ļ���
    /* �����������ص�״̬����ʼ�����������أ���ͣ����� */
    public static final int INIT = 1;
    public static final int DOWNLOADING = 2;
    public static final int DOWNPAUSE = 3;
    public static final int DOWNSUCCESS = 4;
    private static final int DOWNERROR = 5;
    /** ����״̬����ʼ0����ȷ����ͨ��set������ֵ���޸� */
    private int state = INIT;// 0;
    private String appName;
    private double appSize;
    private String iconPath;
    private int curPrecent = 0;
    private int tempPrecent = -1;
    private int appid = 0;
    private int versionCode;
    /**
	 * ���ݴ��ݵ����ݽ������ص�����������
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
        this.fileSize = (int) appSize;//TODO ����
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
	 * �ж��Ƿ���������
	 */
	public boolean isDownloading() {
        Log.w(TAG, appid + " isDownloading:" + state);
		return state == DOWNLOADING;
	}
	public LoaderInfo getDownLoaderInfos(){
		LoaderInfo loaderinfos=null;
		if (DownDao.getInstance(context).hasInfo(appid)){
			// �õ����ݿ������е�urlstr���������ľ�����Ϣ
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
				loaderinfos=null;//��Ϣ�쳣,����ʧ��
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
				 // ����infos�е����ݵ����ݿ�
	                DownDao.getInstance(context).saveInfos(infos);
	                loaderinfos = new LoaderInfo(fileSize, 0, urlstr);
			}
		}
		 if (mHandler != null /*&& !wait*/) {
		        // ��ʼǰͳһ�ڴ˷���һ��// ��ȡ�����ļ������ݿ���Ϣ,����Ϣ���ϲ����
	            if (loaderinfos != null) {
	                // setState(INIT);
	                Message msg = mHandler.obtainMessage(3, urlstr);
	                msg.arg1 = loaderinfos.getComplete();
	                msg.arg2 = loaderinfos.getFileSize();
	                // Bundle b = new Bundle();
	                // b.putString("msg", "���ؿ�ʼ��");
	                // msg.setData(b);
	                msg.sendToTarget();
	            }
	            else {
	                setState(DOWNERROR);
	                Message msg = mHandler.obtainMessage(101, urlstr);
	                Bundle b = new Bundle();
	                b.putString("msg", "���ش����ļ������쳣");
	                msg.setData(b);
	                mHandler.sendMessage(msg);
	            }
	        }
		return loaderinfos;
		
		
	}
	/**
	 * �����߳̿�ʼ��������
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
				// ���÷�Χ����ʽΪRange��bytes x-y;
                connection.setRequestProperty("Range", "bytes=" + (startPos + complete) + "-" + endPos);

                Log.e(TAG, localfile + " createNewFile: " + new File(localfile).createNewFile());
				randomAccessFile = new RandomAccessFile(localfile, "rwd");
				randomAccessFile.seek(startPos + complete);

                Log.w(TAG, startPos + "|" + complete + "|" + endPos);
                Log.w(TAG, connection.getResponseCode() + " :rsp length: " + connection.getContentLength());
                if ((connection.getResponseCode() == 200 || connection.getResponseCode() == 206)
                        && connection.getContentLength() > 0) {
					// ��Ҫ���ص��ļ�д�������ڱ���·���µ��ļ���
					is = connection.getInputStream();
					byte[] buffer = new byte[4096];// ������,���󻺳���??
					int length = -1;
					long spend = System.currentTimeMillis();
					// ע��֪ͨ���޸�Ҫͬ����DownloadTask��SettingFragment�е�handleMessage�У�
					while ((length = is.read(buffer)) != -1) {
						randomAccessFile.write(buffer, 0, length);
						complete += length;
                        // Log.i(TAG, "end="+endPos+" compelete="+complete+" precent/tid="+threadId);

						if (endPos + 1 > complete) {
						    // �������ݿ��е�������Ϣ(Ӧ�������Ը���ʱthreadIdΪ-1,��д�����ݿ�)
                            if (threadId > -1) {
						        DownDao.getInstance(context).updataInfos(threadId, complete, appid);
                                // ��ǰ��download()��,ֻ��һ�� // DownDao.getInstance(context).updataState(threadId, state, appid);
                            }
							curPrecent = (int) (100 * complete / endPos);
							// ���ð��仯ˢ��,���ð����ȼ��ˢ�ºͰ�ʱˢ��
							/* curPrecent % 2 == 0 &&  && curPrecent <= 100*/
							if (tempPrecent < curPrecent && mHandler != null ) {
								tempPrecent = curPrecent;
                                // ����Ϣ��������Ϣ�������������Խ��������и���
								mHandler.obtainMessage(1, complete, endPos, urlstr).sendToTarget();
								// ͳһ��DownloadTask��handleMessage֪ͨ// ��̬���������нǱꡢ ���ع�����TextView
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
                                b.putString("msg", appName + "���ش��󣡶�д�ļ����� " + (endPos > complete));
                                msg.setData(b);
                                mHandler.sendMessage(msg);
                            }
                        }

						//TODO �����Ƶ�run()���??
						if (state == DOWNPAUSE || state == DOWNERROR) {
							DownDao.getInstance(context).updataState(threadId, state, appid);
							Thread.currentThread().interrupt();//�˳��߳�
							break;//ֹͣ��д�ļ�
                        }
                        // Thread.sleep(1000);// for debug
					}
					Log.w(TAG, fileSize+ "B download spend TIME: " + (System.currentTimeMillis() - spend));
				}
				else if (mHandler != null) {
				    setState(DOWNERROR);
					Message msg = mHandler.obtainMessage(101, urlstr);
					Bundle b = new Bundle();
					b.putString("msg", appName + "���ش�������ʧ��" + connection.getResponseCode());
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
					b.putString("msg", appName + "���ش������Ժ�����");
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
		mHandler.sendMessage(mHandler.obtainMessage(2, urlstr));//TODO �Ƶ�run()��
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
