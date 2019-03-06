JB = {
    // UI
    app_menu_url : '/js/sidebar-2.json',
    $nav: $('.sidebar-nav > ul.nav'),
    $siderbar: $('#app-siderbar'),
    $main: $('#ui-view'),
    $crumb: $('#app-crumb').children(),
    defaultPage: 'dashboard',
    viewDir: 'static/',
    // constant
    SUCCESS: '10000',
    USER_LOGIN: '19000',
    API: location.protocol + '//' + location.host + '/admin/v1',
    host: location.protocol + '//' + location.host,
    defines: {},
    colors: ["btn-twitter","btn-facebook","btn-linkedin","btn-flickr","btn-github","btn-html5"]
};

$(document).on('click','#logout', function(){
    $.ajax({
        url: JB.host + '/logout',
        method: 'POST',
        success: function(data){
            window.location.href='/login';
             $.notify('已安全推出登陆', {
                 type: 'success'
             })
        }
    });

});

$(document).ready(function(){
      $.ajax({
        url:'/login/user',
        method: 'GET',
        success: function(data){
          console.log(data.obj.loginUser);
          data = data.obj;
          $('#index-login-user').text(data.loginUser);
            if (data.userType == 1){
               console.log(data.userType);
               JB.app_menu_url = '/js/sidebar.json'
            }else if(data.userType ==2) {
                JB.app_menu_url = '/js/sidebar-1.json'
            }else {
                JB.app_menu_url = '/js/sidebar-2.json'
            }
           // loadSidebar();

        },
        error: function(data){
          console.log(data);
            //loadSidebar();
        }
      });
    });



JB.randomColor = function(i) {
    if(i >= JB.colors.length){
        return JB.colors[i%JB.colors.length];
    }else {
        return JB.colors[i];
    }
}

$.ajaxSetup({
    timeout: 5000,
    cache: false,
    // dataType: 'json',
    dataFilter: function (str, type) {
        //        if(type != 'json') return str;
        if (str.charAt(0) != '{') return str;

        var data = JSON.parse(str);
        if (data.code != JB.SUCCESS) {
            if (data.code == JB.USER_LOGIN) { // 未登录
                window.location = 'login.html';
                return '{}';
            } else {
                var message = data.message || data.code || '出错了'
                $.notify(message, {
                    type: 'danger'
                })
            }
        }
        return str;
    },
    error: function (xhr, textStatus, errorThrown) {
        var message = errorThrown;

        if(xhr.status == 502) {
            message = '服务器无法连接';
        } else {
            var res = xhr.responseJSON;
            if (res) {
                message = res.message || res.code;
            }
        }
        $.notify(message, {
            type: 'danger'
        })
    }
})

if ($.notifyDefaults) {
    $.notifyDefaults({
        type: 'success',
        z_index: 2000,
        // allow_dismiss: false
    });
}

if ($.validator) {
    $.validator.setDefaults({
        errorElement: 'em',
        errorPlacement: function (error, element) {
            // Add the `help-block` class to the error element
            error.addClass('invalid-feedback');
            if (element.prop('type') === 'checkbox') {
                error.insertAfter(element.parent('label'));
            } else {
                error.insertAfter(element);
            }
        },
        highlight: function (element, errorClass, validClass) {
            $(this.currentForm).addClass('was-validated')
        },
        unhighlight: function (element, errorClass, validClass) {
            $(this.currentForm).removeClass('was-validated')
        }
    })
}

