package org.charlie.example.po;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Table;

@Data
@Builder
@Table(name = "foo")
public class FooPO {
    private int id;
    private String name;
}
