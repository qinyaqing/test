package com.example.testdown.downloder;

import java.io.File;

import android.os.Environment;

/**
 * 鍏ㄥ眬甯搁噺銆侀瓟楝兼暟瀛?
 * 
 * @author Huangchao 2014-12-8
 */
public class Constants {
	/** 调试or发布.发布时改为false! */
    public static final boolean FLAG_IS_DEBUG = false;//true;//
    /** 版本升级引导, 版本号变大1 */
    public static final int FLAG_GUIDE_VERSION = 1;// 鎺ユ敹骞挎挱??Intent.ACTION_PACKAGE_FIRST_LAUNCH
  //TODO 清理旧版本目录
	
    /** viewpager当前切换位置 */
	public static final String KEY_INT_INDEX = "intent_index";
	/** 警员ID key */
	public static final String KEY_STR_POLICEID = "policeId";
	/** 搜索 content key */
    public static final String KEY_SEARCH_KEY = "searchContent";
    
    /** 返回请求成功码 */
    public static final String CODE_SUCESS_END = "000000";
    
    /** 输入最大长度 */
    public static final int MAX_INT_INPUT = 20;
    /** 评价最大长度 */
    public static final int MAX_SCORE_COUNT = 70;
    /** 下载最大任务数 */
    public static final int MAX_NUM_DOWNLOAD = 2;
    /** 下载最大等待数 */
    public static final int MAX_NUM_DOWNQUEEN = 5;
    
    /** SD目录 */
    private static final String FILE_STORAGE = Environment.getExternalStorageDirectory().getPath() + File.separator;
    /** 应用中心 数据根目录 */
    public static final String FILE_APP_HOME = Constants.FILE_STORAGE + "NJSIA/PAStore/";
    // 交由StorageManager创建
//	static {
//		File file = new File(Constants.FILE_APP_HOME);
//        if (!file.exists()) {
//            Log.e("Constants", FILE_APP_HOME + " MK HOME DIR !" + file.mkdirs());
//        }
//	}
    /** 下载目录 */
	public static final String FILE_MY_DOWNLOAD = FILE_APP_HOME + "Download" + File.separator;
	/** LOG目录 */
	public static final String FILE_MY_LOG = FILE_APP_HOME + "Log" + File.separator;
	/** 插件权限写缓存文件目录 */
	public static final String FILE_MY_CACHE = FILE_APP_HOME + "cache" + File.separator;
	//    /**  固定存放下载的应用的路径：SD卡目录下   */
    // public static final String SD_PATH = "/mnt/sdcard/";

	public final static String KEY_DLZH = "com.njsia.police.appstore.dlzh";
    public final static String KEY_DLMM = "com.njsia.police.appstore.dlmm";

    public static final String ACTION_STORE_EXIT = "intent.action.njsia.pastore.EXIT";

    /***********启动第三方应用key TODO 同步更换key****************************/
//    /** 用户ID long */
//    public static final String LAUNCH_APP_ID = "njsia.pastore.launch.id";
//    /** 姓名 */
//    public static final String LAUNCH_APP_NAME = "njsia.pastore.launch.name";
//    /** 警号 */
//    public static final String LAUNCH_APP_POLICENO = "njsia.pastore.launch.policeNo";
//    /** 身份证号 */
//    public static final String LAUNCH_APP_IDENTITY = "njsia.pastore.launch.identity";
//    /** 所属单位编码 long */
//    public static final String LAUNCH_APP_DWID = "njsia.pastore.launch.dwId";
//    /** 登录账号(目前使用警号policeNo登录) */
//    public static final String LAUNCH_APP_LOGIN_ID = "njsia.pastore.launch.loginId";
//    /** 登录密码 (目前同policePassword)*/
//    public static final String LAUNCH_APP_LOGIN_PWD = "njsia.pastore.launch.loginPwd";
//    /** 登录时间 */
//    public static final String LAUNCH_APP_LOGIN_TIME = "njsia.pastore.launch.loginTime";
//    /** 应用中心接入网络类型  */
//    public static final String LAUNCH_APP_NET_POINT = "njsia.pastore.launch.netPoint";
//    /** 经纬坐标(#分隔)  */
//    public static final String LAUNCH_APP_LOCATION = "njsia.pastore.launch.location";
    /** 用户ID long */
    public static final String LAUNCHER_APP_ID = "id";
    /** 姓名 */
    public static final String LAUNCHER_APP_NAME = "name";
    /** 警号 */
    public static final String LAUNCHER_APP_POLICENO = "policeNo";
    /** 身份证号 */
    public static final String LAUNCHER_APP_IDENTITY = "identity";
    /** 所属单位编码 long */
    public static final String LAUNCHER_APP_DWID = "dwId";
    /** 登录账号(目前使用警号policeNo登录) */
    public static final String LAUNCHER_APP_LOGIN_ID = "loginId";
    /** 登录密码 (目前同policePassword) */
    public static final String LAUNCHER_APP_LOGIN_PWD = "loginPwd";
    /** 登录时间 */
    public static final String LAUNCHER_APP_LOGIN_TIME = "loginTime";
    /** 应用中心接入网络类型  */
    public static final String LAUNCHER_APP_NET_POINT = "netPoint";
    /** 坐标,经纬度(#分隔)*/
    public static final String LAUNCHER_APP_LOCATION = "location";

