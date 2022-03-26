package org.charlie.example.bo;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FooBo implements Serializable {
    @Value("id")
    private int id;
    @Value("name")
    private String name;
}
