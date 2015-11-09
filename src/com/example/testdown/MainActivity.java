package com.example.testdown;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

public class MainActivity extends FinalActivity{
	private Handler handler=new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case 1://下载正常
				
				break;

			default:
				break;
			}
		}
	};
	@ViewInject(id=R.id.listView1)ListView myList; //初始化listview；
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		List<AppInfo> data=new ArrayList<AppInfo>();
		AppInfo info=new AppInfo();
		info.setName("下载项目");
		data.add(info);
		MyAdapter myadapter =new MyAdapter(data, MainActivity.this);
		myList.setAdapter(myadapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
