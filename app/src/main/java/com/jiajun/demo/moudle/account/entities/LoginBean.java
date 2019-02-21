package com.jiajun.demo.moudle.account.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 登录返回结果
 * Created by cai.jia on 2016/7/21 0021.
 */

public class LoginBean implements Parcelable {


    /**
     * userId : ff80808161195bda01612aff27190655
     * review_stae : 1
     * mobile : 14000000502
     * userName : 测试员9
     * companyId : 1016
     * companyName : 益盛鑫第一分公司-李华
     * distanceMetre : 1000
     */

    private String userId;
    private String review_stae;
    private String mobile;
    private String userName;
    private String companyId;
    private String companyName;
    private String distanceMetre;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReview_stae() {
        return review_stae;
    }

    public void setReview_stae(String review_stae) {
        this.review_stae = review_stae;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDistanceMetre() {
        return distanceMetre;
    }

    public void setDistanceMetre(String distanceMetre) {
        this.distanceMetre = distanceMetre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.review_stae);
        dest.writeString(this.mobile);
        dest.writeString(this.userName);
        dest.writeString(this.companyId);
        dest.writeString(this.companyName);
        dest.writeString(this.distanceMetre);
    }

    public LoginBean() {
    }

    protected LoginBean(Parcel in) {
        this.userId = in.readString();
        this.review_stae = in.readString();
        this.mobile = in.readString();
        this.userName = in.readString();
        this.companyId = in.readString();
        this.companyName = in.readString();
        this.distanceMetre = in.readString();
    }

    public static final Parcelable.Creator<LoginBean> CREATOR = new Parcelable.Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel source) {
            return new LoginBean(source);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };
}