if ($.fn.dataTable) {
    $.extend($.fn.dataTable.defaults, {
        searching: false,
        ordering: false,
        processing: true,
        serverSide: true,
        paging: true,
        autoWidth: false,
        displayLength: 20,
        lengthMenu: [20, 50, 100],
        pagingType: "full",
        dom: "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-12 col-md-5 dt-tools'li><'col-sm-12 col-md-7'p>>",
        deferLoading: null, // -1 表示第一次不加载数据
        fixedHeader: {
            header: true,
            headerOffset: 60 // header height
        },
        responsive: true,
        ajax: {
            type: "GET",
            dataSrc: function (result) {
                if (result.code != JB.SUCCESS) {
                    return [];
                }
                if (result.obj.total >= 0) {
                    if (result.obj.draw) {
                        result.draw = result.obj.draw;
                    }
                    result.recordsTotal = result.obj.total;
                    result.recordsFiltered = result.obj.total;
                    return result.obj.data;
                } else {
                    return result.obj;
                }
            },
            data: function (d) {
                for (var key in d) {
                    if (key.indexOf("columns") == 0 || key.indexOf("order") == 0 || key.indexOf("search") == 0) {
                        delete d[key];
                    }
                }
            }
        },
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "_MENU_",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "当前 _START_ - _END_，共 _TOTAL_",
            "sInfoEmpty": "",
            "sInfoFiltered": "",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "没有数据",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        // initComplete
        // drawCallback
    })
}

