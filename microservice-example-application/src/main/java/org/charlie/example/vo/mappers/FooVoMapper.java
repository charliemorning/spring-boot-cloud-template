package org.charlie.example.vo.mappers;

import org.charlie.example.bo.entities.FooBo;
import org.charlie.example.po.Foo;
import org.charlie.example.po.entities.FooPo;
import org.charlie.example.vo.entities.FooVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FooVoMapper extends TripleConverter<FooVo, FooBo, Foo> {
    FooVoMapper INSTANCE = Mappers.getMapper(FooVoMapper.class);
}
