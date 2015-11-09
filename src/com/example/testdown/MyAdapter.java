package com.example.testdown;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.interfaces.PBEKey;

import com.example.testdown.downloder.DownLoader;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
  private ArrayList<AppInfo> list;
  private Context context;
  private LayoutInflater inflater;
  DownLoader downloder;
  public MyAdapter(List<AppInfo> data, Context context) {
		this.context=context;
		this.list= (ArrayList<AppInfo>) data;
		inflater=LayoutInflater.from(context);
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder ;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.item_list, null);
		    holder=new ViewHolder();
		    holder.text=(TextView) convertView.findViewById(R.id.tv_resouce_name);
			holder.bt_start=(ProgressButton) convertView.findViewById(R.id.btn_start);
			holder.bt_pused=(ProgressButton) convertView.findViewById(R.id.btn_puse);
			holder.bt_open=(ProgressButton) convertView.findViewById(R.id.btn_open);
			holder.bt_start.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					switch (v.getId()) {
					
					case R.id.btn_start:
						holder.bt_pused.setText("继续");
						Log.e("tag", "执行了点击事件" );
						holder.bt_open.setVisibility(v.GONE);
						holder.bt_start.setVisibility( v.GONE);
						holder.bt_pused.setVisibility(v.VISIBLE);
						String url="http://tvstore.oss-cn-hangzhou.aliyuncs.com/LanguagePack_1.9.0.26652-x64-zh_CN.msi";
						String localfile="/date/date/tvStore/";
						 downloder=new DownLoader(url, localfile, 2, context);
						downloder.download();
								break;
					}
					
				}
			});
			holder.bt_pused.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.btn_puse:
						holder.bt_start.setText("下载");
						holder.bt_open.setVisibility(v.GONE);
						holder.bt_start.setVisibility( v.VISIBLE);
						holder.bt_pused.setVisibility(v.GONE);
						downloder.pause();
						break;
					}
				}
			});
			holder.bt_open.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		AppInfo bean=list.get(position);
		holder.text.setText(bean.getName());
		return convertView;
	}
	public  static class ViewHolder{
		TextView text;
		ProgressButton bt_start;
		ProgressButton bt_pused;
		ProgressButton bt_open;
	}

}
