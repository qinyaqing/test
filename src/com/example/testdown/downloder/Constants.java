package com.example.testdown.downloder;

import java.io.File;

import android.os.Environment;

/**
 * 全局常量、魔鬼数�?
 * 
 * @author Huangchao 2014-12-8
 */
public class Constants {
	/** ����or����.����ʱ��Ϊfalse! */
    public static final boolean FLAG_IS_DEBUG = false;//true;//
    /** �汾��������, �汾�ű��1 */
    public static final int FLAG_GUIDE_VERSION = 1;// 接收广播??Intent.ACTION_PACKAGE_FIRST_LAUNCH
  //TODO ����ɰ汾Ŀ¼
	
    /** viewpager��ǰ�л�λ�� */
	public static final String KEY_INT_INDEX = "intent_index";
	/** ��ԱID key */
	public static final String KEY_STR_POLICEID = "policeId";
	/** ���� content key */
    public static final String KEY_SEARCH_KEY = "searchContent";
    
    /** ��������ɹ��� */
    public static final String CODE_SUCESS_END = "000000";
    
    /** ������󳤶� */
    public static final int MAX_INT_INPUT = 20;
    /** ������󳤶� */
    public static final int MAX_SCORE_COUNT = 70;
    /** ������������� */
    public static final int MAX_NUM_DOWNLOAD = 2;
    /** �������ȴ��� */
    public static final int MAX_NUM_DOWNQUEEN = 5;
    
    /** SDĿ¼ */
    private static final String FILE_STORAGE = Environment.getExternalStorageDirectory().getPath() + File.separator;
    /** Ӧ������ ���ݸ�Ŀ¼ */
    public static final String FILE_APP_HOME = Constants.FILE_STORAGE + "NJSIA/PAStore/";
    // ����StorageManager����
//	static {
//		File file = new File(Constants.FILE_APP_HOME);
//        if (!file.exists()) {
//            Log.e("Constants", FILE_APP_HOME + " MK HOME DIR !" + file.mkdirs());
//        }
//	}
    /** ����Ŀ¼ */
	public static final String FILE_MY_DOWNLOAD = FILE_APP_HOME + "Download" + File.separator;
	/** LOGĿ¼ */
	public static final String FILE_MY_LOG = FILE_APP_HOME + "Log" + File.separator;
	/** ���Ȩ��д�����ļ�Ŀ¼ */
	public static final String FILE_MY_CACHE = FILE_APP_HOME + "cache" + File.separator;
	//    /**  �̶�������ص�Ӧ�õ�·����SD��Ŀ¼��   */
    // public static final String SD_PATH = "/mnt/sdcard/";

	public final static String KEY_DLZH = "com.njsia.police.appstore.dlzh";
    public final static String KEY_DLMM = "com.njsia.police.appstore.dlmm";

    public static final String ACTION_STORE_EXIT = "intent.action.njsia.pastore.EXIT";

    /***********����������Ӧ��key TODO ͬ������key****************************/
//    /** �û�ID long */
//    public static final String LAUNCH_APP_ID = "njsia.pastore.launch.id";
//    /** ���� */
//    public static final String LAUNCH_APP_NAME = "njsia.pastore.launch.name";
//    /** ���� */
//    public static final String LAUNCH_APP_POLICENO = "njsia.pastore.launch.policeNo";
//    /** ���֤�� */
//    public static final String LAUNCH_APP_IDENTITY = "njsia.pastore.launch.identity";
//    /** ������λ���� long */
//    public static final String LAUNCH_APP_DWID = "njsia.pastore.launch.dwId";
//    /** ��¼�˺�(Ŀǰʹ�þ���policeNo��¼) */
//    public static final String LAUNCH_APP_LOGIN_ID = "njsia.pastore.launch.loginId";
//    /** ��¼���� (ĿǰͬpolicePassword)*/
//    public static final String LAUNCH_APP_LOGIN_PWD = "njsia.pastore.launch.loginPwd";
//    /** ��¼ʱ�� */
//    public static final String LAUNCH_APP_LOGIN_TIME = "njsia.pastore.launch.loginTime";
//    /** Ӧ�����Ľ�����������  */
//    public static final String LAUNCH_APP_NET_POINT = "njsia.pastore.launch.netPoint";
//    /** ��γ����(#�ָ�)  */
//    public static final String LAUNCH_APP_LOCATION = "njsia.pastore.launch.location";
    /** �û�ID long */
    public static final String LAUNCHER_APP_ID = "id";
    /** ���� */
    public static final String LAUNCHER_APP_NAME = "name";
    /** ���� */
    public static final String LAUNCHER_APP_POLICENO = "policeNo";
    /** ���֤�� */
    public static final String LAUNCHER_APP_IDENTITY = "identity";
    /** ������λ���� long */
    public static final String LAUNCHER_APP_DWID = "dwId";
    /** ��¼�˺�(Ŀǰʹ�þ���policeNo��¼) */
    public static final String LAUNCHER_APP_LOGIN_ID = "loginId";
    /** ��¼���� (ĿǰͬpolicePassword) */
    public static final String LAUNCHER_APP_LOGIN_PWD = "loginPwd";
    /** ��¼ʱ�� */
    public static final String LAUNCHER_APP_LOGIN_TIME = "loginTime";
    /** Ӧ�����Ľ�����������  */
    public static final String LAUNCHER_APP_NET_POINT = "netPoint";
    /** ����,��γ��(#�ָ�)*/
    public static final String LAUNCHER_APP_LOCATION = "location";

