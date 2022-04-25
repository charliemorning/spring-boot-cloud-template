package org.charlie.example.bo.entities;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FooBo extends BaseBo implements Serializable {
    @Value("id")
    private int id;
    @Value("name")
    private String name;
}
