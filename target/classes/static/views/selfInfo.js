JB.define("selfInfo", function(){

var UI = {
    selfData : {},
    $baseForm : $('#selfInfo-baseForm'),
    $baseEditButton : $('#selfInfo-baseEdit'),
    $mainModal : $('#selfInfo-mainModal'),
    $mainModalForm : $('#selfInfo-mainModal-form'),
    $mainModalSave : $('#selfInfo-mainModal-save'),
    $loginUser : $('#index-login-user'),

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




var loadInfo = function() {
    $('.self-read').attr('disabled','disabled');

    var url = JB.API + '/self';
    $.ajax({
        url: url,
        data: 'name=' + UI.$loginUser.text(),
        method: 'GET',
        success: function(data) {
            data = data.obj;
            UI.selfData = data;
            data.userType = UI.userTypes[data.userType];
            data.genders = UI.genders[data.gender];
            UI.$baseForm.autofill(data);
        },
        error: function(msg) {
            console.log(msg);
            $.notify(msg.reponseJSON.message,{
                type:'danger'
            })
        }
    })
}

loadInfo();

UI.$baseEditButton.click(function(){
    UI.$mainModalForm.autofill(UI.selfData);
})

UI.$mainModalSave.on('click', function() {

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
                loadInfo();
                UI.$mainModalForm.autofill(UI.selfData);
            }
        }

    })

});


})