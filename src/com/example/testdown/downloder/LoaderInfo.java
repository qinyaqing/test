package com.example.testdown.downloder;
/***
 * 自定义的一个记载下载器详细信息的类
 * @author songyujin
 *
 */
public class LoaderInfo {
	
	     public int fileSize;//文件大小
	     private int complete;//完成度
	     private String urlstring;//下载器标识
	     public LoaderInfo(int fileSize, int complete, String urlstring) {
	         this.fileSize = fileSize;
	         this.complete = complete;
	         this.urlstring = urlstring;
	     }
	     public LoaderInfo() {
	     }
	     public int getFileSize() {
	         return fileSize;
	     }
	     public void setFileSize(int fileSize) {
	         this.fileSize = fileSize;
	     }
	     public int getComplete() {
	         return complete;
	     }
	     public void setComplete(int complete) {
	         this.complete = complete;
	     }
	     public String getUrlstring() {
	         return urlstring;
	     }
	     public void setUrlstring(String urlstring) {
	         this.urlstring = urlstring;
	     }
	     @Override
	     public String toString() {
	         return "LoadInfo [fileSize=" + fileSize + ", complete=" + complete
	                 + ", urlstring=" + urlstring + "]";
	     }
	 }

