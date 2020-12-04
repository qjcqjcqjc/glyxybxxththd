package com.glyxybxhtxt.controller.shy;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Qdb;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.BxdService;
import com.glyxybxhtxt.service.QdbService;
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
 * Date: 2020/12/4 16:19
 * Version: 1.0
 */
@RestController
public class ShyServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private BxdService bs;
    @Autowired
    private QdbService qs;

    @RequestMapping("/ShyServlet")
    @ResponseBody
    ResponseData shyServlet(@RequestParam("op")String op, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(StringUtils.isWhitespace(op) || StringUtils.isEmpty(op) || StringUtils.isBlank(op))
            return new ResponseData("2");
        switch (op){
            case "selbxdbyshy" : return selbxdbyshy(request,response);
            case "upbxdbyshy" : return upbxdbyshy(request,response);
            case "qd" : return qd(request,response);
            case "selqdb" : return selqdb(request,response);
            case "upbxdhcbyshy" : return upbxdhcbyshy(request,response);
            default: return new ResponseData(false);
        }
    }

    private ResponseData upbxdhcbyshy(HttpServletRequest request, HttpServletResponse response) {
        String bid = request.getParameter("bid");
        String hc = request.getParameter("hc");
        String gs = request.getParameter("gs");
        String ybid = request.getParameter("ybid");
        Bxd b = new Bxd();
        if(bid==null||ybid==null){
            return new ResponseData("3");
        }
        b.setId(Integer.parseInt(bid));
        b.setShy1(ybid);
        if(hc!=null)
            b.setHc(hc);
        if(gs!=null)
            b.setGs(gs);
        return bs.upbxdhcbyshy(b) ==1
                ? new ResponseData(true)
                : new ResponseData("该审核员无法修改此报修单") ;
    }

    private ResponseData selqdb(HttpServletRequest request, HttpServletResponse response) {
        Qdb q = new Qdb();
        String num = request.getParameter("num");
        int num2=30;
        if(num!=null){
            num2 = Integer.parseInt(num);
        }
        q.setId(num2);

        String page = request.getParameter("page");
        if(page==null){
            q.setState(0);
        }else{
            int page2 = (Integer.parseInt(page)-1)*num2;
            q.setState(page2);
        }

        q.setShyid(request.getParameter("ybid"));
        List<Qdb> qlist = qs.selallqy(q);
        Map<String,Object> map = new HashMap<>();
        map.put("qlist", qlist);
        return new ResponseData(map);
    }

    private ResponseData qd(HttpServletRequest request, HttpServletResponse response) {
        String ybid = request.getParameter("ybid");
        String xq = request.getParameter("xq");
        if("0".equals(xq))
        {
            xq="临桂校区";
        }else if("1".equals(xq)){
            xq="东城校区";
        }
        Qdb q = new Qdb();
        q.setShyid(ybid);
        q.setXq(xq);
        q.setState(Integer.parseInt(request.getParameter("state")));
        return qs.qd(q) ? new ResponseData(true) : new ResponseData("db error") ;
    }

    private ResponseData upbxdbyshy(HttpServletRequest request, HttpServletResponse response) {
        String shyid = request.getParameter("shyid");
        String bid = request.getParameter("bid");
        String shystate = request.getParameter("shystate");
        if(shyid==null||bid==null||shystate==null){
            return new ResponseData("3");
        }
        int id = Integer.parseInt(bid);
        int state = Integer.parseInt(shystate);
        Bxd t = bs.selbxdforshyid(id);
        Bxd b = new Bxd();
        b.setId(id);
        if(shyid.equals(t.getShy1())){
            b.setShy1state(state);
            bs.upbxd1byshy(b);
        }else if(shyid.equals(t.getShy2())){
            b.setShy2state(state);
            bs.upbxd2byshy(b);
        }else{
            return new ResponseData("审核员id无效");
        }
        return new ResponseData(true);
    }

    private ResponseData selbxdbyshy(HttpServletRequest request, HttpServletResponse response) {
        String ybid = request.getParameter("shyid");
        if(ybid==null){
            return new ResponseData("3");
        }
        Bxd b = new Bxd();
        String state = request.getParameter("shystate");
        String eid = request.getParameter("eid");
        if(state!=null)b.setShy1state(Integer.parseInt(state));
        if(eid!=null)b.setEid(Integer.parseInt(eid));
        b.setShy1(ybid);
        List<Bxd> blist = bs.selbxdbyshy(b);
        Map<String,Object> map = new HashMap<>();
        map.put("blist", blist);
        return new ResponseData(map);
    }
}
