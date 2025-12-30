package edu.hpu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.hpu.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // MyBatis-Plus 已经帮你写好了增删改查，这里留空即可
}