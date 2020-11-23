/**
 * 请求封装类
 */
var Rquest = (function(window) {

    var Rquest = function(baseURL, methodURL, data, async, successCallback, errorCallback) {
        return new Rquest.fn.init(baseURL, methodURL, data, async, successCallback, errorCallback);
    }

    Rquest.fn = Rquest.prototype = {
        constructor: Rquest,
        init: function(baseURL, methodURL, data, async, successCallback, errorCallback) {

            if(!baseURL || baseURL.replace(/\s*/g,"") == ""){
                baseURL = "";
            }
            if(!methodURL || methodURL.replace(/\s*/g,"") == ""){
                methodURL = "";
            }

            this.requestURL = baseURL + methodURL;
            this.data = data;

            this.ajaxpost= function () {

                if(this.requestURL && this.requestURL.replace(/\s*/g,"") != ""){
                    console.log("Rquest:  " + this.requestURL +"  with:  " + JSON.stringify(this.data));
                    $.ajax({
                        url:this.requestURL,
                        type:"post",
                        data:this.data,
                        async: async,
                        dataType:"json",
                        //contentType: "application/json;charset=UTF-8",
                        success:function(data){
                            if(data.show && !data.validate){
                                tips(data.msg);
                            }
                            successCallback(data);
                        },
                        error:function(xhr, textStatus, errorThrown){
                            console.log(xhr);
                            console.log(textStatus);
                            console.log(errorThrown);
                            if(null != errorCallback){
                                errorCallback(xhr, textStatus, errorThrown);
                            }
                        }
                    })
                }else{
                    console.log("Rquest url is empty");
                }
            }
        }
    }

    Rquest.fn.init.prototype = Rquest.fn;
    return Rquest;
})();