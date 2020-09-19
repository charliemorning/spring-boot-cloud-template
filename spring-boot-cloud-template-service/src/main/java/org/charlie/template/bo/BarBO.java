package org.charlie.template.bo;


import lombok.Builder;
import lombok.Data;
import org.charlie.template.po.BarPO;

@Data
@Builder
public class BarBO {
    private int barInt;
    private int fooInt;

    public static BarPO toPO (BarBO barBO) {
        return BarPO.builder()
                .barInt(barBO.getBarInt())
                .fooInt(barBO.getFooInt())
                .build();
    }

    public static BarBO fromPO(BarPO barPO) {
        return BarBO.builder()
                .barInt(barPO.getBarInt())
                .fooInt(barPO.getFooInt())
                .build();
    }
}
