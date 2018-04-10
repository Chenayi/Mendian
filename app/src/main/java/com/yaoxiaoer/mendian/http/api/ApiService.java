package com.yaoxiaoer.mendian.http.api;


import com.yaoxiaoer.mendian.mvp.entity.AccountDetailEntity;
import com.yaoxiaoer.mendian.mvp.entity.AccountEntity;
import com.yaoxiaoer.mendian.mvp.entity.ApkInfoEntity;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.mvp.entity.CashierEntity;
import com.yaoxiaoer.mendian.mvp.entity.HomeEntity;
import com.yaoxiaoer.mendian.mvp.entity.OrderDetailEntity;
import com.yaoxiaoer.mendian.mvp.entity.OrderEntity;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;
import com.yaoxiaoer.mendian.mvp.entity.QrcodeEntity;
import com.yaoxiaoer.mendian.mvp.entity.UserEntity;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Chenwy on 2018/2/1.
 */

public interface ApiService {
    @GET("queryAllordersListapp.shtml")
    Observable<BaseResponse<OrderEntity>> requestOrders(@QueryMap Map<String, Object> params);

    @GET("applogin.shtml")
    Observable<BaseResponse<UserEntity>> login(@Query("userName") String userName, @Query("password") String password);

    @GET("indexapp.shtml")
    Observable<BaseResponse<HomeEntity>> requestHomes(@Query("userId") long userId, @Query("randomNum") String randomNum);

    @GET("orderDetailapp.shtml")
    Observable<BaseResponse<OrderDetailEntity>> requestOrderDetail(
            @Query("userId") long userId, @Query("randomNum") String randomNum, @Query("orderId") int orderId);

    @GET("accountListApp.shtml")
    Observable<BaseResponse<AccountEntity>> requestAccounts(@QueryMap Map<String, Object> params);

    @GET("accountDetailapp.shtml")
    Observable<BaseResponse<AccountDetailEntity>> requestAccountDetail(
            @Query("userId") long userId, @Query("randomNum") String randomNum,
            @Query("orderId") String orderId);

    @GET("saomapayApp.shtml")
    Observable<BaseResponse<QrcodeEntity>> requestQrcode(
            @QueryMap Map<String, Object> params);

    @GET("noOrdersaomapayApp.shtml")
    Observable<BaseResponse<QrcodeEntity>> requestQrcodeNoOrder(
            @QueryMap Map<String, Object> params);

    @GET("fansaopayApp.shtml")
    Observable<BaseResponse<PayResultEntity>> requestFansao(
            @QueryMap Map<String, Object> params);

    @GET("noOrderfansaopayApp.shtml")
    Observable<BaseResponse<PayResultEntity>> requestFansaoNoOrder(
            @QueryMap Map<String, Object> params);

    @GET("queryOrderpayOrnot.shtml")
    Observable<BaseResponse<PayResultEntity>> queryOrderPay(
            @Query("userId") long userId, @Query("randomNum") String randomNum, @Query("orderId") String orderId);

    @GET("cashPay.shtml")
    Observable<BaseResponse<PayResultEntity>> selfDelivery(
            @Query("userId") long userId, @Query("randomNum") String randomNum, @Query("orderId") String orderId
    );

    @GET("queryUseNameApp.shtml")
    Observable<BaseResponse<CashierEntity>> requestCashier(@Query("userId") long userId, @Query("randomNum") String randomNum);

    @GET("appversion.shtml")
    Observable<BaseResponse<ApkInfoEntity>> checkUpgrade();

    /**
     * 下载文件
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);//直接使用网址下载
}
