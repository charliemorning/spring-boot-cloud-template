package org.charlie.template.vo;


import lombok.Builder;
import lombok.Data;
import org.charlie.template.bo.FooBO;

@Data
@Builder
public class FooVO {
    private int id;
    private String name;
}
