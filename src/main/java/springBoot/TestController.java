package springBoot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

   /* @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }
*/
    @RequestMapping(value="hello")
    public String hello(){
        return "hello";
    }

}
