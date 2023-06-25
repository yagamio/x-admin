package com.hb.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hb.sys.entity.User;
import com.hb.sys.mapper.UserMapper;
import com.hb.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cheng
 * @since 2023-06-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Map<String, Object> login(User user) {
        // 根据用户名和密码查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        wrapper.eq(User::getPassword, user.getPassword());
        User loginUser = this.baseMapper.selectOne(wrapper);

        // 结果不为空，则生成token，并将用户信息存入redis
        if (loginUser != null) {
            // 暂时用uuid 终极方案是jwt
            String key = "user:" + UUID.randomUUID();

            // 存入redis

            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", key);
            return data;
        }

        return null;
    }
}
