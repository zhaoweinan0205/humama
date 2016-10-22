package com.humama.sso.service;

import com.humama.sso.mapper.UserMapper;
import com.humama.sso.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/10/18 .
 * @Version: 1.0 .
 */
@Service
public class UserService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserMapper userMapper;

    public Boolean check(String param, Integer type) {
        if (type < 1 || type > 3) {
            return null;
        }
        User record = new User();
        switch (type) {
            case 1:
                record.setUsername(param);
                break;
            case 2:
                record.setPhone(param);
                break;
            case 3:
                record.setEmail(param);
                break;
            default:
                break;
        }
        return this.userMapper.selectOne(record) == null;
    }

    public Boolean saveUser(User user) {
        user.setId(null);
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());

        // 密码通过MD5进行加密处理
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return this.userMapper.insert(user) == 1;
    }
}
