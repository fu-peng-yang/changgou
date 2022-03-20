package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.domain.User;

import java.util.*;

@Controller
@RequestMapping("test")
public class TestController {
    @GetMapping(value = "hello")
    public String showPage(Model model) {
        List<User>   users = new ArrayList<>();
        users.add(new User(12,"zhang","北京"));
        users.add(new User(13,"han","天津"));
        users.add(new User(14,"jcao","上海"));
        model.addAttribute("uList",users);
        model.addAttribute("message", "hello thymeleaf");
        Map<String,Object> hMap = new HashMap<String, Object>();
        hMap.put("No","000");
        hMap.put("Address","俄罗斯");
        model.addAttribute("dataMap", hMap);
        model.addAttribute("now",new Date());

        model.addAttribute("age",20);

        return "demo";
    }

    public static void main(String[] args) {
        /*HashMap map = new HashMap();
        map.put()*/
    }
}
