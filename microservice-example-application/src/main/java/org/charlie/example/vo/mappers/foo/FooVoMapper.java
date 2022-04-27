package org.charlie.example.vo.mappers.foo;

import org.charlie.example.bo.entities.FooBo;
import org.charlie.example.po.foo.Foo;
import org.charlie.example.vo.entities.foo.FooVo;
import org.charlie.example.vo.mappers.TripleConverter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FooVoMapper extends TripleConverter<FooVo, FooBo, Foo> {
    FooVoMapper INSTANCE = Mappers.getMapper(FooVoMapper.class);
}
