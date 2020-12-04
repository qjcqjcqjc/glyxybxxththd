package com.glyxybxhtxt.controller.hc;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Hc;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.HcService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:wangzh
 * Date: 2020/12/4 15:52
 * Version: 1.0
 */
@RestController
public class HcServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private HcService hs;

    @RequestMapping("/HcServlet")
    @ResponseBody
    ResponseData hcServlet(@RequestParam("op")String op, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(StringUtils.isWhitespace(op) || StringUtils.isEmpty(op) || StringUtils.isBlank(op))
            return new ResponseData("2");
        switch (op){
            case "selhc" : return selhc(request,response);
            case "delhc" : return delhc(request,response);
            case "newhc" : return newhc(request,response);
            case "uphc" : return uphc(request,response);
            default: return new ResponseData(false);
        }
    }

    @ResponseBody
    private ResponseData newhc(HttpServletRequest request,
                                      HttpServletResponse response){
        Hc h = new Hc();
        h.setMc(request.getParameter("mc"));
        h.setDw(request.getParameter("dw"));
        h.setJg(Double.parseDouble(request.getParameter("jg")));
        return hs.newhc(h)==1 ? new ResponseData(true) :new ResponseData("db error");

    }

    @ResponseBody
    private ResponseData uphc(HttpServletRequest request, HttpServletResponse response) {
        Hc h = new Hc();
        h.setId(Integer.parseInt(request.getParameter("id")));
        String mc = request.getParameter("mc");
        if(mc!=null) h.setMc(mc);
        String dw = request.getParameter("dw");
        if(dw!=null) h.setDw(dw);
        String jg = request.getParameter("jg");
        if(jg!=null) h.setJg(Double.parseDouble(jg));
        return hs.uphc(h)==1 ? new ResponseData(true) :new ResponseData("db error");
    }

    private ResponseData delhc(HttpServletRequest request, HttpServletResponse response) {
        return hs.delhc(Integer.parseInt(request.getParameter("id")))==1 ? new ResponseData(true) :new ResponseData("db error");
    }

    private ResponseData selhc(HttpServletRequest request, HttpServletResponse response) {
        Map<String,List> map = new HashMap<>();
        map.put("hlist", hs.selall());
        return new ResponseData(map);
    }
}
