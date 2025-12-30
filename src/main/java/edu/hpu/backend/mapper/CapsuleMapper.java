package edu.hpu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.hpu.backend.entity.Capsule; // 假设你有这个Entity
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CapsuleMapper extends BaseMapper<Capsule> {
}