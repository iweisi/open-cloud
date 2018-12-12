layui.define(['jquery'], function (exports) {
    var $ = layui.jquery;
    var tools = {
        /**
         * 移除数组的值
         * @param arr   数组
         * @param val   删除的值
         */
        removeByValue: function (arr, val) {
            for (var i = 0; i < arr.length; i++) {
                if (arr[i] == val) {
                    arr.splice(i, 1);
                    break;
                }
            }
        },
        /**
         * 产生一个唯一的uuid
         * @param len 产生的字符串长度
         * @param radix 进制数
         */
        uuid: function () {
            var s = [];
            var hexDigits = "0123456789abcdef";
            for (var i = 0; i < 36; i++) {
                s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
            }
            s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
            s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
            s[8] = s[13] = s[18] = s[23];

            var uuid = s.join("");
            return uuid;
        },
        getCookie: function (name) {
            var strcookie = document.cookie;//获取cookie字符串
            var arrcookie = strcookie.split("; ");//分割
            for (var i = 0; i < arrcookie.length; i++) {
                var arr = arrcookie[i].split("=");
                if (arr[0] == name) {
                    return arr[1];
                }
            }
            return "";
        }
    }

    exports('tools', tools);
});  