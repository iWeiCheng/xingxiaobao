package com.jiajun.demo.moudle.customer.entities;

/**
 * Created by dan on 2017/11/24/024.
 */

public class CustomerBean {

    /**
     * id : 1889
     * name : 陈君君
     * car_name : 别克SGM7144TATB轿车
     * car_number : 粤AS9D17
     * url : http://192.168.31.239:8080/agent/cust/car_info.jsp?car_id=1889&userIds=297ebe0e55dd69430155de1e99db006e
     */

    private String id;
    private String name;
    private String car_name;
    private String car_number;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
