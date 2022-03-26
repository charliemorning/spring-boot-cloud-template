package org.charlie.example.entities.vo;

import org.charlie.example.bo.FooBo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FooVoMapper {
    FooVoMapper INSTANCE = Mappers.getMapper(FooVoMapper.class);
    FooBo getBo(FooVo vo);
    FooVo getVo(FooBo fooBo);
}
