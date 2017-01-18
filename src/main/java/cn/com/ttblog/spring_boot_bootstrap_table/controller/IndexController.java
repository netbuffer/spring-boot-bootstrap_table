package cn.com.ttblog.spring_boot_bootstrap_table.controller;

import cn.com.ttblog.spring_boot_bootstrap_table.Constant.ConfigConstant;
import cn.com.ttblog.spring_boot_bootstrap_table.service.IUserService;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.codahale.metrics.annotation.Timed;

/**
 * index
 */
@Controller(value="mainindex")
@RequestMapping("/")
public class IndexController {
	public IndexController(){
		
	}
	@Resource
	private IUserService userService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired  
    private ApplicationContext applicationContext;  

	@RequestMapping(method = RequestMethod.GET)
	public String index(){
		logger.info("index页面");
		return "redirect:index.html";
	}

	@RequestMapping(value = "/welcome",method = RequestMethod.GET)
	public String welcome(Model m){
		logger.info("welcome页面");
		m.addAttribute("user","world");
		return "welcome";
	}

	@RequestMapping("/login")
	public String login(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, String username, String password,@RequestParam(value="requri",required=false) String requri) {
//		RequestContextUtils.getWebApplicationContext(request)
		logger.info("进入username:{},pwd:{},requri:{}", username, password,requri);
		if (username.equals(ConfigConstant.USERNAME)
				&& password.equals("admin")) {
			session.setAttribute(ConfigConstant.ISLOGIN, true);
			session.setAttribute(ConfigConstant.USERNAME, username);
			Cookie c = new Cookie(ConfigConstant.USERNAME, username);
			c.setMaxAge(86400);
			response.addCookie(c);
			Map<String, String> param=new HashMap<String,String>();
			param.put("loginname", username);
			param.put("logintime", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
			param.put("loginip", request.getRemoteAddr());
			if(requri!=null&&requri.length()>0){
				String uri=new String(Base64.decodeBase64(requri));
				String touri=uri.substring(request.getContextPath().length()+1);
				logger.debug("request.getContextPath():{}  decode-requri:{}  touri:{}",request.getContextPath(),uri,touri);
//				/sssbootstrap_table
//				/sssbootstrap_table/test/form?null
				return "redirect:/"+touri;
			}
			return "redirect:/manage.html";
		} else {
			return "redirect:/index.html?requri="+requri;
		}
	}
	
	@RequestMapping("/demo")
	public String demolist() {
		logger.debug("demo");
		return "redirect:/demolist.html";
	}

	@RequestMapping("/exit")
	public String exit(HttpSession session,HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("用户{}退出系统",session.getAttribute(ConfigConstant.USERNAME));
		//删除cookie
		Cookie cookie = new Cookie(ConfigConstant.USERNAME, null); 
		cookie.setMaxAge(0);
		session.invalidate();
		logger.debug("exit c2:{}",cookie);
		response.addCookie(cookie);
		return "redirect:/index.html";
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

	@RequestMapping("teststr")
	public @ResponseBody String teststr() {
		return "this is str";
	}
	
//	@Timed
	@RequestMapping("/datacount")
	public @ResponseBody Map<String, Object> datacount() {
		logger.debug("获取datacount");
		List<Map<String, Object>> counts = userService.getDataSum();
		JSONArray categorys = new JSONArray();
		JSONArray nums = new JSONArray();
		for (Map<String, Object> m : counts) {
			categorys.add(m.get("adddate")==null?"":m.get("adddate").toString());
			nums.add(m.get("num").toString());
		}
		logger.debug("categorys:{},nums:{}", categorys, nums);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("c", categorys);
		data.put("d", nums);
		return data;
	}

	@RequestMapping(value = "/files/{file_name}", method = RequestMethod.GET)
//	@ResponseBody
	public FileSystemResource getFile(@PathVariable("file_name") String fileName,HttpServletRequest request) {
	    return new FileSystemResource(new File(request.getServletContext().getRealPath("export")+ File.separator+fileName+".xls")); 
	}
	
	@RequestMapping("/testerror")
	public String testthrowException() {
		throw new RuntimeException("test error");
	}

	@RequestMapping("/sysexit")
	public String sysexit(){
		//退出当前jvm，导致容器停止(tomcat关闭等)
		System.exit(-1);
		return "exit";
	}

	@ExceptionHandler
	public ModelAndView handleAllException(Exception ex) {
		ModelAndView mav = new ModelAndView("500");
		mav.addObject("errMsg", ex.getMessage());
		return mav;
	}
}