package model.sys;

import com.fasterxml.jackson.databind.node.ObjectNode;
import model.base.JSON;
import model.statics.EnumUtil;
import model.statics.JBoyResponse;
import model.statics.SpringAppMessage;
import model.statics.Valuable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static model.statics.AdminRoute.SELFINFO;
import static model.statics.AdminRoute.SYSTEM_TOOL;
import static model.statics.AdminRoute.SYSUSER;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    SpringAppMessage appMessage;

    @RequestMapping(value = SYSUSER,method = RequestMethod.GET)
    public ResponseEntity<List<SyUser>> findAllUsers(SyUser user) {
        return JBoyResponse.success(SyUser.findBy(user).findList());
    }
    @RequestMapping(value = SYSUSER + "/save", method = RequestMethod.POST)
    public ResponseEntity<SyUser> saveUsers(@RequestBody SyUser user) {

        return JBoyResponse.success( userService.saveUsers(user));
    }
    @RequestMapping(value = SYSUSER + "/{id}", method = RequestMethod.GET)
    public ResponseEntity<SyUser> findOneBy(@PathVariable Integer id) {

        return JBoyResponse.success( userService.findOneBy(id));
    }
  @RequestMapping(value = SYSUSER + "/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable Integer id) {
      userService.deleteBy(id);
        return JBoyResponse.success( ).build();
    }

    @RequestMapping(value = SELFINFO , method = RequestMethod.GET)
    public ResponseEntity<SyUser> findOneBy(@SessionAttribute("user") String loginName) {

        return JBoyResponse.success( userService.findOneByName(loginName));
    }

    @RequestMapping(path = "/login/user",method = RequestMethod.GET)
    public ResponseEntity forIndex(@SessionAttribute("user") String loginName, Model model) {
        model.addAttribute("loginUser", loginName);
        SyUser user = userService.findOneByName(loginName);
        model.addAttribute("userType",user.getUserType());
        return JBoyResponse.success(model);
    }

    @RequestMapping(path = SYSTEM_TOOL + "/constant", method = RequestMethod.GET)
    public ResponseEntity constantDefine() {
        Set<Class<? extends Valuable>> list = EnumUtil.getRegisterClass();

        ObjectNode obj = JSON.node();
        for (Class<? extends Valuable> clazz : list) {
            String name = clazz.getSimpleName();
            ObjectNode vNode = JSON.node();
            Map<String, Valuable> map = EnumUtil.get(clazz);
            for (Map.Entry<String, Valuable> entry : map.entrySet()) {
                // JB.CommonState.A
                String value = appMessage.getMessage("JB." + name + "." + entry.getKey(), null, null);
                vNode.put(entry.getKey(), value == null ? ((Enum) entry.getValue()).name() : value);
            }
            obj.putPOJO(name, vNode);
        }

        return JBoyResponse.success(obj);
    }

}
