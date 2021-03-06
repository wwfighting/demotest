package com.ww.administrator.demotest.util;

import com.ww.administrator.demotest.R;

/**
 * Created by Administrator on 2016/8/20.
 */
public class Constants {

    //api
   

    //file
    public static final String SD_ROOT = FileUtil.getRoot("/Jvawa/");
    public static final String SD_TMP = FileUtil.GetFolder(SD_ROOT + "/temp/");
    public static final String SD_NODE = ToolsUtil.GetFolder(SD_ROOT + "/node/");
    public static final String SD_LOGINUSERNODE = Constants.SD_NODE + "user";
    public static final String SD_DBINTENT = SD_TMP + "/intent";
    public static final String SD_TEMPIMG = SD_TMP + "temp.png";

    //refresh
    public static final String SHOPPING_CART_REFRESH = "SHOPPING_CART_REFRESH";
    public static final String MY_REFRESH = "MY_REFRESH";
    public static final String ADDRESS_REFRESH = "ADDRESS_REFRESH";
    public static final String ADD_SHOPPING_CART_REFRESH = "ADD_SHOPPING_CART_REFRESH";
    public static final String HOME_REFRESH = "HOME_REFRESH";
    public static final String CATE_REFRESH = "CATE_REFRESH";

    //params
    public static final String[] FLOW_LAYOUT_TITLE = {"小康美厨", "尚品魅厨", "精英悦厨", "铂金丽厨",
            "名家雅厨", "欧风御厨", "至尊典厨"};

    public static final String[] CITY_ARRAY = new String[]{"南京", "上海", "兰州", "沈阳"};

    public static final String[] CATE_TITLE = {"圣诞特惠", "新品推荐", "热卖商品", "精品橱柜", "全屋定制"};

    public static final int[] CATE_ICON = {R.drawable.cm_th, R.drawable.cm_new, R.drawable.cm_hot,
                                            R.drawable.cm_jy, R.drawable.cm_qw};

}
