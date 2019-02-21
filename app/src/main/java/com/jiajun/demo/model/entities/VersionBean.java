package com.jiajun.demo.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * VersionBean
 * Created by danjj on 2016/12/9.
 */
public class VersionBean implements Parcelable {
    private int resultCode;
    private String resultDesc;
    private DataBean data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
     private String versionNumber;
     private String versionDesc;
     private String versionUrl;

        public String getVersionNumber() {
            return versionNumber;
        }

        public void setVersionNumber(String versionNumber) {
            this.versionNumber = versionNumber;
        }

        public String getVersionDesc() {
            return versionDesc;
        }

        public void setVersionDesc(String versionDesc) {
            this.versionDesc = versionDesc;
        }

        public String getVersionUrl() {
            return versionUrl;
        }

        public void setVersionUrl(String versionUrl) {
            this.versionUrl = versionUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.versionNumber);
            dest.writeString(this.versionDesc);
            dest.writeString(this.versionUrl);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.versionNumber = in.readString();
            this.versionDesc = in.readString();
            this.versionUrl = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.resultCode);
        dest.writeString(this.resultDesc);
        dest.writeParcelable(this.data, flags);
    }

    public VersionBean() {
    }

    protected VersionBean(Parcel in) {
        this.resultCode = in.readInt();
        this.resultDesc = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<VersionBean> CREATOR = new Parcelable.Creator<VersionBean>() {
        @Override
        public VersionBean createFromParcel(Parcel source) {
            return new VersionBean(source);
        }

        @Override
        public VersionBean[] newArray(int size) {
            return new VersionBean[size];
        }
    };
}