if (jQuery.fn.dataTable) {
    jQuery.fn.dataTable.ellipsis = function (cutoff, wordbreak, escapeHtml) {
        var esc = function (t) {
            return t
                .replace(/&/g, '&amp;')
                .replace(/</g, '&lt;')
                .replace(/>/g, '&gt;')
                .replace(/"/g, '&quot;');
        };

        return function (d, type, row) {
            // Order, search and type get the original data
            if (type !== 'display') {
                return d;
            }

            if (typeof d !== 'number' && typeof d !== 'string') {
                return d;
            }

            d = d.toString(); // cast numbers

            if (d.length <= cutoff) {
                return d;
            }

            var shortened = d.substr(0, cutoff - 1);

            // Find the last white space character in the string
            if (wordbreak) {
                shortened = shortened.replace(/\s([^\s]*)$/, '');
            }

            // Protect against uncontrolled HTML input
            if (escapeHtml) {
                shortened = esc(shortened);
            }

            return '<span class="ellipsis" title="' + esc(d) + '">' + shortened + '&#8230;</span>';
        };
    };
}

if($.fn.datetimepicker) {
    $.fn.datetimepicker.defaults = $.extend($.fn.datetimepicker.defaults, {
        format: 'Y-m-d H:i:s',
        formatTime:	'H:i',
		formatDate:	'Y-m-d',
        step:5,
        onShow: function(current_time,$input) {
            if($input.val() == '') {
                var now = new Date();
                now.setSeconds(0);
                now.setMinutes((Math.floor(now.getMinutes()/5)+1)*5);
                this.setOptions({
                    value: now
                })
            }
        }
    });
    jQuery.datetimepicker.setLocale('ch');
}

function loadJS(files) {
    // remove loaded object
    for (var k1 in JB.defines) {
        for (var k2 in JB.defines[k1]) {
            delete JB.defines[k1][k2];
        }
        delete JB.defines[k1];
    };

    var i;
    var body = document.getElementsByTagName('body')[0];
    var adds = body.getElementsByTagName('script');
    for (i = 0; i < adds.length; i++) {
        var add = adds[i];
        if (add.className == '.add') {
            add.parentNode.removeChild(add);
        }
    }

    if (files == undefined) return;

    if (!Array.isArray(files)) {
        files = [files];
    }

    for (i = 0; i < files.length; i++) {
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.async = false;
        script.src = files[i] + '?_' + new Date().getTime()
        script.className = '.add';
        body.appendChild(script);
    }
}

function loadCSS(cssFiles) {
    var i;
    var body = document.getElementsByTagName('body')[0];
    var adds = body.getElementsByTagName('link');
    for (i = 0; i < adds.length; i++) {
        var add = adds[i];
        if (add.className == '.add') {
            add.parentNode.removeChild(add);
        }
    }

    if (files == undefined) return;

    if (!Array.isArray(files)) {
        files = [files];
    }

    for (i = 0; i < files.length; i++) {
        var style = document.createElement('link');
        style.setAttribute('rel', 'stylesheet');
        style.setAttribute('type', 'text/css');
        style.setAttribute('href', file[i]);
        body.appendChild(style);
    }
}

JB.define = function (name, fun) {
    JB.defines[name] = fun() || {};
    JB.defines[name]['_fun_'] = fun;
};


//// Appliction UI ////
$(document).ready(function ($) {
//var loadSidebar = function($){
    $.ajax({
        url: JB.API + '/system/tool/constant',
        dataType: 'json'
    }).then(function (res) {
        if (res.code == JB.SUCCESS) {
            JB.enum = res.obj;
        } else {
            return new $.Deferred().reject(res).promise();
        }
    }).done(function () {
        // Sidebar menu
        $.ajax({
            url: JB.app_menu_url,
            success: function (data) {
                var i1, i2;
                for (i1 = 0; i1 < data.length; i1++) {
                    var m1 = data[i1];
                    var hasMenu = (m1.menus !== undefined && m1.menus.length > 0);
                    var m1Id = m1.id || i1
                    var str = '<li class="nav-item ' + (hasMenu ? 'nav-dropdown' : '') + ' ' + (m1.open ? 'open' : '') + '"><a class="nav-link ' + (hasMenu ? 'nav-dropdown-toggle' : '') + '" href="' + (m1.url || '#') + '" data-id="' + m1Id + '"><i class="fa fa-' + m1.icon + '"></i> ' + m1.name + (m1.badge ? '<span class="badge badge-info">' + m1.badge + '</span>' : '') + '</a>';

                    if (hasMenu) {
                        str += '<ul class="nav-dropdown-items">';
                        for (i2 = 0; i2 < m1.menus.length; i2++) {
                            var m2 = m1.menus[i2];
                            var m2Id = m2.id || (i1 + '-' + i2)
                            str += '<li class="nav-item"><a class="nav-link" href="' + (m2.url || '#') + '"id=si'+m2Id+' data-id=' + m2Id + '>' + m2.name + '</a></li>';
                        }
                        str += '</ul>';
                    }
                    str += '</li>';
                    JB.$siderbar.append(str);
                }
            },
            error: function () {
                $.notify('无法连接服务器');
            },
            complete: function () {
                var url = location.hash.replace(/^#/, '');

                if (url != '') {
                    setUpUrl(url);
                } else {
                    setUpUrl(JB.defaultPage);
                }
            }
        });
    }).fail(function (res) {
        if (res.code == JB.USER_LOGIN) {
            window.location = 'login.html';
        }
    });

    // Dropdown Menu
    JB.$nav.on('click', 'a', function (e) {
        e.preventDefault();

        if ($(this).hasClass('nav-dropdown-toggle')) {
            $(this).parent().toggleClass('open');
            resizeBroadcast();
        }
    });

    JB.$nav.on('click', 'a[href!="#"]', function (e) {
        if ($(this).parent().parent().hasClass('nav-tabs') || $(this).parent().parent().hasClass('nav-pills')) {
            e.preventDefault();
        } else if ($(this).attr('target') == '_top') {
            e.preventDefault();
            var target = $(e.currentTarget);
            window.location = (target.attr('href'));
        } else if ($(this).attr('target') == '_blank') {
            e.preventDefault();
            var target = $(e.currentTarget);
            window.open(target.attr('href'));
        } else {
            e.preventDefault();
            var target = $(e.currentTarget);
            setUpUrl(target.attr('href'));
        }
    });

    $(document).on('click', 'a[href="#"]', function (e) {
        e.preventDefault();
    });

    function setUpUrl(url) {
        JB.$nav.find('li .nav-link').removeClass('active');
        // $('nav .nav li.nav-dropdown').removeClass('open');
        // $('nav .nav li:has(a[href="' + url.split('?')[0] + '"])').addClass('open');
        $this = JB.$nav.find('a[href="' + url.split('?')[0] + '"]');
        $this.addClass('active');
        $parent = $this.parent().parent().parent();

        if($parent.length > 0) {
            if ($parent.get(0).tagName == 'NAV') { // level 1
                JB.$crumb.eq(0).text(onlyText($this));
                JB.$crumb.eq(1).text('');
            } else {
                JB.$crumb.eq(0).text(onlyText($parent.children().first()));
                JB.$crumb.eq(1).text(onlyText($this));
            }
        }
        loadPage(url);
    }

    function onlyText($t) {
        return $t.contents().filter(function () {
            return this.nodeType == 3;
        }).text()
    }

    function loadPage(url) {
        var idx = url.lastIndexOf('.');
        var stuff = '.html';

        if (idx > 0 && url.substring(idx) == '.html') {
            url = url.substring(0, idx);
        }

        // Pace.restart();
        $('html, body').animate({
            scrollTop: 0
        }, 0);
        JB.$main.load(JB.viewDir + url + stuff, null, function (responseText) {
            window.location.hash = url;
            initTooltip(url);
        }).delay(250).animate({
            opacity: 1
        }, 0);
    }

    function resizeBroadcast() {

        var timesRun = 0;
        var interval = setInterval(function () {
            timesRun += 1;
            if (timesRun === 5) {
                clearInterval(interval);
            }
            window.dispatchEvent(new Event('resize'));
        }, 62.5);
    }

    function initTooltip(url) {
        /* ---------- Tooltip ---------- */
        $('[rel="tooltip"],[data-rel="tooltip"]').tooltip({
            "placement": "bottom",
            delay: {
                show: 400,
                hide: 200
            }
        });

        /* ---------- Popover ---------- */
        $('[rel="popover"],[data-rel="popover"],[data-toggle="popover"]').popover();
    }

    /* ---------- Main Menu Open/Close, Min/Full ---------- */
    $('.sidebar-toggler').click(function () {
        $('body').toggleClass('sidebar-hidden');
        resizeBroadcast();
    });

    $('.sidebar-minimizer').click(function () {
        $('body').toggleClass('sidebar-minimized');
        resizeBroadcast();
    });

    $('.brand-minimizer').click(function () {
        $('body').toggleClass('brand-minimized');
    });

    $('.aside-menu-toggler').click(function () {
        $('body').toggleClass('aside-menu-hidden');
        resizeBroadcast();
    });

    $('.mobile-sidebar-toggler').click(function () {
        $('body').toggleClass('sidebar-mobile-show');
        resizeBroadcast();
    });

    $('.sidebar-close').click(function () {
        $('body').toggleClass('sidebar-opened').parent().toggleClass('sidebar-opened');
    });

    /* ---------- Disable moving to top ---------- */
    $('a[href="#"][data-top!=true]').click(function (e) {
        e.preventDefault();
    });
})

//// Application Util ////
JB.formatDate = function (ms) {
    return moment(ms).format('YYYY-MM-DD HH:mm:ss');
};


JB.selectGroupAndGameServer = function ($group, $server) {
    $.ajax({
        url: JB.API + "/game/platform?state=A",
        success: function (data) {
            if (data.code == JB.SUCCESS) {
                $group.children('.add').remove();
                data.obj.forEach(function (el) {
                    $group.append("<option class='add' value='" + el.id + "'>" + el.name + "</option>");
                });
            }
        },
        complete: function () {
            JB.selectGameServer($server, $group.val());
        }
    });
    $group.on('change', function () {
        JB.selectGameServer($server, $group.val());
    })
};

JB.selectGameServer = function ($server, groupId) {
    if (groupId == undefined || groupId == '') return;

    var servers = {};
    $.ajax({
        url: JB.API + "/game/server",
        data: {
            platformId: groupId
        },
        success: function (data) {
            if (data.code == JB.SUCCESS) {
                $server.children('.add').remove();
                if (data.obj.data.length == 0) {
                    if ($server.children().length == 0) {
                        $server.append('<option class="add" value="">没有服务器</option>');
                    }
                } else {
                    data.obj.data.forEach(function (el) {
                        $server.append("<option class='add' value='" + el.id + "'>" + el.name + "</option>");
                        servers[el.id] = el.name;
                    });
                }
            }
        }
    })
    return servers;
};

JB.selectEnum = function ($target, name) {
    var arr = JB.enum[name]
    $.each(arr, function (k, v) {
        $target.append('<option value="' + k + '">' + v + '</option>');
    })
}

JB.selectChannel = function ($target, name) {
    $.ajax({
        url: JB.API + "/game/channel",
        success: function (data) {
            if (data.code == JB.SUCCESS) {
                $target.children('.add').remove();
                data.obj.forEach(function (el) {
                    $target.append("<option class='add' value='" + el.code + "'>" + el.name + "</option>");
                });
            }
        },
    });
}

// $table is jQuery
JB.dataTableCheckbox = function($table, callback) {
    $table.on('change', 'input[type="checkbox"]', function() {
        var $this = $(this);
        $this.parent().parent().toggleClass('selected', this.checked);
        if(callback) {
            var rows = JB.dataTableSelected($table);
            callback(rows);
        }
    })
    return $table;
}

JB.dataTableSelected = function($table) {
    var selectedRows = [];
    var table = $table.api();
    $table.find('input[type="checkbox"]').each(function(){
        var $this = $(this);
        if(this.checked) {
            var d = table.row($this.parent().parent()).data();
            if(d) selectedRows.push(d);
        }
    })
    return selectedRows;
}

// @Deprecated
JB.dataTableSelectedData = function ($table) {
    var data = new Array();
    var table = $($table).DataTable();

    $('input[type="checkbox"]').each(function () {
        if (this.checked) {
            $(this).parent().parent().addClass('selected');
        } else {
            $(this).parent().parent().removeClass('selected');
        }
    })

    $('input[type="checkbox"]').each(function () {
        if (this.checked) {
            var rows = table.rows($(this).parent().parent()).data()[0];
            if (rows) {
                data.push(rows);
            }
        }
    });

    var ids = new Array();
    for (var i = 0; i < data.length; i++) {
        ids.push(data[i].id);
    }
    return {
        'ids': ids,
        'rows': data
    };
}

JB.json2html = function ($target, json) {
    for (var key in json) {
        var html = '<div class="form-group row id">' +
            '<label class="col-sm-2 form-control-label ">' + key + '</label>' +
            '<div class="col-sm-10" ><input type="text" class="form-control json2html" name="' + key + '" value="' + json[key] + '"></div>' +
            '</div>';
        $target.append(html);
    }
}




JB.select2 = function ($select, platformId) {
    var selectData = [];
    platformId = platformId> 0? platformId:0;
    $.ajax({
        url: JB.API + '/bill/item/select',
        method: 'POST',
        data: 'platformId=' + platformId,
        success: function (data) {
            for (ev in data.obj) {
                var element = {};
                element['text'] = ev;
                var children = [];
                if (data.obj[ev] != null) {
                    data.obj[ev].forEach(function (eva) {
                        var text = {};
                        text['id'] = eva['code'];
                        text['text'] = eva['name'];
                        children.push(text);
                    })
                }

                element['children'] = children;
                selectData.push(element);
            }

            $select.empty();
            $select.select2({data: selectData});
            $select.select2({matcher: matchStart});
            //自定义选择框的宽度
            $('.select2-container.select2-container--default').attr('style', 'width: 200px;')

        }
    })



    //兼容Bootstrap的模态框
    $.fn.modal.Constructor.prototype.enforceFocus = function () {};

    /*$('#mail-itemSelect').select2({
      dropdownParent: $('#mailModal')
    })*/

    //自定义搜索模式
    function matchStart(params, data) {
        // If there are no search terms, return all of the data
        if ($.trim(params.term) === '') {
            return data;
        }

        // Skip if there is no 'children' property
        if (typeof data.children === 'undefined') {
            return null;
        }

        // `data.children` contains the actual options that we are matching against
        var filteredChildren = [];
        $.each(data.children, function (idx, child) {
            if (child.text.toUpperCase().indexOf(params.term.toUpperCase()) == 0) {
                filteredChildren.push(child);
            }
        });

        // If we matched any of the timezone group's children, then set the matched children on the group
        // and return the group object
        if (filteredChildren.length) {
            var modifiedData = $.extend({}, data, true);
            modifiedData.children = filteredChildren;

            // You can return modified objects from here
            // This includes matching the `children` how you want in nested data sets
            return modifiedData;
        }

        // Return `null` if the term should not be displayed
        return null;
    }
};