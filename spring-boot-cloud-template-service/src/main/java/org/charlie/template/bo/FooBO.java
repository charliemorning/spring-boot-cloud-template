package org.charlie.template.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FooBO {
    private int id;
    private String name;
}
