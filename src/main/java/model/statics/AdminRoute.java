package model.statics;

/**
 * 用于保存URL常量
 */
public class AdminRoute {
    public static final String MARSH = "/marsh";
    public static final String ADMIN = "/admin/v1";

    public static final String SYSUSER = MARSH + "/user";
    public static final String LOGIN =  MARSH + "/login";
    public static final String LOGOUT = "/logout";
    public static final String SELFINFO = ADMIN + "/self";
    public static final String LOADFILE = ADMIN + "/upload/photo";
    public static final String HANDLE = ADMIN + "/handle";


    public static final String SYSTEM_TOOL = ADMIN + "/system/tool";

}
