package com.yaoxiaoer.mendian.http.api;


import com.yaoxiaoer.mendian.mvp.entity.AccountDetailEntity;
import com.yaoxiaoer.mendian.mvp.entity.AccountEntity;
import com.yaoxiaoer.mendian.mvp.entity.ApkInfoEntity;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.mvp.entity.CashierEntity;
import com.yaoxiaoer.mendian.mvp.entity.HomeEntity;
import com.yaoxiaoer.mendian.mvp.entity.NullEntity;
import com.yaoxiaoer.mendian.mvp.entity.OrderDetailEntity;
import com.yaoxiaoer.mendian.mvp.entity.OrderEntity;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;
import com.yaoxiaoer.mendian.mvp.entity.QrcodeEntity;
import com.yaoxiaoer.mendian.mvp.entity.UserEntity;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Chenwy on 2018/2/1.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("queryAllordersListapp.shtml")
    Observable<BaseResponse<OrderEntity>> requestOrders(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("applogin.shtml")
    Observable<BaseResponse<UserEntity>> login(@Field("userName") String userName, @Field("password") String password);

    @FormUrlEncoded
    @POST("indexapp.shtml")
    Observable<BaseResponse<HomeEntity>> requestHomes(@Field("userId") long userId, @Field("randomNum") String randomNum);

    @FormUrlEncoded
    @POST("orderDetailapp.shtml")
    Observable<BaseResponse<OrderDetailEntity>> requestOrderDetail(
            @Field("userId") long userId, @Field("randomNum") String randomNum, @Field("orderId") int orderId);

    @FormUrlEncoded
    @POST("accountListApp.shtml")
    Observable<BaseResponse<AccountEntity>> requestAccounts(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("accountDetailapp.shtml")
    Observable<BaseResponse<AccountDetailEntity>> requestAccountDetail(
            @Field("userId") long userId, @Field("randomNum") String randomNum,
            @Field("orderId") String orderId);

    @FormUrlEncoded
    @POST("saomapayApp.shtml")
    Observable<BaseResponse<QrcodeEntity>> requestQrcode(
            @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("noOrdersaomapayApp.shtml")
    Observable<BaseResponse<QrcodeEntity>> requestQrcodeNoOrder(
            @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("fansaopayApp.shtml")
    Observable<BaseResponse<PayResultEntity>> requestFansao(
            @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("noOrderfansaopayApp.shtml")
    Observable<BaseResponse<PayResultEntity>> requestFansaoNoOrder(
            @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("queryOrderpayOrnot.shtml")
    Observable<BaseResponse<PayResultEntity>> queryOrderPay(
            @Field("userId") long userId, @Field("randomNum") String randomNum, @Field("orderId") String orderId);

    @FormUrlEncoded
    @POST("cashPay.shtml")
    Observable<BaseResponse<PayResultEntity>> selfDelivery(
            @Field("userId") long userId, @Field("randomNum") String randomNum, @Field("orderId") String orderId
    );

    @FormUrlEncoded
    @POST("queryUseNameApp.shtml")
    Observable<BaseResponse<CashierEntity>> requestCashier(@Field("userId") long userId, @Field("randomNum") String randomNum);

    //    @FormUrlEncoded
    @POST("appversion.shtml")
    Observable<BaseResponse<ApkInfoEntity>> checkUpgrade();

    @FormUrlEncoded
    @POST("timequeryOrderpayOrnot.shtml")
    Observable<BaseResponse<NullEntity>> timequeryOrderpayOrnot(@Field("orderId") String orderId, @Field("userId") long userId);

    /**
     * 下载文件
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);//直接使用网址下载
}
