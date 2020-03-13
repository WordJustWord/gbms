package com.pigmo.gbms.utils;

/**
 * @author zhangqian
 * @date 2019/7/4
 */
public class ApplyContants {
    private ApplyContants(){}
    public static final String LIKESTR = "%";
    public static final String ALL = "-1";
    public static final String SETED = "0";

    public static final String ADD = "新增";
    public static final String UPDATE = "更新";
    public static final String DELETE = "删除";

    public static final String SCANED = "已阅";
    public static final String NOT_SCAN = "未阅";
    public static final String DEALED = "已处理";

    public static final String BY_LAW_FILE_SPILT = "##";

    /**
     * 导出每页查询条数
     */
    public static final int EXPORT_QUERY_PAGE_SIZE = 3000;

    /**
     * 数字检验正则
     */
    public static final String NUMBER_VALIDID_STR = "\\d+";
    /**
     * 邮箱检验正则
     */
    public static final String EMAIL_VALIDID_STR = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public static final String EMAIL_STR = "邮箱";

    /**
     * 导入excel保存数据时批量出的的数据个数
     */
    public static final int BATCHSIZE = 3000;

    public static final String NOT_NUMBER = "不是数字";
    public static final String IS_EMAIL = "必须为邮箱";
    public static final String NOT_NULL = "不能为空";
    public static final String IS_NOT_DICT = "不是字典规范值， 字典规范值包含：";

    public static String getLikeStr(String str){
        return LIKESTR + str.trim() + LIKESTR;
    }
}
