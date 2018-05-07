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
    /**
     * 订单
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("queryAllordersListapp.shtml")
    Observable<BaseResponse<OrderEntity>> requestOrders(@FieldMap Map<String, Object> params);

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("applogin.shtml")
    Observable<BaseResponse<UserEntity>> login(@Field("userName") String userName, @Field("password") String password);

    /**
     * 首页数据
     *
     * @param userId
     * @param randomNum
     * @return
     */
    @FormUrlEncoded
    @POST("indexapp.shtml")
    Observable<BaseResponse<HomeEntity>> requestHomes(@Field("userId") long userId, @Field("randomNum") String randomNum);

    /**
     * 订单详情
     *
     * @param userId
     * @param randomNum
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("orderDetailapp.shtml")
    Observable<BaseResponse<OrderDetailEntity>> requestOrderDetail(
            @Field("userId") long userId, @Field("randomNum") String randomNum, @Field("orderId") int orderId);

    /**
     * 账单
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("accountListApp.shtml")
    Observable<BaseResponse<AccountEntity>> requestAccounts(@FieldMap Map<String, Object> params);

    /**
     * 账单详情
     *
     * @param userId
     * @param randomNum
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("accountDetailapp.shtml")
    Observable<BaseResponse<AccountDetailEntity>> requestAccountDetail(
            @Field("userId") long userId, @Field("randomNum") String randomNum,
            @Field("orderId") String orderId);

    /**
     * 支付二维码
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("saomapayApp.shtml")
    Observable<BaseResponse<QrcodeEntity>> requestQrcode(
            @FieldMap Map<String, Object> params);

    /**
     * 无订单二维码
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("noOrdersaomapayApp.shtml")
    Observable<BaseResponse<QrcodeEntity>> requestQrcodeNoOrder(
            @FieldMap Map<String, Object> params);

    /**
     * 反扫
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("fansaopayApp.shtml")
    Observable<BaseResponse<PayResultEntity>> requestFansao(
            @FieldMap Map<String, Object> params);

    /**
     * 无订单反扫
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("noOrderfansaopayApp.shtml")
    Observable<BaseResponse<PayResultEntity>> requestFansaoNoOrder(
            @FieldMap Map<String, Object> params);

    /**
     * 轮询支付结果
     *
     * @param userId
     * @param randomNum
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("queryOrderpayOrnot.shtml")
    Observable<BaseResponse<PayResultEntity>> queryOrderPay(
            @Field("userId") long userId, @Field("randomNum") String randomNum, @Field("orderId") String orderId);

    /**
     * 自提
     *
     * @param userId
     * @param randomNum
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("cashPay.shtml")
    Observable<BaseResponse<PayResultEntity>> selfDelivery(
            @Field("userId") long userId, @Field("randomNum") String randomNum, @Field("orderId") String orderId
    );

    /**
     * 收银员
     *
     * @param userId
     * @param randomNum
     * @return
     */
    @FormUrlEncoded
    @POST("queryUseNameApp.shtml")
    Observable<BaseResponse<CashierEntity>> requestCashier(@Field("userId") long userId, @Field("randomNum") String randomNum);

    /**
     * 版本更新
     *
     * @return
     */
    //    @FormUrlEncoded
    @POST("appversion.shtml")
    Observable<BaseResponse<ApkInfoEntity>> checkUpgrade();

    /**
     * 触发此接口让后台开始轮询支付结果
     */
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
