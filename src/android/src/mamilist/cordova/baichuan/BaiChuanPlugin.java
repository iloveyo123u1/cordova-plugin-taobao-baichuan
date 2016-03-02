package mamilist.cordova.baichuan;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.ResultCode;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.alibaba.sdk.android.trade.*;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TaokeParams;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.alibaba.sdk.android.trade.page.ItemDetailPage;
import com.alibaba.sdk.android.trade.page.MyCartsPage;
import com.alibaba.sdk.android.trade.page.MyOrdersPage;
import com.alibaba.sdk.android.webview.UiSettings;
import com.taobao.tae.sdk.webview.TaeWebViewUiSettings;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BaiChuanPlugin extends CordovaPlugin {

    public TelephonyManager tm;
    public Context ccontext;
    private Boolean sdk_inited = false;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        this.ccontext = this.cordova.getActivity().getApplicationContext();
//        AlibabaSDK.setEnvironment(Environment.TEST);
        if (!sdk_inited) {
            AlibabaSDK.asyncInit(ccontext, new InitResultCallback() {
                @Override
                public void onSuccess() {
                    sdk_inited = true;
                    System.err.println("AlibabaSDK inited ok");
//                    Toast.makeText(ccontext, "初始化成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int code, String message) {
                    sdk_inited = false;
                    System.err.println("AlibabaSDK onFailure " + message);
//                    Toast.makeText(ccontext, "初始化异常", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    public TradeProcessCallback callback(final CallbackContext cbc) {
        return new TradeProcessCallback() {

            @Override
            public void onPaySuccess(TradeResult tradeResult) {
                cbc.success("交易成功");
            }

            @Override
            public void onFailure(int code, String msg) {
                if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
                    cbc.error("确认交易订单失败");
                } else {
                    cbc.error("交易取消");
                }
            }
        };
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            JSONObject params = args.getJSONObject(0);
            if (action.equals("showItemDetailByItemId")) {
                System.err.println("action: showItemDetailByItemId");
                showItemDetailByItemId(params.getLong("itemid"), callbackContext);
                return true;
            }
            if (action.equals("showPageH5")) {
                System.err.println("action: showPageH5");
                showPageH5(params.getString("wd"), callbackContext);
                return true;
            }
            if (action.equals("showTaokeItemDetailByItemId")) {
                System.err.println("action: showTaokeItemDetailByItemId");
                showTaokeItemDetailByItemId(params.getString("pid"), params.getLong("itemid"), callbackContext);
                callbackContext.success("ok");
                return true;
            }
            if (action.equals("showCart")) {
                System.err.println("action: showCart");
                showCart(callbackContext);
                return true;
            }
            if (action.equals("addItem2Cart")) {
                System.err.println("action: addItem2Cart");
                addItem2Cart(params.getString("openid"), callbackContext);
                return true;
            }
            if (action.equals("addTaoKeItem2Cart")) {
                System.err.println("action: addTaoKeItem2Cart");
                addTaoKeItem2Cart(params.getString("pid"), params.getString("openid"), callbackContext);
                return true;
            }
            if (action.equals("showItemDetailPage")) {
                System.err.println("action: showItemDetailPage");
                showItemDetailPage(params.getString("pid"), params.getString("itemid"), params.getString("pagetype"), callbackContext);
                return true;
            }
            if (action.equals("showOrderWithSKU")) {
                System.err.println("action: showOrderWithSKU");
                showOrderWithSKU(params.getString("openid"), callbackContext);
                return true;
            }
            if (action.equals("showTaoKeOrderWithSKU")) {
                System.err.println("action: showTaoKeOrderWithSKU");
                showTaoKeOrderWithSKU(params.getString("pid"), params.getString("openid"), callbackContext);
                return true;
            }
            if (action.equals("showMyOrdersPage")) {
                System.err.println("action: showMyOrdersPage");
                showMyOrdersPage(params.getInt("status"), params.getBoolean("all"), callbackContext);
                return true;
            }
            if (action.equals("showMyCartsPage")) {
                System.err.println("action: showMyCartsPage");
                showMyCartsPage(callbackContext);
                return true;
            }
            callbackContext.error("Invalid Action");
            return false;
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        }
    }

    //http://baichuan.taobao.com/doc2/detail.htm?spm=0.0.0.0.CqDJX8&treeId=51&articleId=103900&docType=1
    public boolean showItemDetailPage(String pid, String itemid, String pageType, final CallbackContext cbc) {
        TradeService tradeService = AlibabaSDK.getService(TradeService.class);
        TaokeParams taokeParams = new TaokeParams();
        taokeParams.pid = pid;
        //若非淘客taokeParams设置为null即可
        Map<String, String> params = new HashMap<String, String>();
        //TradeConstants.ITEM_DETAIL_VIEW_TYPE：启动页面类型，分为BAICHUAN_H5_VIEW、TAOBAO_H5_VIEW、TAOBAO_NATIVE_VIEW。
        //TradeConstants.ISV_CODE
        //radeConstants. TAOBAO_BACK_URL:设置启动手淘native页面后的返回页面
        if (pageType.equals("baichuan")) {
            params.put(TradeConstants.ITEM_DETAIL_VIEW_TYPE, TradeConstants.BAICHUAN_H5_VIEW);

        }
        if (pageType.equals("h5")) {
            params.put(TradeConstants.ITEM_DETAIL_VIEW_TYPE, TradeConstants.TAOBAO_H5_VIEW);

        }
        if (pageType.equals("taobao")) {
            params.put(TradeConstants.ITEM_DETAIL_VIEW_TYPE, TradeConstants.TAOBAO_NATIVE_VIEW);
        }
        ItemDetailPage itemDetailPage = new ItemDetailPage(itemid, params);
        if (pid.equals("")) {
            taokeParams = null;
        }
        UiSettings uiSettings = new UiSettings();
        uiSettings.title = "淘宝商品详情";
        tradeService.show(itemDetailPage, taokeParams, this.cordova.getActivity(), null, callback(cbc));
        return true;
    }

    public boolean showItemDetailByItemId(Long itemid, final CallbackContext cbc) {
        TaeWebViewUiSettings taeWebViewUiSettings = new TaeWebViewUiSettings();
        ItemService itemService = AlibabaSDK.getService(ItemService.class);
        itemService.showItemDetailByItemId(this.cordova.getActivity(), callback(cbc), taeWebViewUiSettings, itemid, 1, null);
        return true;
    }

    public boolean showPageH5(String wd, final CallbackContext cbc) {
        ItemService itemService = AlibabaSDK.getService(ItemService.class);
        String tburl = "http://s.m.taobao.com/h5?q=";
        if (wd.equals("")) {
            tburl = "http://m.taobao.com";
        } else {
            tburl += wd;
        }
        itemService.showPage(this.cordova.getActivity(),
                callback(cbc), null, tburl);
        return true;
    }

    public boolean showTaokeItemDetailByItemId(String pid, Long itemid, final CallbackContext cbc) {
        TaeWebViewUiSettings taeWebViewUiSettings = new TaeWebViewUiSettings();
        TaokeParams taokeParams = new TaokeParams();
        taokeParams.pid = pid;
        taokeParams.unionId = "null";
        ItemService itemService = AlibabaSDK.getService(ItemService.class);
        itemService.showTaokeItemDetailByItemId(this.cordova.getActivity(), callback(cbc), taeWebViewUiSettings, itemid, 1, null, taokeParams);
        return true;
    }

    public boolean showCart(final CallbackContext cbc) {
        CartService cartService = AlibabaSDK.getService(CartService.class);
        cartService.showCart(this.cordova.getActivity(), callback(cbc));
        return true;
    }

    /***
     * 添加商品到购物车
     *
     * @param openId
     * @return boon
     */
    public boolean addItem2Cart(String openId, final CallbackContext cbc) {

        CartService cartService = AlibabaSDK.getService(CartService.class);
        cartService.addItem2Cart(this.cordova.getActivity(), callback(cbc), "加入购物车", openId, null);
        return true;
    }

    public boolean addTaoKeItem2Cart(String pid, String openId, final CallbackContext cbc) {
        TaokeParams taokeParams = new TaokeParams();
        taokeParams.pid = pid;
        taokeParams.unionId = "null";
        CartService cartService = AlibabaSDK.getService(CartService.class);
        cartService.addTaoKeItem2Cart(this.cordova.getActivity(), callback(cbc), "加入购物车", openId, null, taokeParams);
        return true;
    }

    public boolean showOrderWithSKU(String openId, final CallbackContext cbc) {
        OrderService orderService = AlibabaSDK.getService(OrderService.class);
        orderService.showOrderWithSKU(this.cordova.getActivity(), callback(cbc), "选择SKU", openId, null);
        return true;

    }

    public boolean showTaoKeOrderWithSKU(String pid, String openId, final CallbackContext cbc) {
        TaokeParams taokeParams = new TaokeParams();
        taokeParams.pid = pid;
        taokeParams.unionId = "null";
        OrderService orderService = AlibabaSDK.getService(OrderService.class);
        orderService.showTaoKeOrderWithSKU(this.cordova.getActivity(), callback(cbc), "选择SKU", openId, null, taokeParams);
        return true;
    }

    //MyOrdersPage(我的订单页)

    /**
     * 我的订单页
     *
     * @param status   默认跳转页面；填写：0：全部；1：待付款；2：待发货；3：待收货；4：待评价。若传入的不是这几个数字，则跳转到“全部”页面且“allOrder”失效
     * @param allOrder true：显示全部订单。False：显示当前appKey下的订单
     */
    public void showMyOrdersPage(int status, boolean allOrder, final CallbackContext cbc) {
        TradeService tradeService = AlibabaSDK.getService(TradeService.class);
        MyOrdersPage myOrdersPage = new MyOrdersPage(status, allOrder);
        tradeService.show(myOrdersPage, null, this.cordova.getActivity(), null, callback(cbc));
    }

    //Page类型:MyCartsPage(我的购物车页)
    public void showMyCartsPage(final CallbackContext cbc) {
        TradeService tradeService = AlibabaSDK.getService(TradeService.class);
        MyCartsPage myCartsPage = new MyCartsPage();
        tradeService.show(myCartsPage, null, this.cordova.getActivity(), null, callback(cbc));
    }
}
