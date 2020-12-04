package com.glyxybxhtxt.controller.jdr;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.BxdService;
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
 * Date: 2020/12/4 16:01
 * Version: 1.0
 */
@RestController
public class JdrServlet{
    private static final long serialVersionUID = 1L;
    @Autowired
    private BxdService bs;

    @RequestMapping("/JdrServlet")
    @ResponseBody
    ResponseData jdrServlet(@RequestParam("op")String op, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(StringUtils.isWhitespace(op) || StringUtils.isEmpty(op) || StringUtils.isBlank(op))
            return new ResponseData("2");
        switch (op){
            case "selbxdbyjdr" : return selbxdbyjdr(request,response);
            case "upbxdbyjdr" : return upbxdbyjdr(request,response);
            case "selgs" : return selgs(request,response);
            default: return new ResponseData(false);
        }
    }

    @ResponseBody
    private ResponseData selgs(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<>();
        String jid = request.getParameter("jid");
        Double gs = bs.selgs(jid);
        if(gs==null||gs==0.0){
            map.put("gs", 0);
        }
        else{
            map.put("gs", gs);
        }
        return new ResponseData(map);
    }

    @ResponseBody
    private ResponseData upbxdbyjdr(HttpServletRequest request, HttpServletResponse response) {
        ResponseData responseData = null;
        String jid = request.getParameter("jid");
        String bid = request.getParameter("bid");
        if(jid==null||bid==null){
            return new ResponseData("3");
        }
        Bxd b = new Bxd();
        b.setJid(jid);
        b.setId(Integer.parseInt(bid));
        String state = request.getParameter("state");
        if(state!=null){
            b.setState(Integer.parseInt(state));
            bs.upbxdbyjdr(b);
            responseData =new ResponseData("success","修改维修状态成功");
        }else{
            Bxd t = bs.selbxdforshyid(b.getId());
            if(t==null||t.getId()==null){
                responseData = new ResponseData("bidfalse");
            }
            if(!(0==t.getShy1state()||0==t.getShy2state())){
                responseData = new ResponseData("耗材及工时已审核，无法修改");
            }else{
                b.setHc(request.getParameter("hc"));
                b.setGs(request.getParameter("gs"));
                bs.upbxdbyjdr(b);
                responseData = new ResponseData("success","设置耗材、工时成功");
            }
        }
        return responseData;
    }

    @ResponseBody
    private ResponseData selbxdbyjdr(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<>();
        String jid = request.getParameter("jid");
        if(jid==null){
            return new ResponseData("3");
        }
        Bxd b = new Bxd();
        b.setJid(jid);
        String state = request.getParameter("state");
        String eid = request.getParameter("eid");
        if(eid!=null)b.setEid(Integer.parseInt(eid));
        if(state!=null)b.setState(Integer.parseInt(state));
        List<Bxd> blist = bs.selbxdbyjdr(b);
        map.put("blist",blist);
        return new ResponseData(map);
    }
}
