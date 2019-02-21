package com.jiajun.demo.moudle.homepage.entities;

import java.util.List;

/**
 * Created by dan on 2017/11/20/020.
 */

public class HomePageBean {

    /**
     * imgList : [{"url":"","imgUrl":"http://prepare.implus100.com/upload/banner_new1.jpg","desc":""},{"url":"","imgUrl":"http://prepare.implus100.com/upload/banner_new2.jpg","desc":""}]
     * province : 粤
     * share_title : 车保宝
     * share_url : http://www.implus100.com/agent/interface/app_share.jsp?appShorName=rbb
     * share_image_url : http://www.implus100.com/agent/interface/images/logo_rbb.png
     * share_content : 车保宝，买保险算佣金简单快捷。更多服务等着您。
     * recordName : 中国保险行业协会互联网保险信息披露备案
     * ICPId : 粤ICP备17061786号-1
     * business_company :
     * technical : 技术支持由新疆益盛鑫网络科技有限公司提供
     * insure_car_url : http://prepare.implus100.com/agent-new/handle_new/insure_page.jsp?userIds=ff80808161195bda01612aff27190655
     * host_all_url : http://prepare.implus100.com/agent-new/handle_new/insure_page.jsp?userIds=ff80808161195bda01612aff27190655
     * policyMenu : [{"name":"智能报价","unread":"0","url":"http://prepare.implus100.com/agent-new/handle_new/insure_page.jsp?userIds=ff80808161195bda01612aff27190655","imgUrl":"/agent-new/weixin/images/xb_v2_icon01.png"},{"name":"二手车业务","unread":"0","url":"http://prepare.implus100.com/agent-new/second_hand_car/insure_page.jsp?userIds=ff80808161195bda01612aff27190655","imgUrl":"/agent-new/weixin/images/xb_v2_icon02.png"},{"name":"车险订单","unread":"1","url":"http://prepare.implus100.com/agent-new/order/order_list.jsp?userIds=ff80808161195bda01612aff27190655","imgUrl":"/agent-new/weixin/images/xb_v2_icon03.png"},{"name":" 续保提醒","unread":"0","url":"http://prepare.implus100.com//agent-new/order/toBeRenewedList.jsp?userIds=ff80808161195bda01612aff27190655","imgUrl":"/agent-new/weixin/images/xb_v2_icon09.png"},{"name":"工具箱","unread":"0","url":"http://prepare.implus100.com/agent-new/weixin/gongju.jsp","imgUrl":"/agent-new/weixin/images/xb_v2_icon05.png"},{"name":"团队管理","unread":"0","url":"http://prepare.implus100.com//agent/member_center/my_branch.jsp?userIds=ff80808161195bda01612aff27190655","imgUrl":"/agent-new/weixin/images/xb_v2_icon06.png"},{"name":"车险业绩","unread":"0","url":"http://prepare.implus100.com//agent-new/bill/bill_single_month.jsp?userIds=ff80808161195bda01612aff27190655","imgUrl":"/agent-new/weixin/images/xb_v2_icon07.png"},{"name":"金币钱包","unread":"0","url":"http://prepare.implus100.com/agent-new/gold/wallet.jsp?userIds=ff80808161195bda01612aff27190655","imgUrl":"/agent-new/weixin/images/xb_v2_icon08.png"}]
     * noticeNews : {"name":"消息管理","url":"http://prepare.implus100.com/agent-new/weixin/build.jsp","imgUrl":"/agent-new/weixin/images/xb_v2_icon012.png","noticeList":[{"title":"新版本升级啦","time":"2018-05-28 10：12:12","state":"0"},{"title":"你的出单红包到账了","time":"2018-05-28 09：12:12","state":"1"}]}
     * hostMenu : [{"title":"费改后车主最高可享3.8折","desc":"万元1小时赔付 理赔网点最多","url":"http://prepare.implus100.com/agent-new/handle_new/insure_page.jsp?product_code=11&userIds=ff80808161195bda01612aff27190655","imgUrl":"/agent-new/weixin/images/xb_v2_hot01.png","logo":"/agent-new/weixin/images/picc.png"}]
     * newMenu : [{"title":"新品上市","desc":"本月新品","url":"http://prepare.implus100.com/agent-new/weixin/build.jsp","imgUrl":"/agent-new/weixin/images/xb_tj01.jpg"},{"title":"意外险","desc":"排行榜","url":"http://prepare.implus100.com/agent-new/weixin/build.jsp","imgUrl":"/agent-new/weixin/images/xb_tj02.jpg"},{"title":"少儿险","desc":"排行榜","url":"http://prepare.implus100.com/agent-new/weixin/build.jsp","imgUrl":"/agent-new/weixin/images/xb_tj03.jpg"},{"title":"健康险","desc":"排行榜","url":"http://prepare.implus100.com/agent-new/weixin/build.jsp","imgUrl":"/agent-new/weixin/images/xb_tj04.jpg"},{"title":"旅游险","desc":"排行榜","url":"http://prepare.implus100.com/agent-new/weixin/build.jsp","imgUrl":"/agent-new/weixin/images/xb_tj04.jpg"}]
     */

