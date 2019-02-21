package com.jiajun.demo.moudle.vip.entities;

import java.util.List;

/**
 * Created by dan on 2017/11/22/022.
 */

public class PrivilegeBean {

    /**
     * grade_id : 8
     * grade_name : 一级特权
     * name : 刘备(APP)
     * surplus_point : 3840
     * surplus_point_url : http://192.168.31.239:8080/agent-new/weixin/integralList.jsp?userIds=297ebe0e55dd69430155de1e99db006e
     * signIn_url : http://192.168.31.239:8080/agent-new/weixin/signIn.jsp?userIds=297ebe0e55dd69430155de1e99db006e
     * gradeList : [{"grade_id":9,"grade_name":"普通会员","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/openMember.jsp?userIds=297ebe0e55dd69430155de1e99db006e&gradeId=9","privilegeList":[{"ico":"/file/manager/20171115180042470.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=9&privilegeId=56&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":56,"pri_name":"车险询价保障","position":"1","buy_status":"0"},{"ico":"/file/manager/20171115183323690.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=9&privilegeId=59&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":59,"pri_name":"高温保障","position":"","buy_status":"0"},{"ico":"/file/manager/20171117113908963.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=9&privilegeId=66&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":66,"pri_name":"地铁乐","position":"2","buy_status":"0"}]},{"grade_id":8,"grade_name":"一级特权","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/openMember.jsp?userIds=297ebe0e55dd69430155de1e99db006e&gradeId=8","privilegeList":[{"ico":"/file/manager/20171115180042470.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=8&privilegeId=56&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":56,"pri_name":"车险询价保障","position":"1","buy_status":"1"},{"ico":"/file/manager/20171115181905675.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=8&privilegeId=58&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":58,"pri_name":"现金购物券","position":"3","buy_status":"1"}]},{"grade_id":10,"grade_name":"二级特权","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/openMember.jsp?userIds=297ebe0e55dd69430155de1e99db006e&gradeId=10","privilegeList":[{"ico":"/file/manager/20171115181905675.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=10&privilegeId=58&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":58,"pri_name":"现金购物券","position":"3","buy_status":"0"},{"ico":"/file/manager/20171115180042470.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=10&privilegeId=56&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":56,"pri_name":"车险询价保障","position":"1","buy_status":"0"},{"ico":"/file/manager/20171117112450713.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=10&privilegeId=65&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":65,"pri_name":"国内旅游（5天4夜）","position":"3","buy_status":"0"},{"ico":"/file/manager/20171117113908963.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=10&privilegeId=66&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":66,"pri_name":"地铁乐","position":"2","buy_status":"0"}]},{"grade_id":11,"grade_name":"三级特权","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/openMember.jsp?userIds=297ebe0e55dd69430155de1e99db006e&gradeId=11","privilegeList":[{"ico":"/file/manager/20171115180042470.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=11&privilegeId=56&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":56,"pri_name":"车险询价保障","position":"1","buy_status":"0"},{"ico":"/file/manager/20171115181905675.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=11&privilegeId=58&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":58,"pri_name":"现金购物券","position":"3","buy_status":"0"},{"ico":"/file/manager/20171117112450713.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=11&privilegeId=65&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":65,"pri_name":"国内旅游（5天4夜）","position":"3","buy_status":"0"}]}]
     */

    private int grade_id;
    private String grade_name;
    private String grade_stop_date;
    private String head_Img;
    private String name;
    private String explain_url;
    private int surplus_point;
    private String surplus_point_url;
    private String signIn_url;
    private List<GradeListBean> gradeList;

    public String getGrade_stop_date() {
        return grade_stop_date;
    }

    public void setGrade_stop_date(String grade_stop_date) {
        this.grade_stop_date = grade_stop_date;
    }

    public String getExplain_url() {
        return explain_url;
    }

    public void setExplain_url(String explain_url) {
        this.explain_url = explain_url;
    }

    public String getHead_Img() {
        return head_Img;
    }

    public void setHead_Img(String head_Img) {
        this.head_Img = head_Img;
    }

    public int getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(int grade_id) {
        this.grade_id = grade_id;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSurplus_point() {
        return surplus_point;
    }

    public void setSurplus_point(int surplus_point) {
        this.surplus_point = surplus_point;
    }

    public String getSurplus_point_url() {
        return surplus_point_url;
    }

    public void setSurplus_point_url(String surplus_point_url) {
        this.surplus_point_url = surplus_point_url;
    }

    public String getSignIn_url() {
        return signIn_url;
    }

    public void setSignIn_url(String signIn_url) {
        this.signIn_url = signIn_url;
    }

    public List<GradeListBean> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<GradeListBean> gradeList) {
        this.gradeList = gradeList;
    }

    public static class GradeListBean {
        /**
         * grade_id : 9
         * grade_name : 普通会员
         * open_grade_url : http://192.168.31.239:8080/agent-new/weixin/openMember.jsp?userIds=297ebe0e55dd69430155de1e99db006e&gradeId=9
         * privilegeList : [{"ico":"/file/manager/20171115180042470.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=9&privilegeId=56&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":56,"pri_name":"车险询价保障","position":"1","buy_status":"0"},{"ico":"/file/manager/20171115183323690.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=9&privilegeId=59&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":59,"pri_name":"高温保障","position":"","buy_status":"0"},{"ico":"/file/manager/20171117113908963.png","open_grade_url":"http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=9&privilegeId=66&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e","privilege_id":66,"pri_name":"地铁乐","position":"2","buy_status":"0"}]
         */

        private int grade_id;
        private String grade_name;
        private String open_grade_url;
        private List<PrivilegeListBean> privilegeList;

        public int getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(int grade_id) {
            this.grade_id = grade_id;
        }

        public String getGrade_name() {
            return grade_name;
        }

        public void setGrade_name(String grade_name) {
            this.grade_name = grade_name;
        }

        public String getOpen_grade_url() {
            return open_grade_url;
        }

        public void setOpen_grade_url(String open_grade_url) {
            this.open_grade_url = open_grade_url;
        }

        public List<PrivilegeListBean> getPrivilegeList() {
            return privilegeList;
        }

        public void setPrivilegeList(List<PrivilegeListBean> privilegeList) {
            this.privilegeList = privilegeList;
        }

        public static class PrivilegeListBean {
            /**
             * ico : /file/manager/20171115180042470.png
             * open_grade_url : http://192.168.31.239:8080/agent-new/weixin/privilegeDetail.jsp?gradeId=9&privilegeId=56&buy_status=null&userIds=297ebe0e55dd69430155de1e99db006e
             * privilege_id : 56
             * pri_name : 车险询价保障
             * position : 1
             * buy_status : 0
             */

            private String ico;
            private String open_grade_url;
            private int privilege_id;
            private String pri_name;
            private String position;
            private String buy_status;

            public String getIco() {
                return ico;
            }

            public void setIco(String ico) {
                this.ico = ico;
            }

            public String getOpen_grade_url() {
                return open_grade_url;
            }

            public void setOpen_grade_url(String open_grade_url) {
                this.open_grade_url = open_grade_url;
            }

            public int getPrivilege_id() {
                return privilege_id;
            }

            public void setPrivilege_id(int privilege_id) {
                this.privilege_id = privilege_id;
            }

            public String getPri_name() {
                return pri_name;
            }

            public void setPri_name(String pri_name) {
                this.pri_name = pri_name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getBuy_status() {
                return buy_status;
            }

            public void setBuy_status(String buy_status) {
                this.buy_status = buy_status;
            }
        }
    }
}
