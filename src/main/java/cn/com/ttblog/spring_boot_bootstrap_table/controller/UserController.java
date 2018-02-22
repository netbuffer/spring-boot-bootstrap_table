package cn.com.ttblog.spring_boot_bootstrap_table.controller;

import cn.com.ttblog.spring_boot_bootstrap_table.constant.ConfigConstant;
import cn.com.ttblog.spring_boot_bootstrap_table.model.User;
import cn.com.ttblog.spring_boot_bootstrap_table.service.IUserService;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

//	@InitBinder
//	public void initBinder(WebDataBinder binder){
//	}

    @RequestMapping(method = RequestMethod.GET)
    public String add(Map m) {
        logger.debug("to get user page");
        m.put("from", "user");
        return "user/add";
    }

    @ModelAttribute
    public void getUser(@RequestParam(value = "id", required = false) String id, Map m) {
        if (StringUtils.isNotBlank(id)) {
            User user = userService.getUserById(id);
            logger.info("modelattribute执行!,查询用户:{},放入map:{}", id, user);
//			m.put("user",user);
            m.put("u", user);//放入的键不是类名小写的话,那么在方法入参上需要加@ModelAttribute("u") User user标记
        }
    }

    //增
    @RequestMapping(method = RequestMethod.POST)
    public String save(@Valid User user) {
        logger.debug("save user:{}", user);
        if (user.getAdddate() == null) {
            user.setAdddate((int) (System.currentTimeMillis() / 1000));
        }
        userService.addUser(user);
        return "redirect:user";
    }

    //删
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Map delete(@RequestParam(value = "id", required = false) String id) {
        Map result = new HashMap(2);
        logger.debug("delete user:{}", id);
        try {
            result.put("success", true);
            userService.deleteById(id);
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", e.getMessage());
            logger.error("delete user出错:{}", id, e);
        }
        return result;
    }

    //改
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Map update(@Valid @ModelAttribute("u") User user, BindingResult br) {
        logger.debug("update user bindiing result:{}", br);
        if (br.getFieldErrorCount() > 0) {
            logger.error("User校验错误:{}", br.getFieldErrors());
        }
        Map result = new HashMap(2);
        logger.debug("update user:{}", user);
        try {
            result.put("success", true);
            userService.addUser(user);
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", e.getMessage());
            logger.error("save user出错:{}", user, e);
        }
        result.put("user", user);
        return result;
    }

    @RequestMapping("/datacount")
    public @ResponseBody
    Map<String, Object> datacount() {
        logger.debug("获取datacount");
        List<Map<String, Object>> counts = userService.getDataSum();
        JSONArray categorys = new JSONArray();
        JSONArray nums = new JSONArray();
        for (Map<String, Object> m : counts) {
            categorys.add(m.get("adddate") == null ? "" : m.get("adddate").toString());
            nums.add(m.get("num").toString());
        }
        logger.debug("categorys:{},nums:{}", categorys, nums);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("c", categorys);
        data.put("d", nums);
        return data;
    }

    @RequestMapping("/newdata")
    public String newdata(HttpSession session, Model model) {
        DecimalFormat df = new DecimalFormat("0.00");
        // Display the total amount of memory in the Java virtual machine.
        long totalMem = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        System.out.println(df.format(totalMem) + " MB");
        // Display the maximum amount of memory that the Java virtual machine
        // will attempt to use.
        long maxMem = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        System.out.println(df.format(maxMem) + " MB");
        // Display the amount of free memory in the Java Virtual Machine.
        long freeMem = Runtime.getRuntime().freeMemory() / 1024 / 1024;
        System.out.println(df.format(freeMem) + " MB");
        logger.info("执行前:{}", model);
        int newcount = userService.getNewData();
        String username = session.getAttribute(ConfigConstant.USERNAME).toString();
        model.addAttribute("newcount", newcount);
        model.addAttribute("username", username);
        logger.info("执行后:{}", model);
        return "newdata";
    }

    @RequestMapping(value = "/photos", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String photos() {
        logger.debug("go to user-photos");
        return "user/photos";
    }

    @RequestMapping("/showUser/{id}")
    public String toIndex(@PathVariable("id") String userId,
                          HttpServletRequest request, Model model) {
        logger.info("进入:{},参数:{}", request.getRequestURI(), model.toString());
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "su";
    }

    @RequestMapping("/testmodel")
    public ModelAndView model() {
        ModelAndView mav = new ModelAndView();
        User u = new User();
        u.setName("tianyu");
        u.setAge(11);
        u.setDeliveryaddress("收货地址");
        mav.addObject("model", u);
        return mav;
    }

    /**
     * 访问pdf视图 http://localhost:8080/sssbootstrap_table/user/userpdfview.pdf
     *
     * @param model
     * @return
     */
    @RequestMapping("/userpdfview")
    public String userpdfview(Model model) {
        model.addAttribute("users", userService.getUserList("desc", 10, 0));
        List<String> columns = new ArrayList<>();
        columns.add("姓名");
        columns.add("性别");
        columns.add("年龄");
        columns.add("手机号");
        model.addAttribute("columns", columns);
        return "userpdfview";
    }

    /**
     * http://localhost:8080/sssbootstrap_table/user/userlist?order=asc&limit=10
     * &offset=0
     *
     * @param order
     * @param limit
     * @param offset
     * @param model
     * @return
     */
    @RequestMapping("/userlist")
    public String userlist(@RequestParam(value = "search", required = false) String search, String order, int limit, int offset, Model model) {
        long startTime = System.nanoTime();
//		logger.info("参数:{},{},{}", order, limit, offset);
        if (search != null) {
            try {
                //get参数乱码问题:http://luanxiyuan.iteye.com/blog/1849169
                search = new String(search.getBytes("ISO-8859-1"), "UTF-8");
                logger.info("查询参数:{}", search);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        List<User> users = search == null ? userService.getUserList(order, limit, offset) : userService.getUserList(search, order, limit, offset);
        long total = search == null ? userService.getUserListCount() : userService.getUserListCount(search);
        model.addAttribute("total", total);
        model.addAttribute("rows", users);
        logger.info("结果:{}", model);
        long estimatedTime = System.nanoTime() - startTime;
        logger.debug("userlist execute with:{}ns", estimatedTime);
        return "userlist";
    }


    @RequestMapping("/showUserXML")
    public ModelAndView showUserXML(HttpServletRequest request, Model model) {
        ModelAndView mav = new ModelAndView("xStreamMarshallingView");
        User user = userService.getUserById(request.getParameter("userId"));
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/u/{id}", headers = "Accept=application/json")
    public @ResponseBody
    User getEmp(@PathVariable String id) {
        User e = userService.getUserById(id);
        return e;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/us", headers = "Accept=application/json")
    public @ResponseBody
    List<User> ListinggetAllEmp(HttpServletRequest request) {
        List<User> us = new ArrayList<User>();
        us.add(userService.getUserById(request.getParameter("id1")));
        us.add(userService.getUserById(request.getParameter("id2")));
        return us;
    }
}