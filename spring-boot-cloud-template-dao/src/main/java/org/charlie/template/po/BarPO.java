package org.charlie.template.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Entity
//@Table(name = "bar")
public class BarPO {
//    @Id
//    @GeneratedValue
    private int barInt;
    private int fooInt;
}
