package org.charlie.example.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.charlie.example.po.Foo;

@Mapper
public interface FooDao extends BaseMapper<Foo> {
}
