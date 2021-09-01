package org.charlie.example.bo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class FooBO implements Serializable {
    private int id;
    private String name;
}
