package org.charlie.example.bo.mappers;


import org.charlie.example.bo.entities.FooBo;
import org.charlie.example.po.Foo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FooBoConverter extends BoConverter<FooBo, Foo> {
    FooBoConverter INSTANCE = Mappers.getMapper(FooBoConverter.class);
}
