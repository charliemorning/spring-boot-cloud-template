package org.charlie.example.dao;


import org.apache.ibatis.annotations.Mapper;
import org.charlie.example.po.entities.FooPo;

import java.util.List;

@Mapper
public interface FooDao {
    void insertFoo(FooPo fooPO);
    void updateFoo(FooPo fooPO);
    List<FooPo> selectFoos(FooPo fooPO);
    void deleteFoo(FooPo fooPO);
}
