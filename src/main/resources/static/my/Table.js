/**
 * 请求封装类
 */
var Table = (function(window) {

    var Table = function(element, column, param, baseurl, methodurl) {
        return new Table.fn.init(element, column, param, baseurl, methodurl);
    }

    Table.fn = Table.prototype = {
        constructor: Table,
        init: function(element, column, param, baseurl, methodurl) {

            this.param = param;
            //渲染表格
            this.renderingTable= function () {

                var html_head = '<div class="table_header"><ul class="ul_wrap">',
                    html_column = '<div class="table_content">',
                    html_pagination = '<div class="table_pagination"><ul class="pageWrap">\n' +
                        '<li class="page_text " id="page_pre">上一页</li>\n' +
                        '$pagination$' +
                        '<li class="page_text " id="page_next">下一页</li>\n' +
                        '<li class="page_total ">共<span class="total_num">$pageCount$</span>页</li></ul></div>',
                    pagination = null;

                //设置表格Title
                $.each(column, function(keyc, vc){
                    $.each(vc, function(k, v){
                        html_head += '<li class="li_wrap">'+v+'</li>';
                    })
                })
                html_head += '<li class=" opcity">操作</li></ul></div>';

                //加载数据，拼接表格行
                new Rquest(baseurl, methodurl, {param:JSON.stringify(this.param)}, false,
                    function (response) {
                        if(response && response.validate && response.pagination){

                            var data = response.pagination.datas;
                            pagination = response.pagination;
                            if(data.length == 0){
                                html_column = '<ul class="ul_wrap no_content">暂无数据</ul></div>';
                            }else{
                                $.each(data, function(datakey, datav){

                                    html_column += '<ul class="ul_wrap">';
                                    $.each(column, function(keyc, vc){
                                        $.each(vc, function(k, v){

                                            if(k === "sex"){
                                                if(datav[k] == 2){
                                                    html_column += '<li class="li_wrap">男</li>';
                                                }else{
                                                    html_column += '<li class="li_wrap">女</li>';
                                                }
                                            }else{
                                                html_column += '<li class="li_wrap" title="'+datav[k]+'">'+datav[k]+'</li>';
                                            }
                                        })
                                    })
                                    html_column += '<li class=" opcity">'+
                                        '<span class="edit" value="" onclick="update(\''+datav['id']+'\');">修改</span>'+
                                        '<span class="delete" onclick="remove(\''+datav['id']+'\');">删除</span>'+
                                        '</li></ul>';
                                });
                            }
                        }
                    },
                    function () {
                        console.log("error");
                    }).ajaxpost();

                if(pagination == null){
                    //tips("分页信息错误");
                }
                console.log(pagination);
                const pageIndex = pagination != null ?pagination['pageIndex']:null;
                const pageCount = pagination != null ?pagination['pageCount']:0;
                html_pagination = html_pagination.replace("$pageCount$",(pageIndex + 1) +"/"+ pageCount)

                if(pageCount>3){
                    var s = '';
                    for(var i = pageIndex + 2; i < pageCount; i++){
                        s += '<li class="page_text page_no">'+i+'</li>';
                    }
                    html_pagination = html_pagination.replace("$pagination$",s);
                }else{
                    html_pagination = html_pagination.replace("$pagination$",'');
                }

                html_column += '</div>';



                //渲染到页面
                $(element).html('');
                $(element).append(html_head).append(html_column).append(html_pagination);


                if (pagination.isFirstPage) {

                    //禁用上一页,启用下一页
                    $("#page_pre").unbind('click').removeClass("page_text").addClass("page_text_disable");
                    this.nextPage(pageIndex);
                } else if(pagination.isLasePage) {

                    //禁用下一页,启用上一页
                    $("#page_next").unbind('click').removeClass("page_text").addClass("page_text_disable");
                    this.prePage(pageIndex);
                } else{
                    this.prePage(pageIndex);
                    this.nextPage(pageIndex);
                }

                $(".page_no").click(function () {
                    const pageIndex = parseInt($(this).text());
                    param['pagination']['pageIndex'] = pageIndex - 1;
                    new Table(element, column, param, baseurl, methodurl).renderingTable();

                    $(".page_no").each(function (k,v) {
                        if(pageIndex - 1 == k){
                            $(v).addClass("page_text_active");
                        }else{
                            $(v).removeClass("page_text_active");
                        }
                    })
                });
            },
            //下一页
            this.nextPage = function (pageIndex) {
                $("#page_next").click([pageIndex],function (event) {
                        param['pagination']['pageIndex'] = event.data[0] + 1;
                        new Table(element, column, param, baseurl, methodurl).renderingTable();
                    }
                );
            },
            //上一页
            this.prePage = function (pageIndex) {
                $("#page_pre").click([pageIndex],function (event) {
                    param['pagination']['pageIndex'] = event.data[0] - 1;
                        new Table(element, column, param, baseurl, methodurl).renderingTable();
                    }
                );
            }
        }
    }

    Table.fn.init.prototype = Table.fn;
    return Table;
})();