package org.charlie.example.vo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Accessors(chain = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PagedVo<T> {
    int pageIndex;
    int pageSize;
    int total;
    int recordIndex;
    List<T> data;
}
