JB.define("user", function(){
     $(document).ready(function(){
        $('#register-userType').attr('disabled','disabled');
     });




$('#user-register-form').validate({
submitHandler: function() {
      $('#register-save').click(function(){
        $.ajax("/marsh/user/save",{
            method: 'POST',
            data: JSON.stringify($('#user-register-form').serializeJSON()),
            success: function(data) {
                if (data.code == JB.SUCCESS) {
                    $('#registerModal').modal('hide');
                    $.notify('注册成功,现在登陆吧', {
                        type: 'success'
                     })
                }
            }

        })
    })
    }
})
})