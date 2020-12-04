package com.glyxybxhtxt.controller.bxd;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Ewm;
import com.glyxybxhtxt.response.ResponseData;
import com.glyxybxhtxt.service.BxdService;
import com.glyxybxhtxt.service.EwmService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:wangzh
 * Date: 2020/12/4 14:54
 * Version: 1.0
 */
@RestController
public class BxdServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String PATH_FOLDER = "/";
    @Autowired
    private BxdService bs;
    @Autowired
    private EwmService es;


    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletCtx = config.getServletContext();
        PATH_FOLDER = servletCtx.getRealPath("/bxdimg");
    }

    @RequestMapping("/BxdServlet")
    @ResponseBody
    ResponseData bxdServlet(@RequestParam("op")String op, HttpServletRequest request,HttpServletResponse response) throws IOException {
        if(StringUtils.isWhitespace(op) || StringUtils.isEmpty(op) || StringUtils.isBlank(op))
            return new ResponseData("2");
        switch (op){
            case "sbrbxd" : return sbr(request,response);
            case "upbxdbysbr" : return upbxdbysbr(request,response);
            case "newbxdbysbr" : return filebase64(request,response);
            case "selqybysbr" : return selqybysbr(request,response);
            case "selbxdforeid" : return selbxdforeid(request,response);
            default: return new ResponseData(false);
        }
    }

    @ResponseBody
    private ResponseData selbxdforeid(HttpServletRequest request,
                                      HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        int eid = Integer.parseInt(request.getParameter("eid"));
        List<Bxd> blist = bs.selbxdforeid(eid);
        map.put("blist",blist);
        return new ResponseData(map);
    }

    @ResponseBody
    private ResponseData selqybysbr(HttpServletRequest request,
                                      HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        Ewm e = es.selqybysbr(Integer.parseInt(request.getParameter("eid")));
        map.put("ewm",e);
        return new ResponseData(map);
    }

    @ResponseBody
    private ResponseData filebase64(HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        String eid = request.getParameter("eid");
        String xxdd = request.getParameter("xxdd");
        String yysj = request.getParameter("yysj");
        String bxlb = request.getParameter("bxlb");
        String bxnr = request.getParameter("bxnr");
        String sbrsj = request.getParameter("sbrsj");
        String sbrxh = request.getParameter("sbrxh");
        String sbr = request.getParameter("sbr");



        Bxd bxd = new Bxd();

        String filename = "";
        String tp = request.getParameter("tp");
        if(tp!=null&&tp.length()!=0){
            tp = "{\"tp\":"+ tp + "}";
            JSONObject jsontp = JSONObject.fromObject(tp);
            JSONArray array = jsontp.getJSONArray("tp");
            int j = array.size();
            for(int k = 0;k<j;k++){
                JSONObject temp = array.getJSONObject(k);

                String base64 = temp.getString("base64");
                String[] ss = base64.split("\\,");
                String img64 = ss[1];

                Base64 decoder = new Base64();
                byte[] b = decoder.decode(img64);
                for(int i=0;i<b.length;++i)
                {
                    if(b[i]<0)
                    {
                        b[i]+=256;
                    }
                }

                Date d = new Date();
                String time = String.valueOf(d.getTime())+k;
                String fname = PATH_FOLDER+"/"+time+"."+temp.getString("hz");
                filename = filename + "|" + time+"."+temp.getString("hz");
                OutputStream out = new FileOutputStream(fname);
                out.write(b);
                out.flush();
                out.close();
            }
            bxd.setTp(filename);
        }
        bxd.setEid(Integer.parseInt(eid));
        bxd.setXxdd(xxdd);
        bxd.setYysj(yysj);
        bxd.setBxlb(bxlb);
        bxd.setBxnr(bxnr);
        bxd.setSbr(sbr);
        bxd.setSbrsj(sbrsj);
        bxd.setSbrxh(sbrxh);

        return bs.newbxdbysbr(bxd) == 1
                ? new ResponseData(true)
                    : new ResponseData(false);

    }


    @ResponseBody
    private ResponseData upbxdbysbr(HttpServletRequest request,
                                    HttpServletResponse response){
        String cxsy = request.getParameter("cxsy");
        String pj = request.getParameter("pj");
        String pjnr = request.getParameter("pjnr");
        String pjzj = request.getParameter("pjzj");
        String xh = request.getParameter("xh");
        String bid = request.getParameter("bid");
        Bxd b = new Bxd();
        b.setSbrxh(xh);
        b.setId(Integer.parseInt(bid));
        b.setPj(pj);
        b.setPjnr(pjnr);
        b.setPjzj(pjzj);
        b.setCxsy(cxsy);
        bs.upbxdbysbr(b);
        return new ResponseData(true);
    }

    @ResponseBody
    private ResponseData sbr(HttpServletRequest request,
                                    HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        String eid = request.getParameter("eid");
        Bxd b = new Bxd();
        b.setSbrxh(request.getParameter("xh"));
        if(eid!=null){
            b.setEid(Integer.parseInt(eid));
        }
        map.put("blist",bs.selforsbr(b));
        return new ResponseData(map);
    }

}
