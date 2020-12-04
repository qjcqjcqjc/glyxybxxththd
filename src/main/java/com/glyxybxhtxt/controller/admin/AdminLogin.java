package com.glyxybxhtxt.controller.admin;

import com.glyxybxhtxt.dataObject.Admin;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author:wangzh
 * Date: 2020/12/4 17:26
 * Version: 1.0
 */
@RestController
public class AdminLogin {
    private static final long serialVersionUID = 1L;
    @Autowired
    private AdminService as;

    @RequestMapping("/AdminLogin")
    @ResponseBody
    ResponseData adminLogin(HttpServletRequest request, HttpServletResponse response){
        String account = request.getParameter("account");
        String password	= request.getParameter("password");
        Admin s = new Admin();
        s.setAccount(account);
        s.setPassword(password);
        Admin temp = as.login(s);
        if(temp==null || temp.getId()==null){
            return new ResponseData(false);
        }else{
            request.getSession().setAttribute("admin", s);
            return new ResponseData(true);
        }
    }
}
