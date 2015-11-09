package com.example.testdown;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.example.testdown.downloder.DownLoader;
import com.example.testdown.downloder.DownloaderTask;

import android.app.Application;

public class MyApplication extends Application{
	public static String useId;//用户的ID
	/** 存放与下载器对应的进度条 */
    public static HashMap<String, ProgressButton> ProgressBars = new HashMap<String, ProgressButton>();
    /** 存放各个下载器 */
    public static HashMap<String, DownLoader> downloaders = new LinkedHashMap<String, DownLoader>();
    /** 存放下载任务 */
    public static HashMap<Integer, DownloaderTask> TASK = new HashMap<Integer, DownloaderTask>();


}
