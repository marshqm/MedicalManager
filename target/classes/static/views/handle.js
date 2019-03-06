JB.define("handle", function(){
    var UI = {
        $src : '',
        $photoName : '',
 //       $loginName : '',
        COMMONUSER : 3,
        $imgWidth : 836,
        $imgHeight: 488,
        $loginUser : $('#index-login-user'),
        $placeholder : $('#handle-list-placeholder'),
        $mainImg : $('#handle-main-img'),
        $imgList : $('#handle-viewList-list'),
        $superDiv : $('#handle-viewList-list'),
        $big : $('#handle-big'),
        $small : $('#handle-small'),
        $rotate : $('#handle-rotate'),
        $reset : $('#handle-reset'),
        $download : $('#handle-download'),
        $downloadA : $('#handle-download-a'),
    }

/*    var getUserType = function(){
           var url = JB.API + '/self';
            $.ajax({
                url: url,
                data: 'name=' + UI.$loginUser.text(),
                method: 'GET',
                success: function(data) {
                    if (data.obj.userType == UI.COMMONUSER) {
                        UI.$loginName = data.obj.loginName;
                    }
                },
                error: function(msg) {
                    $.notify(msg.reponseJSON.message,{
                        type:'danger'
                    })
                }
            })
    };
    getUserType();*/

    var loadList = function() {
        var url = JB.API + '/handle';
        var loginName = UI.$loginUser.text();
/*        var data = '';
        if (UI.$loginName != ''){
            data = 'loginName=' + UI.$loginName;
        }*/
        $.ajax({
            url: url,
            method: 'GET',
            data: 'loginName=' + loginName,
            success: function(data) {
                console.log(data);
                data = data.obj;
                if (data.length > 0){
                    UI.$placeholder.hide();
                }
                for (var i = 0; i < data.length; i++) {
                    UI.$imgList.append('<div class="handle-viewList"><img class="viewList-img"'
                        + ' src="/upload/'+data[i].fileSaveName+'" data-photoname="'+data[i].fileName+'"alt=""></div>');
                }
            },
            error: function(msg) {
                msg = msg.message;
                $.notify(msg,{
                    type:'danger'
                })
            }
        })
    };
    loadList();

    UI.$superDiv.on('click', '.viewList-img', function(){
        var src = $(this).attr('src');
        UI.$mainImg.attr('src',src);
        UI.$src = src;
        UI.$photoName = $(this).data('photoName');
    });

var imgSrc = document.getElementById('handle-main-img');
    UI.$big.click(function(){
        UI.$imgWidth = UI.$imgWidth * 1.1;
        UI.$imgHeight = UI.$imgHeight * 1.1;
        UI.$mainImg.width(UI.$imgWidth );
        UI.$mainImg.height(UI.$imgHeight );

    });

    UI.$small.click(function(){
        UI.$imgWidth = UI.$imgWidth / 1.1;
        UI.$imgHeight = UI.$imgHeight / 1.1;
        UI.$mainImg.width(UI.$imgWidth );
        UI.$mainImg.height(UI.$imgHeight );
    });

    var deg = 0;
    UI.$rotate.click(function(){
        deg += 90;
        document.getElementById('handle-main-img').style.transform = "rotate(" + deg + "deg)";
    });
     UI.$reset.click(function(){
        UI.$mainImg.width(836);
        UI.$mainImg.height(499 );
        deg = 0;
        document.getElementById('handle-main-img').style.transform = "rotate(" + deg + "deg)";
    });

    UI.$download.click(function(){
        UI.$downloadA.attr('href',UI.$src);
        UI.$downloadA.attr('download',UI.$photoName);

    });




})