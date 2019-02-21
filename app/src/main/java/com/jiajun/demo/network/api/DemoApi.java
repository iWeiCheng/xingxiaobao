package com.jiajun.demo.network.api;


import com.jiajun.demo.model.entities.GankBeautyResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/*************************************************************
 * Created by OCN.Yang           * * * *   * * * *   *     * *
 * Time:2016/10/10 16:51.        *     *   *         * *   * *
 * Email address:yangocn@163.com *     *   *         *   * * *
 * Web site:www.ocnyang.com      * * * *   * * * *   *     * *
 *************************************************************/

/**

 *
 * retrofit2 @Path 多用于 路径式,如第一个接口为: data/福利/3/2  (number = 3 , page = 2)
 * retrofit2 @Query key-value式,如 key = aaa value = "aaa" 那么 GET下,请求为/login/loginSuccess/?aaa="aaa"
 * retrofit2 @Query 动态参数 如果某些参数不是必须的,那么将这些参数类型设置为引用类型,并传入null,在构建时不会发送这些参数
 */
//public interface Api {
//
//    @GET("data/福利/{number}/{page}")
//    Observable<GankBeautyResult> getBeauties(@Path("number") int number, @Path("page") int page);
//
//    @GET("login/loginSuccess")
//    Observable<LoginVo> login(@Query("account") String account
//            , @Query("pwd") String pwd, @Query("vcode") String vcode
//            , @Query("imageId") String imageId, @Query("deviceToken") String deviceToken);
//
//    @GET("appCms/startPage")
//    Observable<StartPage> getStartPage();
//}
public interface DemoApi {
    @GET("data/福利/{number}/{page}")
    Observable<GankBeautyResult> getBeauties(@Path("number") int number, @Path("page") int page);
}
