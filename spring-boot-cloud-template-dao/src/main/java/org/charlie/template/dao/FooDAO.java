package org.charlie.template.dao;


import org.apache.ibatis.annotations.Mapper;
import org.charlie.template.po.FooPO;

import java.util.List;

@Mapper
public interface FooDAO {
    void insertFoo(FooPO fooPO);
    void updateFoo(FooPO fooPO);
    List<FooPO> selectFoos(FooPO fooPO);
    void deleteFoo(FooPO fooPO);
}
