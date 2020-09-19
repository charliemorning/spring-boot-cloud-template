package org.charlie.template.dao;

import org.apache.ibatis.annotations.Mapper;
import org.charlie.template.po.FooPO;

import java.util.List;

@Mapper
public interface FooDAO {
    List<FooPO> selectFoos();
    void insertFoo(FooPO fooPO);
    void updateFoo(FooPO fooPO);
    void deleteFoo(FooPO fooDO);
}