    /***********插件鉴权key ****************************/
	/** 请求权限的APP包名 */
    public static final String AUTH_APP_PKG = "njsia.pastore.auth.appPkg";
    /** @deprecated 请求权限的APP回调组件名 */
    public static final String AUTH_APP_CMP = "njsia.pastore.auth.appCmp";
    /** TODO APP versionCode */
    public static final String AUTH_APP_VER = "njsia.pastore.auth.appVer";
	/** app请求权限的插件包名 */
    public static final String AUTH_PLUG_PKG = "njsia.pastore.auth.plugPkg";
    /** 插件versionCode */
    public static final String AUTH_PLUG_VER = "njsia.pastore.auth.plugVer";
    
    /** 插件权限 KEYSOLT:SHA1<FingerPrint+包名 */
    public static final String AUTH_TOKEN_SOLT = "16QewE458BLNsAy3qfgdwBYf";
    /** 插件权限 KEYSOLT:MD5<简包名 */
    public static final String COMMON_TOKEN_KEY = "7109073b9e89295181ed5a5842128ef5";
    /** 密钥KEY 返回给应用 */
    public static final String AUTH_TOKEN_REQUEST = "njsia.pastore.auth.plugKey";
//    /** TODO 密钥ERROR MSG. 返回给请求者 */
//    public static final String AUTH_TOKEN_MSG = "njsia.pastore.auth.msgKey";
//    /** 响应KEY 插件请求鉴定有效性*/
//    public static final String AUTH_TOKEN_VALID = "njsia.pastore.auth.validKey";
    
    /** 请求码标识KEY 由请求者带入 应用1 插件3 */
    public static final String AUTH_REQUEST_CODE = "njsia.pastore.auth.requestCode";
    /** 请求码标识值 应用1 */
    public static final int AUTH_REQUEST_CODE_APP = 1;
    /** 请求码标识值 插件3 */
    public static final int AUTH_REQUEST_CODE_PLUG = 3;
    
    /** 插件权限resultCode 正常 */
    public static final int AUTH_CODE_OK = -3000;
    /** 插件权限resultCode 参数无效 */
    public static final int AUTH_CODE_ILLEGAL_PARAM = -3010;
    /** 插件权限resultCode 没有/缺少数据 */
    public static final int AUTH_CODE_NULL = -3020;
    /** 插件权限resultCode 没有该应用数据(未安装) */
    public static final int AUTH_CODE_NULL_APP = -3021;
    /** 插件权限resultCode 没有该插件数据(未安装) */
    public static final int AUTH_CODE_NULL_PLUG = -3022;
    /** 插件权限resultCode 没有权限 */
    public static final int AUTH_CODE_DENIED = -3030;
    /** 插件权限resultCode 鉴权失败 */
    public static final int AUTH_CODE_ERROR = -3050;
    /** 插件权限resultCode 生成token失败 */
    public static final int AUTH_CODE_ERROR_BUILD = -3051;
    /** 插件权限resultCode 缓存器错误 */
    public static final int AUTH_CODE_ERROR_CACHE = -3052;
    /** 插件权限resultCode 缓存token失败 */
    public static final int AUTH_CODE_ERROR_ENQUEEN = -3053;
    /** 插件权限resultCode 密钥失效 */
    public static final int AUTH_CODE_EXPIRED = -3060;
    /** 插件权限resultCode 插件版本过低 */
    public static final int AUTH_CODE_OUT_VERSION = -3081;
    
}
