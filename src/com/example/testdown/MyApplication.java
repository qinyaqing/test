package com.example.testdown;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.example.testdown.downloder.DownLoader;
import com.example.testdown.downloder.DownloaderTask;

import android.app.Application;

public class MyApplication extends Application{
	public static String useId;//�û���ID
	/** �������������Ӧ�Ľ����� */
    public static HashMap<String, ProgressButton> ProgressBars = new HashMap<String, ProgressButton>();
    /** ��Ÿ��������� */
    public static HashMap<String, DownLoader> downloaders = new LinkedHashMap<String, DownLoader>();
    /** ����������� */
    public static HashMap<Integer, DownloaderTask> TASK = new HashMap<Integer, DownloaderTask>();


}
