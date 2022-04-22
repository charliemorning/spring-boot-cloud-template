package org.charlie.example.service;


import org.charlie.example.bo.entities.FooBo;

import java.util.List;

public interface FooService {
    List<FooBo> queryFoos(FooBo fooBO);
    FooBo createFoo(FooBo fooBO);
    FooBo modifyFoo(FooBo fooBO);
    FooBo removeFoo(FooBo fooBO);
}
