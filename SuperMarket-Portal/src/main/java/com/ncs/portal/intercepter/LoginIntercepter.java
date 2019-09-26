package com.ncs.portal.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ncs.pojo.TbUser;
import com.ncs.portal.service.CartService;
import com.ncs.portal.service.UserService;

public class LoginIntercepter implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;

	@Value("${SSO_BASE_SIT_URL}")
	private String SSO_BASE_SIT_URL;
	@Value("${SSO_USER_LOGIN_SERVICE}")
	private String SSO_USER_LOGIN_SERVICE;
	
	@Override
	// 在Handler（controller）方法执行之前执行
	// 使用场景: 权限认证，身份认证
	// return false:表示拦截，不向下执行
	// return true:放行
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		TbUser user = userService.getUserByToken(request, response);

		// session expire, redis expire , or some error occur
		if (user == null) {
			// user info can't found, so user need login first, redirect to login page
			response.sendRedirect(SSO_BASE_SIT_URL + SSO_USER_LOGIN_SERVICE + "?redirectUrl=" + request.getRequestURL());
			return false;
		}
		
		// put the user into this request scope
		request.setAttribute("user", user);

		// user info is not null, so that user no need login anymore
		return true;
	}

	@Override
	// 在Handler(Controller)方法执行后，返回ModelAndView之前执行
	// 使用场景： 菜单导航，存放公用的模型或试图
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	// 在Handler（controller）方法执行后执行
	// 使用场景： 由于这里我们获取了异常对象，所以可以用于统一的异常处理 还可以用于统一的日志处理
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
