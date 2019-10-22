UrlParm = function() { // url参数
    // 登录验证接口
    // var loginInterface='http://192.168.0.166:8080/user/login';
    // 济南一中--温控器接口
    var oneWen='http://192.168.0.166:8080/device/getDeviceData';
    // 济南一中--风机
    var oneFeng='http://192.168.0.166:8080/device/getDeviceData';
    // 济南二中--温控器接口
    var SecondEen='http://192.168.0.166:8080/device/getDeviceData';
    // 济南二中--风机接口
    var SecondFeng='http://192.168.0.166:8080/device/getDeviceData';
    return {
        // 登录验证接口
        parm : function() { // o: 参数名或者参数次序
            try {
                return loginInterface='http://192.168.0.166:8080/user/login';
            } catch (e) {
            }
        },
        //获得参数组, 类似request.getParameterValues()
        parmValues : function(o) { //  o: 参数名或者参数次序
            try {
                return (typeof(o) == 'number' ? data[o] : data[index[o]]);
            } catch (e) {}
        },
        //是否含有parmName参数
        hasParm : function(parmName) {
            return typeof(parmName) == 'string' ? typeof(index[parmName]) != 'undefined' : false;
        },
        // 获得参数Map ,类似request.getParameterMap()
        parmMap : function() {
            var map = {};
            try {
                for (var p in index) {  map[p] = data[index[p]];  }
            } catch (e) {}
            return map;
        }
    }
}(document, window);
  