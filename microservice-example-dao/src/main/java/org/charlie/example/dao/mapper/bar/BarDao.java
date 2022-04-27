package org.charlie.example.dao.mapper.bar;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.charlie.example.po.bar.Bar;

@Mapper
public interface BarDao extends BaseMapper<Bar> {
}
