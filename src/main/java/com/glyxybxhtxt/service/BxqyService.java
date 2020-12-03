package com.glyxybxhtxt.service;

import com.glyxybxhtxt.dataObject.Bxqy;

import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 20:38
 * Version: 1.0
 */
public interface BxqyService {
    Bxqy selbxqy(int id);
    List<Bxqy> selallqy();
    List<Bxqy> ditu(String xq);
    boolean newqy(Bxqy q);
    boolean upqy(Bxqy q);
}
