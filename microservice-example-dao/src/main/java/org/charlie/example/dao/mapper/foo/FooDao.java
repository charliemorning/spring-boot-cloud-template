package org.charlie.example.dao.mapper.foo;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.charlie.example.po.foo.Foo;

@Mapper
public interface FooDao extends BaseMapper<Foo> {
}
