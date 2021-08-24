package org.charlie.example.dao;


import org.apache.ibatis.annotations.Mapper;
import org.charlie.example.po.FooPO;

import java.util.List;

@Mapper
public interface FooDAO {
    void insertFoo(FooPO fooPO);
    void updateFoo(FooPO fooPO);
    List<FooPO> selectFoos(FooPO fooPO);
    void deleteFoo(FooPO fooPO);
}
