package org.charlie.template.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.charlie.template.bo.FooBO;
import org.charlie.template.dao.BarDAO;
import org.charlie.template.dao.FooDAO;
import org.charlie.template.po.FooPO;
import org.charlie.template.utilities.BeanUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Slf4j
@Component("fooServiceImpl")
//@CacheConfig(cacheNames = {"fooCache"})
public class FooServiceImpl implements FooService {

    private FooDAO fooDAO;

    @Autowired
    public void setFooDAO(FooDAO fooDAO) {
        this.fooDAO = fooDAO;
    }

    @Override
//    @Cacheable("fooList")
    public List<FooBO> queryFoos() {
        log.info("Fail to hit foo cache.");

//        List<FooPO> fooPOs = fooDAO.selectFoos();
//        BeanUtility.()
        return Lists.transform(fooDAO.selectFoos(), new Function<FooPO, FooBO>() {
            @Nullable
            @Override
            public FooBO apply(@Nullable FooPO fooPO) {
                return FooBO.fromPO(fooPO);
            }
        });
    }

    @Override
//    @CachePut(cacheNames = { "foo" }, key = "#foo.fooId")
    public boolean addFoo(FooBO fooBO) {
        log.info("Add to foo cache.");
        fooDAO.insertFoo(FooBO.toPO(fooBO));
        return true;
    }

    @Override
//    @CacheEvict(cacheNames = { "foo" }, key = "#foo.fooId")
    public void modifyFoo(FooBO fooBO) {
        log.info("Update foo cache.");
        fooDAO.updateFoo(FooBO.toPO(fooBO));
    }

    @Override
//    @CacheEvict(cacheNames = { "foo" }, key = "#foo.fooId")
    public void removeFoo(FooBO fooBO) {
        fooDAO.deleteFoo(FooBO.toPO(fooBO));
    }
}
