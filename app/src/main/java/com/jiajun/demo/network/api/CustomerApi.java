package com.jiajun.demo.network.api;


import com.jiajun.demo.model.BaseBean;
import com.jiajun.demo.moudle.customer.entities.CustomerBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/24 14:53.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public interface CustomerApi {
    @POST()
    Observable<BaseBean<List<CustomerBean>>> getCustomer(@Url String url,@QueryMap Map<String, String> map);
}
