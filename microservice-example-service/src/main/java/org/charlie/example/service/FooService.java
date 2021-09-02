package org.charlie.example.service;


import org.charlie.example.bo.FooBO;

import java.util.List;

public interface FooService {
    List<FooBO> queryFoos(FooBO fooBO);
    FooBO createFoo(FooBO fooBO);
    FooBO modifyFoo(FooBO fooBO);
    FooBO removeFoo(FooBO fooBO);
}
