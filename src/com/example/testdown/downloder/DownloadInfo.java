package com.example.testdown.downloder;
/***
 * ���������Ϣ��ļ���
 * @author Zhaixd
 *
 */
public class DownloadInfo {
	 private String useId;//�û���ʶ��
		private int downState;// ����״ֵ̬
		private int threadId;// ������id
		private int startPos;// ��ʼ��
		private int endPos;// ������
		private int compeleteSize;// ��ɶ�
		private String url;// �����������ʶ
		private String appName;// ���ص�Ӧ����
		private double appSize;// ���ص�Ӧ�ô�С
		private String iconPath;// ���ص�Ӧ�õ�ͼ���ַ
		private int appId;// ����Ӧ��ID
		private int versionCode;// ���ص�Ӧ�õİ汾��

		public DownloadInfo(String useId,int downState, int threadId, int startPos, int endPos, int compeleteSize,
		        String url, String appName, double appSize, String iconPath, int appId, int versionCode) {
			super();
			this.useId = useId;
			this.downState = downState;
			this.threadId = threadId;
			this.startPos = startPos;
			this.endPos = endPos;
			this.compeleteSize = compeleteSize;
			this.url = url;
			this.appName = appName;
			this.appSize = appSize;
			this.iconPath = iconPath;
			this.appId = appId;
			this.versionCode = versionCode;
		}

		public DownloadInfo() {
	    }

	    @Override
		public String toString() {
			return "DownloadInfo [useId=" + useId + ", downState=" + downState + ", threadId=" + threadId + ", startPos="
			        + startPos + ", endPos=" + endPos + ", compeleteSize=" + compeleteSize + ", url="
			        + url + ", appName=" + appName + ", appSize=" + appSize + ", iconPath=" + iconPath
			        + ", appId=" + appId + ", versionCode=" + versionCode + "]";
		}

		public String getuseId() {
	        return useId;
	    }

	    public void setuseId(String useId) {
	        this.useId = useId;
	    }

	    public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getThreadId() {
			return threadId;
		}

		public void setThreadId(int threadId) {
			this.threadId = threadId;
		}

		public int getStartPos() {
			return startPos;
		}

		public void setStartPos(int startPos) {
			this.startPos = startPos;
		}

		public int getEndPos() {
			return endPos;
		}

		public void setEndPos(int endPos) {
			this.endPos = endPos;
		}

		public int getCompeleteSize() {
			return compeleteSize;
		}

		public void setCompeleteSize(int compeleteSize) {
			this.compeleteSize = compeleteSize;
		}

		/**
		 * @return the appName
		 */
		public String getAppName() {
			return appName;
		}

		/**
		 * @param appName
		 *            the appName to set
		 */
		public void setAppName(String appName) {
			this.appName = appName;
		}

		/**
		 * @return the appSize
		 */
		public double getAppSize() {
			return appSize;
		}

		/**
		 * @param appSize
		 *            the appSize to set
		 */
		public void setAppSize(double appSize) {
			this.appSize = appSize;
		}

		/**
		 * @return the iconPath
		 */
		public String getIconPath() {
			return iconPath;
		}

		/**
		 * @param iconPath
		 *            the iconPath to set
		 */
		public void setIconPath(String iconPath) {
			this.iconPath = iconPath;
		}

		public int getDownState() {
			return downState;
		}

		public void setDownState(int downState) {
			this.downState = downState;
		}

		public int getAppId() {
			return appId;
		}

		public void setAppId(int appId) {
			this.appId = appId;
		}

		public int getVersionCode() {
			return versionCode;
		}

		public void setVersionCode(int versionCode) {
			this.versionCode = versionCode;
		}

}
