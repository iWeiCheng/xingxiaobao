package com.jiajun.demo.moudle.me.entities;

import java.util.List;

/**
 * Created by dan on 2017/11/27/027.
 */

public class UserInfoBean {


    /**
     * name : 测试员9
     * level : 高级保险专家
     * headImg : /upload/newfile20180307170410205.png
     * carInfo : {"name":"车险订单","imgUrl":"/agent-new/weixin/images/xb_v2_wo_dicon02.png","url":"http://prepare.implus100.com/agent-new/order/order_list.jsp?userIds=ff80808161195bda01612aff27190655"}
     * ariskInfo : {"name":"个险订单","imgUrl":"/agent-new/weixin/images/xb_v2_wo_dicon01.png","url":"http://prepare.implus100.com/agent-new/weixin/build.jsp"}
     * otherInfo : {"name":"其他订单","imgUrl":"/agent-new/weixin/images/xb_v2_wo_dicon03.png","url":"http://prepare.implus100.com/agent-new/extended_warranty/order_list.jsp?userIds=ff80808161195bda01612aff27190655"}
     * accIncome : {"name":"佣金累计","imgUrl":"/agent-new/weixin/images/xb_v2_wo_licon01.png","accIncomeNum":"0.00","url":""}
     * balance : {"name":"佣金余额","balanceNum":0,"imgUrl":"/agent-new/weixin/images/xb_v2_wo_licon02.png","url":""}
     * carRevenue : {"name":"车险收入","balanceNum":"0.00","imgUrl":"","url":""}
     * aRiskRevenue : {"name":"个险收入","balanceNum":"0.00","imgUrl":"","url":""}
     * otherRevenue : {"name":"其他收入","balanceNum":"0.00","imgUrl":"","url":""}
     * compensation : {"name":"补偿申请","imgUrl":"","url":"http://prepare.implus100.com//agent-new/weixin/compensationList.jsp?userIds=ff80808161195bda01612aff27190655"}
     * myFans : {"name":"我的团队","desc":"一起玩的伙伴","imgUrl":"/agent-new/weixin/images/xb_v2_icon06.png","url":"http://prepare.implus100.com//agent/member_center/my_branch.jsp?userIds=ff80808161195bda01612aff27190655"}
     * invitFriend : {"name":"邀请好友","desc":"好事当然要分享","imgUrl":"/agent-new/weixin/images/xb_v2_wo_gongicon04.png","url":"http://prepare.implus100.com/agent/user/qrcode.jsp?mobile=14000000502&appShorName=rbb&userIds=ff80808161195bda01612aff27190655"}
     * setManagInfo : [{"name":"常见问题","imgUrl":"/agent-new/weixin/images/xb_v2_wo_qticon02.png","url":"http://prepare.implus100.com/agent-new/handle_new/common_problem.jsp?userIds=ff80808161195bda01612aff27190655"},{"name":"设置管理","imgUrl":"/agent-new/weixin/images/xb_v2_arrow3.png","url":"http://prepare.implus100.com//agent-new/setting_management/setting_main.jsp?userIds=ff80808161195bda01612aff27190655"}]
     */

    private String name;
    private String level;
    private String headImg;
    private String commission_open;
    private CarInfoBean carInfo;
    private AriskInfoBean ariskInfo;
    private OtherInfoBean otherInfo;

    public BarterBean getBarter() {
        return barter;
    }

    public void setBarter(BarterBean barter) {
        this.barter = barter;
    }

    private AccIncomeBean accIncome;
    private BalanceBean balance;
    private CarRevenueBean carRevenue;
    private ARiskRevenueBean aRiskRevenue;
    private OtherRevenueBean otherRevenue;
    private CompensationBean compensation;
    private MyFansBean myFans;
    private InvitFriendBean invitFriend;
    private BarterBean barter;
    private List<SetManagInfoBean> setManagInfo;

    public String getCommission_open() {
        return commission_open;
    }

    public void setCommission_open(String commission_open) {
        this.commission_open = commission_open;
    }

    public ARiskRevenueBean getaRiskRevenue() {
        return aRiskRevenue;
    }

    public void setaRiskRevenue(ARiskRevenueBean aRiskRevenue) {
        this.aRiskRevenue = aRiskRevenue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public CarInfoBean getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfoBean carInfo) {
        this.carInfo = carInfo;
    }

    public AriskInfoBean getAriskInfo() {
        return ariskInfo;
    }

    public void setAriskInfo(AriskInfoBean ariskInfo) {
        this.ariskInfo = ariskInfo;
    }

    public OtherInfoBean getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(OtherInfoBean otherInfo) {
        this.otherInfo = otherInfo;
    }

    public AccIncomeBean getAccIncome() {
        return accIncome;
    }

    public void setAccIncome(AccIncomeBean accIncome) {
        this.accIncome = accIncome;
    }

    public BalanceBean getBalance() {
        return balance;
    }

    public void setBalance(BalanceBean balance) {
        this.balance = balance;
    }

    public CarRevenueBean getCarRevenue() {
        return carRevenue;
    }

    public void setCarRevenue(CarRevenueBean carRevenue) {
        this.carRevenue = carRevenue;
    }

    public ARiskRevenueBean getARiskRevenue() {
        return aRiskRevenue;
    }

    public void setARiskRevenue(ARiskRevenueBean aRiskRevenue) {
        this.aRiskRevenue = aRiskRevenue;
    }

