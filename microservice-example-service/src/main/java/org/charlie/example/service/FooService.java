package org.charlie.example.service;


import org.charlie.example.bo.FooBo;

import java.util.List;

public interface FooService {
    List<FooBo> queryFoos(FooBo fooBO);
    void createFoo(FooBo fooBO);
    void modifyFoo(FooBo fooBO);
    void removeFoo(FooBo fooBO);
}
