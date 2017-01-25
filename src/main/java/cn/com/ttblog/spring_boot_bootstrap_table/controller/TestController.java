package cn.com.ttblog.spring_boot_bootstrap_table.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping("/test")
@Controller
public class TestController {

	private static final Logger LOG=LoggerFactory.getLogger(TestController.class);

	//退出系统
	@RequestMapping(value = "/err",method = RequestMethod.GET)
	public String exit(HttpSession session,HttpServletRequest request,
			HttpServletResponse response) {
		int i=1/0;
		return "redirect:/index.html";
	}

	//终止服务器
	@RequestMapping("/sysexit")
	public String sysexit(){
		//退出当前jvm，导致容器停止(tomcat关闭等)
		System.exit(-1);
		return "exit";
	}

//	@ExceptionHandler
//	public ModelAndView handleAllException(Exception ex) {
//		ModelAndView mav = new ModelAndView("500");
//		mav.addObject("errMsg", ex.getMessage());
//		return mav;
//	}
}