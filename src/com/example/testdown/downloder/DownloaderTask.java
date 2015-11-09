package com.example.testdown.downloder;

import com.example.testdown.MyApplication;
import com.example.testdown.ProgressButton;
import com.example.testdown.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

@SuppressLint("HandlerLeak")
public class DownloaderTask extends AsyncTask<String, Integer, LoaderInfo>{
	private static final String TAG = DownloaderTask.class.getSimpleName();
	private DownLoader downloader=null;
	private View v;
	private final Context context;
	private  int appId;
	private final String appUrl;
	private final String appName;
	private final double appSize;
	private int verCode;
	private int curPercent = 0;
	private int tempPercent = -1;
	public DownloaderTask(View v, Context ctx, int appId, String url, String appName, double appSize, int versionCode) {
			this.v = v;
			this.context = ctx;
			this.appId = appId;
	        this.appUrl = url;
			this.appName = appName;
			this.appSize = appSize;//this.appSize = 0.0;
	        this.verCode = versionCode;
		}
	/**
	 * 利用消息处理机制适时更新进度条
	 */
	public Handler handler=new Handler(){
		@SuppressWarnings("unused")
		public void handlerMessage(Message msg){
			switch (msg.what) {
			case 1:// 下载正常
				String url=(String) msg.obj;
				int compeleteSize=msg.arg1;
				int fileSize = msg.arg2;
				curPercent = (int) (compeleteSize * 100 / fileSize);
				ProgressButton bar = MyApplication.ProgressBars.get(url);
                if (bar != null) {
                    // 设置进度条按读取的length长度更新
                    bar.setProgress(curPercent);// incrementProgressBy(length);
                    bar.setEnabled(true);// 点击时禁用,开始下载前恢复
                    v.setEnabled(true);// 点击时禁用,开始下载前恢复// v.findViewById(R.id.btnAppLoad)
				}
				break;

			 case 2:// 暂停并取消通知
				 final String url2 = (String) msg.obj;
				 MyApplication.downloaders.remove(url2);
                 ProgressButton barPause = MyApplication.ProgressBars.get(url2);
                 if (barPause != null) {
                     RelativeLayout layout = (RelativeLayout) barPause.getParent().getParent();
                     ((LinearLayout) layout.findViewById(R.id.btn_puse)).setVisibility(View.VISIBLE);
                     ((ProgressButton) layout.findViewById(R.id.btn_puse)).setText("继续");
                    // ((Button) layout.findViewById(R.id.btnAppLoad)).setEnabled(true);// 点击时禁用,暂停任务后恢复
                     barPause.setEnabled(true);// 点击时禁用,暂停任务后恢复
                     MyApplication.ProgressBars.remove(url2);
                     //TODO 把downloaders操作统一到此处
                 }
				break;
			 case 4:
                 curPercent = 100;
                 final String url4 = (String) msg.obj;
                 ProgressButton barSucc = MyApplication.ProgressBars.get(url4);
                 if (barSucc != null) {
                     // 下载完成后恢复按钮并移除ProgressBars中进度条
                     MyApplication.downloaders.remove(url4);
                     MyApplication.ProgressBars.remove(url4);
                     RelativeLayout layout = (RelativeLayout) barSucc.getParent().getParent();
                     ((LinearLayout) layout.findViewById(R.id.btn_open)).setVisibility(View.VISIBLE);
                    ProgressButton btnAppLoad = (ProgressButton) layout.findViewById(R.id.btn_open);
                     if (btnAppLoad != null) {// NullPointerException
                         btnAppLoad.setText("安装");
                         btnAppLoad.setBackgroundResource(R.drawable.main_button_focus);
                         // btnAppLoad.setCompoundDrawables(null, null, null, null);
                     }
                     barSucc = null;
                 }
                 break;
			}
		}
	};
	@Override
	protected void onPreExecute() {
		if (v != null) {
            LinearLayout pbLayout = (LinearLayout) ((View) v.getParent().getParent()).findViewById(R.id.btn_puse);
            pbLayout.setVisibility(View.VISIBLE);

            ProgressButton bar = (ProgressButton) pbLayout.findViewById(R.id.btn_puse);
            // 保存任务、显示进度条并通知//
            if (MyApplication.ProgressBars.get(appUrl) == null) {
                MyApplication.ProgressBars.put(appUrl, bar);
            }
            ProgressButton btn = (ProgressButton) pbLayout.findViewById(R.id.btn_puse);
            // btn.setBackgroundResource(R.drawable.progress_holo_20);
            // 先禁用,下载启动后handleMessage中恢复
            btn.setEnabled(false);
            btn.setText("暂停");
            bar.setEnabled(false);
        }

	}
	@Override
	protected LoaderInfo doInBackground(String... params) {
		 String iconPath = params[0];
			String localfile = params[1];
			int threadcount = Integer.parseInt(params[2]);
			
			// 初始化一个downloader下载器
			downloader = (DownLoader) MyApplication.downloaders.get(appUrl);
			if (downloader == null) {
				downloader = new DownLoader(appUrl, localfile, threadcount, context.getApplicationContext(), handler,
				        appName, appSize, iconPath, appId, verCode);
				MyApplication.downloaders.put(appUrl, downloader);
				MyApplication.TASK.put(appId, this);
			}
			
	        if (downloader.isDownloading()) {
	            // Log.w(TAG,  "doBackground IS doing:" + localfile);
			    return null;
			}
	        
//	        // 已有队列超限则提示
//	        if (MyApplication.downloaders.size() > Constants.MAX_NUM_DOWNQUEEN) {
//	            Log.e(TAG, "doInBackground SIZE:" + MyApplication.downloaders.size());
//	            // handler通知移除任务并提示
//	            Message msg = handler.obtainMessage(102, appUrl);
//	            Bundle b = new Bundle();
//	            b.putString("msg", context.getString(R.string.download_task_waiting));
//	            msg.setData(b);
//	            handler.sendMessage(msg);
//	            return null;
//	        }
			// 得到下载信息集合
			return downloader.getDownLoaderInfos();
		
	}

}
