package com.example.springboottest.common.commonstatic;

public enum CommonKuaidi {
    /**
     *电商ID
     */
    KDN_EBUSINESSID("1702198"),
    /**
     *外网服务地址
     */
    SERVER_URL("https://www.wanglin6328.com"),
    /**
     *电商加密私钥，快递鸟提供，注意保管，不要泄漏
     */
    KDN_APPKEY("e80778ca-bbaa-4f09-b8ed-56e058687c7a"),
    /**
     * Request类型
     */
    KDN_REQUEST_TYPE("8003"),
    /**
     *测试url
     */
    KDN_REQURL("https://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx"),
    /**
     * 快递鸟 顺丰快递
     */
    KDN_SF("SF"),
    /**
     * 快递鸟 中通快递
     */
    KDN_ZTO("ZTO"),
    /**
     * 快递鸟 圆通快递
     */
    KDN_YTO("YTO"),
    /**
     * 快递鸟 韵达快递
     */
    KDN_YD("YD"),
    /**
     * 快递鸟 邮政快递
     */
    KDN_YZPY("YZPY"),
    /**
     * 快递鸟 特级送
     */
    KDN_TJS("TJS"),
    /**
     * 快递鸟 天天快递
     */
    KDN_HHTT("HHTT"),
    /**
     * 快递鸟 安能物流
     */
    KDN_ANE("ANE"),
    /**
     * 快递鸟 佳吉物流
     */
    KDN_CNEX("CNEX"),
    /**
     * 快递鸟 百世汇通
     */
    KDN_HTKY("HTKY"),
    /**
     * 快递鸟 申通快递
     */
    KDN_STO("STO"),
    /**
     * 快递100 邮政快递
     */
    KDYB_YZPY("youzhengguonei"),
    /**
     * 快递100 特级送
     */
    KDYB_TJS("lntjs"),
    /**
     * 快递100 快捷快递
     */
    KDYB_KJKD("kuaijiesudi"),
    /**
     * 快递100 天天快递
     */
    KDYB_HHTT("tiantian"),
    /**
     * 快递100 国通快递
     */
    KDYB_GTO("guotongkuaidi"),
    /**
     * 快递100 安能物流
     */
    KDYB_ANE("gzanjcwl"),
    /**
     * 快递100 佳吉物流
     */
    KDYB_CNEX("jiajiwuliu"),
    /**
     * 快递100 百世汇通
     */
    KDYB_HTKY("huitongkuaidi"),
    /**
     * 快递100 申通快递
     */
    KDYB_STO("shentong"),
    /**
     * 发件城市
     */
    FROM_CITY("本溪市"),
            ;

    CommonKuaidi(String message){
        this.value = message;
    }
    public String value;
}