    private String province;
    private String share_title;
    private String share_url;
    private String share_image_url;
    private String share_content;
    private String recordName;
    private String ICPId;
    private String business_company;
    private String technical;
    private String insure_car_url;
    private String host_all_url;
    private NoticeNewsBean noticeNews;
    private List<ImgListBean> imgList;
    private List<PolicyMenuBean> policyMenu;
    private List<HostMenuBean> hostMenu;
    private List<NewMenuBean> newMenu;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getShare_image_url() {
        return share_image_url;
    }

    public void setShare_image_url(String share_image_url) {
        this.share_image_url = share_image_url;
    }

    public String getShare_content() {
        return share_content;
    }

    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getICPId() {
        return ICPId;
    }

    public void setICPId(String ICPId) {
        this.ICPId = ICPId;
    }

    public String getBusiness_company() {
        return business_company;
    }

    public void setBusiness_company(String business_company) {
        this.business_company = business_company;
    }

    public String getTechnical() {
        return technical;
    }

    public void setTechnical(String technical) {
        this.technical = technical;
    }

    public String getInsure_car_url() {
        return insure_car_url;
    }

    public void setInsure_car_url(String insure_car_url) {
        this.insure_car_url = insure_car_url;
    }

    public String getHost_all_url() {
        return host_all_url;
    }

    public void setHost_all_url(String host_all_url) {
        this.host_all_url = host_all_url;
    }

    public NoticeNewsBean getNoticeNews() {
        return noticeNews;
    }

    public void setNoticeNews(NoticeNewsBean noticeNews) {
        this.noticeNews = noticeNews;
    }

    public List<ImgListBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgListBean> imgList) {
        this.imgList = imgList;
    }

    public List<PolicyMenuBean> getPolicyMenu() {
        return policyMenu;
    }

    public void setPolicyMenu(List<PolicyMenuBean> policyMenu) {
        this.policyMenu = policyMenu;
    }

    public List<HostMenuBean> getHostMenu() {
        return hostMenu;
    }

    public void setHostMenu(List<HostMenuBean> hostMenu) {
        this.hostMenu = hostMenu;
    }

    public List<NewMenuBean> getNewMenu() {
        return newMenu;
    }

    public void setNewMenu(List<NewMenuBean> newMenu) {
        this.newMenu = newMenu;
    }

    public static class NoticeNewsBean {
        /**
         * name : 消息管理
         * url : http://prepare.implus100.com/agent-new/weixin/build.jsp
         * imgUrl : /agent-new/weixin/images/xb_v2_icon012.png
         * noticeList : [{"title":"新版本升级啦","time":"2018-05-28 10：12:12","state":"0"},{"title":"你的出单红包到账了","time":"2018-05-28 09：12:12","state":"1"}]
         */

        private String name;
        private String url;
        private String imgUrl;
        private String state;
        private List<NoticeListBean> noticeList;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public List<NoticeListBean> getNoticeList() {
            return noticeList;
        }

        public void setNoticeList(List<NoticeListBean> noticeList) {
            this.noticeList = noticeList;
        }

        public static class NoticeListBean {
            /**
             * title : 新版本升级啦
             * time : 2018-05-28 10：12:12
             * state : 0
             */

            private String title;
            private String time;
            private String state;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
    }

    public static class ImgListBean {
        /**
         * url :
         * imgUrl : http://prepare.implus100.com/upload/banner_new1.jpg
         * desc :
         */

        private String url;
        private String imgUrl;
        private String desc;

        public String getUrl() {
            return url;
        }

        public ImgListBean() {
        }

        public ImgListBean(String url, String imgUrl, String desc) {
            this.url = url;
            this.imgUrl = imgUrl;
            this.desc = desc;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public static class PolicyMenuBean {
        /**
         * name : 智能报价
         * unread : 0
         * url : http://prepare.implus100.com/agent-new/handle_new/insure_page.jsp?userIds=ff80808161195bda01612aff27190655
         * imgUrl : /agent-new/weixin/images/xb_v2_icon01.png
         */

        private String name;
        private String unread;
        private String url;
        private String imgUrl;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnread() {
            return unread;
        }

        public void setUnread(String unread) {
            this.unread = unread;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

    public static class HostMenuBean {
        /**
         * title : 费改后车主最高可享3.8折
         * desc : 万元1小时赔付 理赔网点最多
         * url : http://prepare.implus100.com/agent-new/handle_new/insure_page.jsp?product_code=11&userIds=ff80808161195bda01612aff27190655
         * imgUrl : /agent-new/weixin/images/xb_v2_hot01.png
         * logo : /agent-new/weixin/images/picc.png
         */

        private String title;
        private String desc;
        private String url;
        private String imgUrl;
        private String logo;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }

    public static class NewMenuBean {
        /**
         * title : 新品上市
         * desc : 本月新品
         * url : http://prepare.implus100.com/agent-new/weixin/build.jsp
         * imgUrl : /agent-new/weixin/images/xb_tj01.jpg
         */

        private String title;
        private String desc;
        private String url;
        private String imgUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
