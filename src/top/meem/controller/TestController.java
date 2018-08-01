package top.meem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.meem.menu.MenuManager;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;


@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String createMenu(HttpServletRequest request, Model model) {
        System.out.println(request.getRequestURL());

        return "index";
    }
}