    /***********�����Ȩkey ****************************/
	/** ����Ȩ�޵�APP���� */
    public static final String AUTH_APP_PKG = "njsia.pastore.auth.appPkg";
    /** @deprecated ����Ȩ�޵�APP�ص������ */
    public static final String AUTH_APP_CMP = "njsia.pastore.auth.appCmp";
    /** TODO APP versionCode */
    public static final String AUTH_APP_VER = "njsia.pastore.auth.appVer";
	/** app����Ȩ�޵Ĳ������ */
    public static final String AUTH_PLUG_PKG = "njsia.pastore.auth.plugPkg";
    /** ���versionCode */
    public static final String AUTH_PLUG_VER = "njsia.pastore.auth.plugVer";
    
    /** ���Ȩ�� KEYSOLT:SHA1<FingerPrint+���� */
    public static final String AUTH_TOKEN_SOLT = "16QewE458BLNsAy3qfgdwBYf";
    /** ���Ȩ�� KEYSOLT:MD5<����� */
    public static final String COMMON_TOKEN_KEY = "7109073b9e89295181ed5a5842128ef5";
    /** ��ԿKEY ���ظ�Ӧ�� */
    public static final String AUTH_TOKEN_REQUEST = "njsia.pastore.auth.plugKey";
//    /** TODO ��ԿERROR MSG. ���ظ������� */
//    public static final String AUTH_TOKEN_MSG = "njsia.pastore.auth.msgKey";
//    /** ��ӦKEY ������������Ч��*/
//    public static final String AUTH_TOKEN_VALID = "njsia.pastore.auth.validKey";
    
    /** �������ʶKEY �������ߴ��� Ӧ��1 ���3 */
    public static final String AUTH_REQUEST_CODE = "njsia.pastore.auth.requestCode";
    /** �������ʶֵ Ӧ��1 */
    public static final int AUTH_REQUEST_CODE_APP = 1;
    /** �������ʶֵ ���3 */
    public static final int AUTH_REQUEST_CODE_PLUG = 3;
    
    /** ���Ȩ��resultCode ���� */
    public static final int AUTH_CODE_OK = -3000;
    /** ���Ȩ��resultCode ������Ч */
    public static final int AUTH_CODE_ILLEGAL_PARAM = -3010;
    /** ���Ȩ��resultCode û��/ȱ������ */
    public static final int AUTH_CODE_NULL = -3020;
    /** ���Ȩ��resultCode û�и�Ӧ������(δ��װ) */
    public static final int AUTH_CODE_NULL_APP = -3021;
    /** ���Ȩ��resultCode û�иò������(δ��װ) */
    public static final int AUTH_CODE_NULL_PLUG = -3022;
    /** ���Ȩ��resultCode û��Ȩ�� */
    public static final int AUTH_CODE_DENIED = -3030;
    /** ���Ȩ��resultCode ��Ȩʧ�� */
    public static final int AUTH_CODE_ERROR = -3050;
    /** ���Ȩ��resultCode ����tokenʧ�� */
    public static final int AUTH_CODE_ERROR_BUILD = -3051;
    /** ���Ȩ��resultCode ���������� */
    public static final int AUTH_CODE_ERROR_CACHE = -3052;
    /** ���Ȩ��resultCode ����tokenʧ�� */
    public static final int AUTH_CODE_ERROR_ENQUEEN = -3053;
    /** ���Ȩ��resultCode ��ԿʧЧ */
    public static final int AUTH_CODE_EXPIRED = -3060;
    /** ���Ȩ��resultCode ����汾���� */
    public static final int AUTH_CODE_OUT_VERSION = -3081;
    
}
