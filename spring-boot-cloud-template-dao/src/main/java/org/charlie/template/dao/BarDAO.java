package org.charlie.template.dao;

import org.apache.ibatis.annotations.Mapper;
import org.charlie.template.po.BarPO;

import java.util.List;

@Mapper
public interface BarDAO {
    List<BarPO> selectBars();
    void insertBar(BarPO barPO);
    void updateBar(BarPO barPO);
    void deleteBar(BarPO barPO);
}
