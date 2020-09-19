package org.charlie.template.vo;

import lombok.Builder;
import lombok.Data;
import org.charlie.template.bo.FooBO;

import java.util.Date;

@Data
@Builder
public class FooVO {

    private int fooInt;
    private String fooStr;
    private Date fooDate;

    public static FooVO fromBO(FooBO fooBO) {
        return FooVO.builder()
                .fooInt(fooBO.getFooInt())
                .fooStr(fooBO.getFooStr())
                .fooDate(fooBO.getFooDate())
                .build();
    }

    public static FooBO toBO(FooVO fooVO) {
        return FooBO.builder()
                .fooInt(fooVO.fooInt)
                .fooStr((fooVO.fooStr))
                .fooDate(fooVO.fooDate)
                .build();
    }
}
