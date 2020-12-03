package com.glyxybxhtxt.service.impl;

import com.glyxybxhtxt.dao.AdminMapper;
import com.glyxybxhtxt.dataObject.Admin;
import com.glyxybxhtxt.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author:wangzh
 * Date: 2020/12/3 20:08
 * Version: 1.0
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper mapper;

    @Override
    public Admin login(Admin a) {
        return mapper.login(a);
    }
}
