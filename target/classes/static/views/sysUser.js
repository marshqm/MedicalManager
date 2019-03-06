JB.define("user", function(){

var UI = {
    $searchForm : $('#user-search-form'),
    $searchBtn : $('#user-search-btn'),
    $mainAdd : $('#user-main-add'),
    $mainTable : $('#user-main-table'),
    $mainModal : $('#user-mainModal'),
    $mainModalForm : $('#user-mainModal-form'),
    $loginName : $('#user-mainModal-loginName'),
    $mainModalSave : $('#user-mainModal-save'),
    $mainModalDelete : $('#user-mainModal-delete'),
    $Id : $('#user-mainModal-id'),
    $userName : $('#mainModal-userName'),
    $gender : $('#mainModal-gender'),
    $age : $('#mainModal-age'),
    $bornPlace : $('#mainModal-bornPlace'),
    $phoneNumber : $('#mainModal-phoneNumber'),
    genders : {
        0: '',
        1: '男',
        2: '女',
    },
    userTypes : {
        0: '',
        1: '管理员',
        2: '医生',
        3: '普通用户'
    },

};


var userTable = UI.$mainTable.DataTable({
    ajax : {
        url: JB.host + '/marsh/user',
        type: 'GET',
        data: function(d) {
            var searchForm = UI.$searchForm.serializeJSON();
            d.realName = searchForm.userName;
            d.userType = searchForm.userType;
            return d;
        }
    },
    paging: false,
    columns: [
        {data: 'realName', defaultContent: ''},
        {render: function(d, t, row) {
            return UI.genders[row.gender==null?0:row.gender];
        }},
        {data: 'age', defaultContent: ''},
        {render: function(d, t, row) {
            return UI.userTypes[row.userType==null?0:row.userType];
        }},
        {data: 'phoneNumber', defaultContent: ''},
        {data: 'bornPlace', defaultContent: ''},
        {render: function(d, t, row) {
            return '<a href="#" class="user-mainTable-edit" data-toggle="modal" data-target="#user-mainModal" data-id="' + row.id + '" title="修改用户信息或删除" > 编辑</a>'
        }}
    ]
});

UI.$searchBtn.on('click', function() {
    userTable.ajax.reload();
});

UI.$mainAdd.on('click', function() {
    document.getElementById('user-mainModal-form').reset();
    UI.$mainModalDelete.hide();
});

UI.$mainTable.on('click', '.user-mainTable-edit', function() {
    UI.$mainModalDelete.show();
    var id = $(this).data('id');
    $.ajax({
        url: JB.host + '/marsh/user/' + id,
        method: 'GET',
        success: function(data) {
            var data1 = data.obj;
            UI.$mainModalForm.autofill(data1);
        }

    })
});

UI.$mainModalSave.on('click', function() {
    var loginName = UI.$loginName.val();
    $.ajax({
        url: JB.API + '/self',
        method: 'GET',
        data: 'name=' + loginName,
        success: function(data) {
            console.log(data)
            if (data.obj == null||UI.$Id.val() !='') {
                saveInfo();
            }else {
                $.notify('登录名已存在',{
                    type: 'danger'
                })
            }
        },
        error: function(msg) {
            console.log(msg)
        }
    })

});


var saveInfo = function(){

    var $form = UI.$mainModalForm.serializeJSON();

    $.ajax({
        url: JB.host + '/marsh/user/save',
        contentType: 'application/json',
        dataType: 'json',
        method: 'POST',
        data: JSON.stringify($form),
        success: function(data) {
            if (data.code == JB.SUCCESS) {
                UI.$mainModal.modal('hide');
                userTable.ajax.reload();
                var message = '保存成功';
                if (UI.$Id.val() == null || UI.$Id.val() == '') {
                    message = '新增用户成功,默认密码123456';
                }
                $.notify(message,{
                    type: 'success'
                })
            }
        }

    })
}


UI.$mainModalDelete.click(function(){
   var id = UI.$Id.val();
  var url = JB.host + '/marsh/user/' + id + '/delete';
  $.ajax({
    url: url,
    method: 'DELETE',
    success: function(data) {
        if (data.code == JB.SUCCESS) {
            UI.$mainModal.modal('hide');
            userTable.ajax.reload();
            $.notify('成功删除用户',{
                type: 'success'
            })
        }
    }

  })
});


})