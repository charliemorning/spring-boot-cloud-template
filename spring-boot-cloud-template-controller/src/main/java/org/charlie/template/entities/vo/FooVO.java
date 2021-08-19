package org.charlie.template.entities.vo;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FooVO {
    private int id;
    private String name;
}
