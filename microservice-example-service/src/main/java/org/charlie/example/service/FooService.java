package org.charlie.example.service;


import org.charlie.example.bo.FooBO;

import java.util.List;

public interface FooService {
    List<FooBO> queryFoos(FooBO fooBO);
    void createFoo(FooBO fooBO);
    void modifyFoo(FooBO fooBO);
    void removeFoo(FooBO fooBO);
}
