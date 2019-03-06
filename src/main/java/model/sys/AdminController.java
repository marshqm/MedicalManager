package model.sys;

import model.statics.JBoyException;
import model.statics.JBoyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static model.statics.AdminRoute.LOGIN;

@RestController
public class AdminController {

    @Autowired
    UserService userService;

    @RequestMapping(value = LOGIN, method = RequestMethod.POST)
    public ResponseEntity login(@RequestParam String loginName,
                                @RequestParam String password,
                                 HttpSession session) {
        SyUser user = userService.findOneByName(loginName);
        if(user != null && user.getPassword() != null && user.getPassword().equals(password)) {
            // 设置session
            session.setAttribute("user", loginName);
            return JBoyResponse.success(user);
        }

        throw new JBoyException("账号或密码错误", loginName);
    }



}