    public OtherRevenueBean getOtherRevenue() {
        return otherRevenue;
    }

    public void setOtherRevenue(OtherRevenueBean otherRevenue) {
        this.otherRevenue = otherRevenue;
    }

    public CompensationBean getCompensation() {
        return compensation;
    }

    public void setCompensation(CompensationBean compensation) {
        this.compensation = compensation;
    }

    public MyFansBean getMyFans() {
        return myFans;
    }

    public void setMyFans(MyFansBean myFans) {
        this.myFans = myFans;
    }

    public InvitFriendBean getInvitFriend() {
        return invitFriend;
    }

    public void setInvitFriend(InvitFriendBean invitFriend) {
        this.invitFriend = invitFriend;
    }

    public List<SetManagInfoBean> getSetManagInfo() {
        return setManagInfo;
    }

    public void setSetManagInfo(List<SetManagInfoBean> setManagInfo) {
        this.setManagInfo = setManagInfo;
    }

    public static class CarInfoBean {
        /**
         * name : 车险订单
         * imgUrl : /agent-new/weixin/images/xb_v2_wo_dicon02.png
         * url : http://prepare.implus100.com/agent-new/order/order_list.jsp?userIds=ff80808161195bda01612aff27190655
         */

        private String name;
        private String imgUrl;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class AriskInfoBean {
        /**
         * name : 个险订单
         * imgUrl : /agent-new/weixin/images/xb_v2_wo_dicon01.png
         * url : http://prepare.implus100.com/agent-new/weixin/build.jsp
         */

        private String name;
        private String imgUrl;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class OtherInfoBean {
        /**
         * name : 其他订单
         * imgUrl : /agent-new/weixin/images/xb_v2_wo_dicon03.png
         * url : http://prepare.implus100.com/agent-new/extended_warranty/order_list.jsp?userIds=ff80808161195bda01612aff27190655
         */

        private String name;
        private String imgUrl;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class AccIncomeBean {
        /**
         * name : 佣金累计
         * imgUrl : /agent-new/weixin/images/xb_v2_wo_licon01.png
         * accIncomeNum : 0.00
         * url :
         */

        private String name;
        private String imgUrl;
        private String accIncomeNum;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getAccIncomeNum() {
            return accIncomeNum;
        }

        public void setAccIncomeNum(String accIncomeNum) {
            this.accIncomeNum = accIncomeNum;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class BalanceBean {
        /**
         * name : 佣金余额
         * balanceNum : 0
         * imgUrl : /agent-new/weixin/images/xb_v2_wo_licon02.png
         * url :
         */

        private String name;
        private String balanceNum;
        private String imgUrl;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBalanceNum() {
            return balanceNum;
        }

        public void setBalanceNum(String balanceNum) {
            this.balanceNum = balanceNum;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class CarRevenueBean {
        /**
         * name : 车险收入
         * balanceNum : 0.00
         * imgUrl :
         * url :
         */

        private String name;
        private String balanceNum;
        private String imgUrl;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBalanceNum() {
            return balanceNum;
        }

        public void setBalanceNum(String balanceNum) {
            this.balanceNum = balanceNum;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ARiskRevenueBean {
        /**
         * name : 个险收入
         * balanceNum : 0.00
         * imgUrl :
         * url :
         */

        private String name;
        private String balanceNum;
        private String imgUrl;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBalanceNum() {
            return balanceNum;
        }

        public void setBalanceNum(String balanceNum) {
            this.balanceNum = balanceNum;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class OtherRevenueBean {
        /**
         * name : 其他收入
         * balanceNum : 0.00
         * imgUrl :
         * url :
         */

        private String name;
        private String balanceNum;
        private String imgUrl;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBalanceNum() {
            return balanceNum;
        }

        public void setBalanceNum(String balanceNum) {
            this.balanceNum = balanceNum;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class CompensationBean {
        /**
         * name : 补偿申请
         * imgUrl :
         * url : http://prepare.implus100.com//agent-new/weixin/compensationList.jsp?userIds=ff80808161195bda01612aff27190655
         */

        private String name;
        private String imgUrl;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class MyFansBean {
        /**
         * name : 我的团队
         * desc : 一起玩的伙伴
         * imgUrl : /agent-new/weixin/images/xb_v2_icon06.png
         * url : http://prepare.implus100.com//agent/member_center/my_branch.jsp?userIds=ff80808161195bda01612aff27190655
         */

        private String name;
        private String desc;
        private String imgUrl;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class InvitFriendBean {
        /**
         * name : 邀请好友
         * desc : 好事当然要分享
         * imgUrl : /agent-new/weixin/images/xb_v2_wo_gongicon04.png
         * url : http://prepare.implus100.com/agent/user/qrcode.jsp?mobile=14000000502&appShorName=rbb&userIds=ff80808161195bda01612aff27190655
         */

        private String name;
        private String desc;
        private String imgUrl;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class SetManagInfoBean {
        /**
         * name : 常见问题
         * imgUrl : /agent-new/weixin/images/xb_v2_wo_qticon02.png
         * url : http://prepare.implus100.com/agent-new/handle_new/common_problem.jsp?userIds=ff80808161195bda01612aff27190655
         */

        private String name;
        private String imgUrl;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    public static class BarterBean {
        private String name;
        private String imgUrl;
        private String url;
        private String desc;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
