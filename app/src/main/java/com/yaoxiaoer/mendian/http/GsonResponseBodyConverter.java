package com.yaoxiaoer.mendian.http;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.yaoxiaoer.mendian.http.exception.NeedPayPwdException;
import com.yaoxiaoer.mendian.http.exception.ResultException;
import com.yaoxiaoer.mendian.mvp.entity.OrderIdEntity;
import com.yaoxiaoer.mendian.mvp.entity.ResultResponse;
import com.yaoxiaoer.mendian.C;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Chenwy on 2017/10/20.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private Gson gson;
    private Type type;

    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        ResultResponse resultResponse = gson.fromJson(response, ResultResponse.class);
        //成功
        if (resultResponse.code == C.CODE_SUCCESS) {
            return gson.fromJson(response, type);
        }
        //反扫需要支付密码
        if (resultResponse.code == C.CODE_NEED_PAY_PASSWORD) {
            OrderIdEntity orderIdEntity = gson.fromJson(response, OrderIdEntity.class);
            throw new NeedPayPwdException(orderIdEntity.code, orderIdEntity.message, orderIdEntity.data.orderId);
        } else {
            ResultResponse.Data data = resultResponse.data;
            if (null != data && !TextUtils.isEmpty(data.trade_state)) {
                String tradeState = data.trade_state;
                throw new ResultException(resultResponse.code, resultResponse.message, tradeState);
            } else {
                throw new ResultException(resultResponse.code, resultResponse.message);
            }

        }
    }
}
