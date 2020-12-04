package com.glyxybxhtxt.controller.admin;

import com.glyxybxhtxt.dataObject.*;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author:wangzh
 * Date: 2020/12/4 17:29
 * Version: 1.0
 */
@RestController
public class AdminServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private BxdService bs;
    @Autowired
    private BxqyService qs;
    @Autowired
    private JdrService js;
    @Autowired
    private ShyService ss;
    @Autowired
    private QdbService qdbs;
    @Autowired
    private EwmService es;

    @RequestMapping("/AdminServlet")
    @ResponseBody
    ResponseData adminServlet(@RequestParam("op")String op, HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        if(StringUtils.isWhitespace(op) || StringUtils.isEmpty(op) || StringUtils.isBlank(op))
            return new ResponseData("2");
        switch (op){
            case "selbxdbyadmin" : return selbxdbyadmin(request,response);
            case "selallqy" : return selallqy(request,response);
            case "selalljdr" : return selalljdr(request,response);
//            case "selallshy" : return selallshy(request,response);
            case "selqdb" : return selqdb(request,response);
            case "upbxdbyadmin" : return upbxdbyadmin(request,response);
            case "newpeople" : return newpeople(request,response);
            case "uppeople" : return uppeople(request,response);
            case "selewm" : return selewm(request,response);
            case "newqy" : return newqy(request,response);
            case "newewm" : return newewm(request,response);
            case "upqy" : return upqy(request,response);
            case "upewm" : return upewm(request,response);
            case "bxnum" : return bxnum(request,response);
            case "adminindex" : return adminindex(request,response);
            default: return new ResponseData(false);
        }
    }

    private ResponseData adminindex(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<>();
        int zbxd = bs.allcount();
        int zwxd = bs.selnumforstate(2);
        int zzwx = bs.selnumforstate(1);
        int zcxd = bs.selnumforstate(3);
        map.put("tj", bs.tj());
        map.put("zbxd", zbxd);
        map.put("zwxd", zwxd);
        map.put("zcxd", zcxd);
        map.put("zzwx", zzwx);
        map.put("pj1", bs.selnumforpj(1));
        map.put("pj2", bs.selnumforpj(2));
        map.put("pj3", bs.selnumforpj(3));
        map.put("pj4", bs.selnumforpj(4));
        map.put("pj5", bs.selnumforpj(5));
        return new ResponseData(map);
    }

    private ResponseData bxnum(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<>();
        int state = Integer.parseInt(request.getParameter("state"));
        int count = bs.selnumforstate(state);
        map.put("status", "success");
        map.put("count", count);
        return new ResponseData(map);
    }

    private ResponseData newewm(HttpServletRequest request, HttpServletResponse response) {
        String qid = request.getParameter("qid");
        String xxdd = request.getParameter("xxdd");
        Ewm e = new Ewm();
        e.setQid(Integer.parseInt(qid));
        e.setXxdd(xxdd);
        return es.newewm(e) ? new ResponseData(true) : new ResponseData(false);
    }

    private ResponseData newqy(HttpServletRequest request, HttpServletResponse response) {
        String qy = request.getParameter("qy");
        String qylb = request.getParameter("qylb");
        String xq = request.getParameter("xq");
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        if("0".equals(xq))
        {
            xq="临桂校区";
        }else if("1".equals(xq)){
            xq="东城校区";
        }
        Bxqy q = new Bxqy();
        q.setQy(qy);
        q.setQylb(qylb);
        q.setXq(xq);
        q.setX(x);
        q.setY(y);
        return qs.newqy(q) ? new ResponseData(true) : new ResponseData(false);
    }
    private ResponseData upewm(HttpServletRequest request, HttpServletResponse response) {
        String eid = request.getParameter("eid");
        String qid = request.getParameter("qid");
        String xxdd = request.getParameter("xxdd");
        Ewm e = new Ewm();
        e.setId(Integer.parseInt(eid));
        e.setQid(Integer.parseInt(qid));
        e.setXxdd(xxdd);
        return es.upewm(e)? new ResponseData(true) : new ResponseData(false);
    }

    private ResponseData upqy(HttpServletRequest request, HttpServletResponse response) {
        String qid = request.getParameter("qid");
        String qy = request.getParameter("qy");
        String qylb = request.getParameter("qylb");
        String xq = request.getParameter("xq");
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        if("0".equals(xq))
        {
            xq="临桂校区";
        }else if("1".equals(xq)){
            xq="东城校区";
        }
        Bxqy q = new Bxqy();
        q.setId(Integer.parseInt(qid));
        q.setQy(qy);
        q.setQylb(qylb);
        q.setXq(xq);
        q.setX(x);
        q.setY(y);
        return qs.upqy(q)? new ResponseData(true) : new ResponseData(false);
    }

    private ResponseData selewm(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        int qid = Integer.parseInt(request.getParameter("qid"));
        map.put("ewmlist", es.selewm(qid));
        return new ResponseData(map);
    }

    private ResponseData uppeople(HttpServletRequest request, HttpServletResponse response) {
        ResponseData responseData = null;
        String jid = request.getParameter("jid");
        String xm = request.getParameter("xm");
        if(jid==null){
            String shyid = request.getParameter("shyid");
            if(shyid==null){
                return new ResponseData("3");
            }
            String del = request.getParameter("del");
            if("1".equals(del)){
                ss.del(shyid);
                return new ResponseData("success","审核员删除成功");
            }
            String zw = request.getParameter("zw");
            Shy s = new Shy();
            s.setYbid(shyid);
            s.setXm(xm);
            if(zw!=null)s.setZw(Integer.parseInt(zw));
            ss.UPshy(s);
            responseData = new ResponseData("success","审核员修改成功");
        }else{
            String del = request.getParameter("del");
            if("1".equals(del)){
                js.del(jid);
                return new ResponseData("success","接单人删除成功");
            }
            Jdr j = new Jdr();
            String sj = request.getParameter("sj");
            String yx = request.getParameter("yx");
            String ywfw = request.getParameter("ywfw");
            String state = request.getParameter("state");///
            j.setXm(xm);
            j.setYbid(jid);
            j.setSj(sj);
            j.setYx(yx);
            j.setState(state);
            j.setYwfw(ywfw);
            js.upjdr(j);
            responseData = new ResponseData("success","接单人修改成功");
        }
        return responseData;
    }

    private ResponseData newpeople(HttpServletRequest request, HttpServletResponse response) {
        ResponseData responseData = null;
        String y = request.getParameter("ywfw");
        String ybid = request.getParameter("ybid");
        String gh = request.getParameter("gh");
        String xm = request.getParameter("xm");
        if(ybid==null||gh==null||xm==null){
            return new ResponseData("3");
        }
        if(y==null){
            String zw = request.getParameter("zw");
            Shy s = new Shy();
            if(zw==null){
                return new ResponseData("3");
            }
            s.setZw(Integer.parseInt(zw));
            s.setGh(gh);
            s.setXm(xm);
            s.setYbid(ybid);
            ss.newshy(s);
            responseData = new ResponseData("success","审核员添加成功");
        }else{
            Jdr j = new Jdr();
            j.setGh(gh);
            j.setXm(xm);
            j.setYbid(ybid);
            j.setSj(request.getParameter("sj"));
            j.setYx(request.getParameter("yx"));
            j.setYwfw(y);
            js.newjdr(j);
            responseData = new ResponseData("success","接单人添加成功");
        }
        return responseData;
    }

    private ResponseData upbxdbyadmin(HttpServletRequest request, HttpServletResponse response) {
        ResponseData responseData = null;
        Bxd b = new Bxd();
        String del = request.getParameter("del");
        String bid = request.getParameter("bid");
        if(bid==null){
            return new ResponseData("3");
        }
        Integer id = Integer.parseInt(bid);
        b.setId(id);
        if("1".equals(del)){
            bs.del(id);
            responseData =  new ResponseData("success","删除成功");
        }else{
            b.setJid(request.getParameter("jid"));
            b.setShy1(request.getParameter("shy1"));
            b.setShy2(request.getParameter("shy2"));
            b.setPj(request.getParameter("pj"));
            b.setPjnr(request.getParameter("pjnr"));
            b.setHc(request.getParameter("hc"));
            b.setGs(request.getParameter("gs"));
            bs.upbxdbyadmin(b);
            responseData =  new ResponseData("success","修改成功");
        }
        return responseData;
    }

    private ResponseData selqdb(HttpServletRequest request, HttpServletResponse response) {
        String shyid = request.getParameter("shyid");
        if(shyid==null){
            return new ResponseData("3");
        }
        String num = request.getParameter("num");
        Qdb q = new Qdb();
        if(num!=null){
            q.setId(Integer.parseInt(num));
        }
        q.setShyid(shyid);
        Map<String, Object> map = new HashMap<>();
        map.put("qdblist", qdbs.selallqy(q));
        return new ResponseData(map);
    }

    private ResponseData selalljdr(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<>();
        map.put("jlist", js.selalljdr(request.getParameter("state")));
        return new ResponseData(map);
    }

    private ResponseData selallqy(HttpServletRequest request, HttpServletResponse response) {
        String xq = request.getParameter("xq");
        if(xq==null){
            Map<String,Object> map = new HashMap<>();
            map.put("qylist", qs.selallqy());
            return new ResponseData(map);
        }
        else{
            Map<String,Object> map = new HashMap<>();
            if("0".equals(xq))
            {
                xq="临桂校区";
            }else if("1".equals(xq)){
                xq="东城校区";
            }
            List<Bxqy> qylist = qs.ditu(xq);
            for(int i=0;i<qylist.size();i++){
                List<Bxd> b = qylist.get(i).getB();
                if(b==null){
                    qylist.get(i).setCountb(0);
                }else{
                    qylist.get(i).setCountb(b.size());
                }
            }
            map.put("qylist", qylist);
            return new ResponseData(map);
        }
    }

    private ResponseData selbxdbyadmin(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String bid = request.getParameter("bid");//id
        String startime = request.getParameter("startime");//sbsj代替
        String endtime = request.getParameter("endtime");//wxsj代替
        String xq = request.getParameter("xq"); //bxlb代替
        String qy = request.getParameter("qid"); //bxnr代替
        String jdr = request.getParameter("jid");//jid
        String state = request.getParameter("state");//state
        String pj = request.getParameter("pj");//pj
        Bxd b = new Bxd();
        if(bid!=null)b.setId(Integer.parseInt(bid));
        Date star = null;
        Date end = null;
        if(startime!=null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            star = format.parse(startime);
            Calendar cal = new GregorianCalendar();
            cal.setTime(star);
            star = cal.getTime();
        }
        if(endtime!=null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            end = format.parse(endtime);
            Calendar cal = new GregorianCalendar();
            cal.setTime(end);
            cal.add(cal.DATE, 1);
            end = cal.getTime();
        }
        b.setSbsj(star);
        b.setWxsj(end);
        b.setBxlb(xq);
        b.setBxnr(qy);
        b.setJid(jdr);
        if(state!=null)b.setState(Integer.parseInt(state));
        b.setPj(pj);
        List<Bxd> blist = bs.selbxdbyadmin(b);
        Map<String,Object> map = new HashMap<>();
        map.put("blist", blist);
        return new ResponseData(map);
    }
}
