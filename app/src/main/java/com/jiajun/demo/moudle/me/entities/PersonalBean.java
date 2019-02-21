package com.jiajun.demo.moudle.me.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by danjj on 2016/12/8.
 */
public class PersonalBean implements Parcelable {


    /**
     * userId : 1
     * name : 刘芳
     * mobile : 11111111111
     * card_no : 452427199009081905
     * e-mail : 4524271@qq.com
     * organizationName : 益盛鑫公司
     * companyName : 益盛鑫公司第六分公司
     * headImg : http://www.implus100.com/agent/attached/logo/Wx_0000.jpg
     * qcode : ww
     */

    private String userId;
    private String name;
    private String mobile;
    private String card_no;
    private String email;
    private String organizationName;
    private String companyName;
    private String headImg;
    private String qcode;
    private String down_url;

    public String getDown_url() {
        return down_url;
    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getQcode() {
        return qcode;
    }

    public void setQcode(String qcode) {
        this.qcode = qcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.card_no);
        dest.writeString(this.email);
        dest.writeString(this.organizationName);
        dest.writeString(this.companyName);
        dest.writeString(this.headImg);
        dest.writeString(this.qcode);
        dest.writeString(this.down_url);
    }

    public PersonalBean() {
    }

    protected PersonalBean(Parcel in) {
        this.userId = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.card_no = in.readString();
        this.email = in.readString();
        this.organizationName = in.readString();
        this.companyName = in.readString();
        this.headImg = in.readString();
        this.qcode = in.readString();
        this.down_url = in.readString();
    }

    public static final Parcelable.Creator<PersonalBean> CREATOR = new Parcelable.Creator<PersonalBean>() {
        @Override
        public PersonalBean createFromParcel(Parcel source) {
            return new PersonalBean(source);
        }

        @Override
        public PersonalBean[] newArray(int size) {
            return new PersonalBean[size];
        }
    };
}
