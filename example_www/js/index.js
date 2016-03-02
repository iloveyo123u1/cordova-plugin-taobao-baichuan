var app = {
    // Application Constructor
    initialize: function () {
        this.bindEvents();
    },

    bindEvents: function () {
        document.addEventListener('deviceready', this.onDeviceReady, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicitly call 'app.receivedEvent(...);'
    onDeviceReady: function () {
        app.receivedEvent('deviceready');
        console.log('deviceready');
        document.getElementById('bt_item').addEventListener('click', function () {
            console.log('bt_item');
            TaobaoBaichuan.item_show(521348392294);
        }, false);
        document.getElementById('bt_itemh5').addEventListener('click', function () {
            console.log('bt_itemh5');
            TaobaoBaichuan.item_show({ type:'h5',itemid:521348392294 });
        }, false);
        document.getElementById('bt_cart').addEventListener('click', function () {
            console.log('bt_cart');
            TaobaoBaichuan.cart_show();
        }, false);
        document.getElementById('bt_order').addEventListener('click', function () {
            console.log('bt_order');
            TaobaoBaichuan.orders_show();
        }, false);
    },
    // Update DOM on a Received Event
    receivedEvent: function (id) {
        var parentElement = document.getElementById(id);
        console.log('Received Event: ' + id);
    }
};

app.initialize();