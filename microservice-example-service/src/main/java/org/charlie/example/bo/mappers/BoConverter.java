package org.charlie.example.bo.mappers;


import org.charlie.example.bo.entities.BaseBo;
import org.charlie.example.po.BasePo;

import java.util.List;

public interface BoConverter<B extends BaseBo, P extends BasePo>  {
    P toPo(B o);
    B toBo(P o);
    List<P> toPoList(List<B> boList);
    List<B> toBoList(List<P> poList);
}
