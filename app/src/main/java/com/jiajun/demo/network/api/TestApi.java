package com.jiajun.demo.network.api;

import com.jiajun.demo.model.BaseBean;
import com.jiajun.demo.model.entities.VersionBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * http://www.implus100.com/agent/interface/interface_ax.jsp?platform
 * =2&method=getVersion&signature=B2617158AB4221EBA92C4BA589BA0998&random=891642730&type=1&imsi=460075891207698
 * Created by dan on 2017/8/16.
 */

public interface TestApi {
    @POST("interface_ax.jsp")
    Observable<BaseBean<VersionBean.DataBean>> getVersion(@Query("method") String method,
                                                          @Query("platform") String platform,
                                                          @Query("signature") String signature,
                                                          @Query("random") String random,
                                                          @Query("imsi") String imsi,
                                                          @Query("type") String type);
}
