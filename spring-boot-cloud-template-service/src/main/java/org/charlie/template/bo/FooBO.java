package org.charlie.template.bo;


import lombok.Builder;
import lombok.Data;
import org.charlie.template.po.FooPO;

import java.util.Date;

@Data
@Builder
public class FooBO {
    private int fooInt;
    private String fooStr;
    private Date fooDate;

    public static FooPO toPO(FooBO fooBO) {
        return FooPO.builder()
                .fooInt(fooBO.getFooInt())
                .fooStr(fooBO.getFooStr())
                .fooDate(fooBO.getFooDate())
                .build();
    }

    public static FooBO fromPO(FooPO fooPO) {
        return FooBO.builder()
                .fooInt(fooPO.getFooInt())
                .fooStr(fooPO.getFooStr())
                .fooDate(fooPO.getFooDate())
                .build();
    }
}
