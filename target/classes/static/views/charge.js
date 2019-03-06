JB.define("charge", function(){

    $("#charge-myFile").fileinput({
        language : 'zh',
        uploadUrl : JB.API + "/upload/photo/" + $('#charge-userSelect').val(),
        autoReplace : true,
        maxFileCount : 1,
        allowedFileExtensions : [ "jpg", "png", "gif" ],
        browseClass : "btn btn-primary", //按钮样式
        previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
        uploadExtraData:function(){//向后台传递参数
            var data={
                apkName:$('#charge-userSelect').val(),

            };
            return data;
        },

    }).on("fileuploaded", function(e, data) {
        data = data.response;
        console.log(data)
        if (data.code == JB.SUCCESS){
            $("#charge-logo").attr("value", data.obj.success);
            $.notify('上传文件成功,文件位置:'+data.obj.success,{
                type:'success'
            })
        }
    });

$(document).ready(function(){
    var data = new Object();
    data.userType =  3;
     $.ajax({
            url: JB.host + '/marsh/user',
            type: 'GET',
            data: data,
            success: function(data) {
                data = data.obj;
                for (var i = 0 ; i < data.length; i++) {
                    $('#charge-userSelect').append('<option value="'+data[i].loginName+'">'+data[i].realName+'</option>');
                }
            },
            error: function(msg) {
                console.log(msg);
            }
        })
});


});