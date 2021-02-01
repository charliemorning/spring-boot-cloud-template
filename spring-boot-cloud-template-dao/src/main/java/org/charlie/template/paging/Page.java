package org.charlie.template.paging;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    int total;
    int offset;
    int size;
    List<T> elements;
}
