package org.charlie.template.service;


import org.charlie.template.bo.FooBO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FooService {
    List<FooBO> queryFoos();
    boolean addFoo(FooBO fooBO);
    void modifyFoo(FooBO fooBO);
    void removeFoo(FooBO fooBO);
}
