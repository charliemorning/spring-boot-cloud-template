package org.charlie.example.service.implement;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.charlie.example.bo.FooBO;
import org.charlie.example.framework.utils.bean.BeanUtil;
import org.charlie.example.dao.FooDAO;
import org.charlie.example.po.FooPO;
import org.charlie.example.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;


@Service
@CacheConfig(cacheNames = "foo-cache")
public class FooServiceImpl implements FooService {

    private FooDAO fooDAO;

    @Autowired
    public void setFooDAO(FooDAO fooDAO) {
        this.fooDAO = fooDAO;
    }


    @Cacheable(key="#fooBO.id", unless = "#fooBo.id = 1")
    @Override
    public List<FooBO> queryFoos(FooBO fooBO) {
        FooPO fooPO = FooPO.builder().build();
        if (Objects.isNull(fooBO)) {
            fooPO = null;
        } else {
            BeanUtil.copy(fooBO, fooPO);
        }


        return Lists.transform(fooDAO.selectFoos(fooPO
        ), new Function<FooPO, FooBO>() {
            @Nullable
            @Override
            public FooBO apply(@Nullable FooPO fooPO) {
                FooBO fooBO = FooBO.builder().build();
                BeanUtil.copy(fooPO, fooBO);
                return fooBO;
            }
        });

    }

    @Override
    @CachePut(key="#fooBO.id")
    public void createFoo(FooBO fooBO) {
        FooPO fooPO = FooPO.builder().build();
        BeanUtil.copy(fooBO, fooPO);
        fooDAO.insertFoo(fooPO);
    }

    @Override
    @CachePut(key="#fooBO.id")
    public void modifyFoo(FooBO fooBO) {
        FooPO fooPO = FooPO.builder().build();
        BeanUtil.copy(fooBO, fooPO);
        fooDAO.updateFoo(fooPO);
    }

    @Override
    @CacheEvict(key="#fooBO.id")
    public void removeFoo(FooBO fooBO) {
        FooPO fooPO = FooPO.builder().build();
        BeanUtil.copy(fooBO, fooPO);
        fooDAO.deleteFoo(fooPO);
    }
}
