package cn.netbuffer.spring_boot_bootstrap_table.controller;

import cn.netbuffer.spring_boot_bootstrap_table.constant.ConfigConstant;
import cn.netbuffer.spring_boot_bootstrap_table.service.IUserService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller(value = "mainindex")
@RequestMapping("/")
public class IndexController {
    public IndexController() {

    }

    @Resource
    private IUserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        logger.info("index页面");
        return "redirect:index.html";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(Model m) {
        logger.info("welcome页面");
        m.addAttribute("user", "world");
        return "welcome";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error() {
        logger.info("500页面");
        return "500";
    }

    //打开登录
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "requri", required = false) String requri) {
        return "redirect:/index.html?requri=" + requri;
    }

    //登录处理
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, HttpServletRequest request,
                        HttpServletResponse response, String username, String password, @RequestParam(value = "requri", required = false) String requri) {
//		RequestContextUtils.getWebApplicationContext(request)
        logger.info("进入username:{},pwd:{},requri:{}", username, password, requri);
        if (username.equals(ConfigConstant.USERNAME)
                && password.equals("admin")) {
            session.setAttribute(ConfigConstant.ISLOGIN, true);
            session.setAttribute(ConfigConstant.USERNAME, username);
            Cookie c = new Cookie(ConfigConstant.USERNAME, username);
            c.setMaxAge(86400);
            response.addCookie(c);
            Map<String, String> param = new HashMap<String, String>();
            param.put("loginname", username);
            param.put("logintime", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            param.put("loginip", request.getRemoteAddr());
            if (requri != null && requri.length() > 0) {
                String uri = new String(Base64.decodeBase64(requri));
                String touri = uri.substring(request.getContextPath().length() + 1);
                logger.debug("request.getContextPath():{}  decode-requri:{}  touri:{}", request.getContextPath(), uri, touri);
                if (StringUtils.isNotBlank(touri) && !touri.equals("/")) {
                    return "redirect:/" + touri;
                }
            }
            return "redirect:/manage.html";
        } else {
            return "redirect:/index.html?requri=" + requri;
        }
    }

    //demo页面
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String demolist() {
        logger.debug("demo");
        return "redirect:/demolist.html";
    }

    //退出系统
    @RequestMapping(value = "/exit", method = RequestMethod.GET)
    public String exit(HttpSession session, HttpServletRequest request,
                       HttpServletResponse response) {
        logger.debug("用户{}退出系统", session.getAttribute(ConfigConstant.USERNAME));
        //删除cookie
        Cookie cookie = new Cookie(ConfigConstant.USERNAME, null);
        cookie.setMaxAge(0);
        session.invalidate();
        logger.debug("exit c2:{}", cookie);
        response.addCookie(cookie);
        return "redirect:/index.html";
    }

    //终止服务器
    @RequestMapping("/sysexit")
    public String sysexit() {
        //退出当前jvm，导致容器停止(tomcat关闭等)
        System.exit(-1);
        return "exit";
    }

    @ExceptionHandler
    public ModelAndView handleAllException(Exception ex) {
        logger.error("backend error:", ex);
        ModelAndView mav = new ModelAndView("500");
        mav.addObject("errMsg", ex.getMessage());
        return mav;
    }
}