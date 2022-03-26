package org.charlie.example.bo;

import org.charlie.example.po.FooPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FooBoMapper {
    FooBoMapper INSTANCE = Mappers.getMapper(FooBoMapper.class);
    FooPo toPo(FooBo fooBo);
    List<FooPo> toPos(List<FooBo> fooBos);
    FooBo toBo(FooPo fooPo);
    List<FooBo> toBos(List<FooPo> fooPo);
}
