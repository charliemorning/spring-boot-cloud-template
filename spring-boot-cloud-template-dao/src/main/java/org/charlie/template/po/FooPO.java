package org.charlie.template.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FooPO{
    private int fooInt;
    private String fooStr;
    private Date fooDate;
}
