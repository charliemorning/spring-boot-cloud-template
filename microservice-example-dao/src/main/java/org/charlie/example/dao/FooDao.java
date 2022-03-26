package org.charlie.example.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.charlie.example.po.FooPo;

import java.util.List;

@Mapper
public interface FooDao extends BaseMapper<FooPo> {
    void insertFoo(FooPo fooPo);
    void updateFoo(FooPo fooPo);
    List<FooPo> selectFoos(FooPo fooPo);
    void deleteFoo(FooPo fooPo);
}
