package model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

import static model.statics.AdminRoute.LOGOUT;

@Controller
public class SpringBoot {

    @RequestMapping(value="/login")
    public String toLogin(){
        return "/login";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "/index";
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "/register";
    }

    @RequestMapping(value = LOGOUT, method = RequestMethod.POST)
    public String logout(HttpSession session) {
        //移除session
        session.removeAttribute("user");

        return "/login";
    }

}
