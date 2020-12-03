package com.glyxybxhtxt.service;

import com.glyxybxhtxt.dataObject.Shy;

import java.util.List;

/**
 * Author:wangzh
 * Date: 2020/12/3 21:06
 * Version: 1.0
 */
public interface ShyService {
    List<Shy> selallqy();
    void del(String ybid);
    void newshy(Shy s);
    void UPshy(Shy s);
}
