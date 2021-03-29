package org.charlie.template.paging;

import lombok.Data;

import java.util.List;

@Data
public class Paging<PO> {
    int total;
    int offset;
    int size;
    List<PO> elements;
}
