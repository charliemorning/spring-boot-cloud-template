package org.charlie.example.service.api;


import com.baomidou.mybatisplus.extension.service.IService;
import org.charlie.example.bo.entities.FooBo;
import org.charlie.example.po.Foo;

import java.util.List;


public interface FooService extends IService<Foo> {

    List<FooBo> selectWithCache(FooBo fooBO);

    boolean updateWithCache(FooBo fooBo);

    boolean deleteWithCache(FooBo fooBo);
}
