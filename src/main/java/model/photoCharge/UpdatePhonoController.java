package model.photoCharge;

import model.statics.CommonErrorCode;
import model.statics.JBoyResponse;
import model.sys.SyUser;
import model.sys.UserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static model.statics.AdminRoute.HANDLE;
import static model.statics.AdminRoute.LOADFILE;

@RestController
public class UpdatePhonoController {

    @Autowired
    FileChargeService fileChargeService;

    @Autowired
    UserService userService;

    @RequestMapping(value = HANDLE, method = RequestMethod.GET)
    public ResponseEntity<List<FileCharge>> getPhoto(@RequestParam() String loginName) {
        FileCharge charge = new FileCharge();
        SyUser user = userService.findOneByName(loginName);
        if (user.getUserType() == 3) {
            charge.setFileOwner(loginName);
        }
        return JBoyResponse.success(fileChargeService.findBy(charge));
    }

    @RequestMapping(value = LOADFILE + "/{user}")

    public ResponseEntity updatePhoto(HttpServletRequest request,
                                      @RequestParam String apkName,
                                      @RequestParam("myFile") MultipartFile myFile
                                      ){
        Map<String, Object> json = new HashMap<String, Object>();
        try {
            //输出文件后缀名称
            System.out.println(myFile.getOriginalFilename());
            String fileName = myFile.getOriginalFilename();
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //图片名称
            String name = df.format(new Date());

            Random r = new Random();
            for(int i = 0 ;i<3 ;i++){
                name += r.nextInt(10);
            }
            //
            String ext = FilenameUtils.getExtension(myFile.getOriginalFilename());

            //保存图片       File位置 （全路径）   /upload/fileName.jpg
            //String url = "F:\\upload";
            String url = request.getSession().getServletContext().getRealPath("upload/");
            //相对路径
            String path = name + "." + ext;
            File file = new File(url);
            if(!file.exists()){
                file.mkdirs();
            }
            myFile.transferTo(new File(url+path));
            json.put("success", url + path);

            FileCharge charge = new FileCharge();
            charge.setFileName(fileName);
            charge.setFileSaveName(path);
            charge.setFileOwner(apkName);
            fileChargeService.save(charge);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error","上传出错");
            return JBoyResponse.error(CommonErrorCode.FIELD_INVALID,"保存出错");
        }

        return JBoyResponse.success(json) ;

    }
}
