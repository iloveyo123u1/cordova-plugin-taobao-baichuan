var cordova = require('cordova');
var defcbk = function () {
};
//http://baichuan.taobao.com/doc2/detail.htm?spm=0.0.0.0.Ayb23v&treeId=51&articleId=102600&docType=1#s4
module.exports = {
    item_show: function (args, successCallback, errorCallback) {
        if (args == null || args == undefined) {
            args = {};
        }
        if (typeof args === "string" || typeof args === 'number') {
            args = {
                itemid:args
            };
        }
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        args.type = args.type || "taobao";
        args.itemid = args.itemid || "";
        args.pid = args.pid || "";
        cordova.exec(successCallback, errorCallback, 'BaiChuan', 'showItemDetailPage', [{
            itemid: args.itemid,
            pid:  args.pid ,
            pagetype: args.type
        }]);
    },
    cart_show: function (successCallback, errorCallback) {
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        cordova.exec(successCallback, errorCallback, "BaiChuan", 'showMyCartsPage', [
            {}
        ]);
    },
    cart_add: function (openid, successCallback, errorCallback) {
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        cordova.exec(successCallback, errorCallback, 'BaiChuan', 'addItem2Cart', [
            {
                openid: openid
            }
        ]);
    },
    orders_show: function (successCallback, errorCallback) {
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        cordova.exec(successCallback, errorCallback, 'BaiChuan', 'showMyOrdersPage', [
            {
                status: 0,
                all: false
            }
        ]);
    },
    //唤起官方商品组件（真实id）
    showItemDetailByItemId: function (itemid, successCallback, errorCallback) {
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        cordova.exec(successCallback, errorCallback, 'BaiChuan', 'showItemDetailByItemId', [{
            itemid: itemid
        }]);
    },
    //唤起免登H5页面 用于搜索商品
    showPageH5: function (wd, successCallback, errorCallback) {
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        cordova.exec(successCallback, errorCallback, 'BaiChuan', 'showPageH5', [{
            wd: wd
        }]);
    },
    //唤起淘客商品详情(真实id)
    showTaokeItemDetailByItemId: function (pid, itemid, successCallback, errorCallback) {
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        cordova.exec(successCallback, errorCallback, 'BaiChuan', 'showTaokeItemDetailByItemId', [{
            itemid: itemid,
            pid: pid
        }]);
    },
    //最新版本
    showItemDetailPage: function (pid, itemid, pagetype, successCallback, errorCallback) {
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        pagetype = pagetype || "h5";
        cordova.exec(successCallback, errorCallback, 'BaiChuan', 'showItemDetailPage', [{
            itemid: itemid,
            pid: pid,
            pagetype: pagetype
        }]);
    },

    //唤起购物车
    showCart: function (successCallback, errorCallback) {
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        cordova.exec(successCallback, errorCallback, 'BaiChuan', 'showCart', []);
    },
    //添加商品到购物车
    addItem2Cart: function (openid, successCallback, errorCallback) {
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        cordova.exec(successCallback, errorCallback, 'BaiChuan', 'addItem2Cart', [
            {
                openid: openid
            }
        ]);
    },
    //添加淘客商品到购物车
    addTaoKeItem2Cart: function (pid, openid, successCallback, errorCallback) {
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        cordova.exec(successCallback, errorCallback, 'BaiChuan', 'addTaoKeItem2Cart', [
            {
                openid: openid,
                pid: pid
            }
        ]);
    },
    //唤起官方SKU页面（淘客方式）
    showTaoKeOrderWithSKU: function (pid, openid, successCallback, errorCallback) {
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        cordova.exec(successCallback, errorCallback, 'BaiChuan', 'showTaoKeOrderWithSKU', [
            {
                openid: openid,
                pid: pid
            }
        ]);
    },
    //MyOrdersPage(我的订单页)
    showMyOrdersPage: function (ostatus, ois_all, successCallback, errorCallback) {
        ois_all = ois_all || false;
        ostatus = ostatus || 0;
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        cordova.exec(successCallback, errorCallback, 'BaiChuan', 'showMyOrdersPage', [
            {
                status: ostatus,
                all: ois_all
            }
        ]);
    },
    //MyCartsPage(我的购物车页)
    showMyCartsPage: function (successCallback, errorCallback) {
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        cordova.exec(successCallback, errorCallback, "BaiChuan", 'showMyCartsPage', [
            {}
        ]);
    },

    //唤起官方SKU页面
    showOrderWithSKU: function (openid, successCallback, errorCallback) {
        successCallback = successCallback || defcbk;
        errorCallback = errorCallback || defcbk;
        cordova.exec(successCallback, errorCallback, 'BaiChuan', 'showOrderWithSKU', [
            {
                openid: openid
            }
        ]);
    }
};