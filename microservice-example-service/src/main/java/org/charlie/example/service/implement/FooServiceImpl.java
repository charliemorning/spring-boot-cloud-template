package org.charlie.example.service.implement;


import org.charlie.example.bo.FooBo;
import org.charlie.example.bo.FooBoMapper;
import org.charlie.example.dao.FooDao;
import org.charlie.example.framework.utils.bean.BeanUtil;
import org.charlie.example.po.FooPo;
import org.charlie.example.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service("fooServiceImpl")
@CacheConfig(cacheNames = "foo-cache")
public class FooServiceImpl implements FooService {

    private FooDao fooDao;

    @Autowired
    public void setFooDAO(FooDao fooDao) {
        this.fooDao = fooDao;
    }

    @Cacheable(key = "#fooBo.id", unless = "#result == null")
    @Override
    public List<FooBo> queryFoos(FooBo fooBo) {
        FooPo fooPo = null;
        if (Objects.isNull(fooBo)) {
            fooPo = null;
        } else {
            fooPo = FooBoMapper.INSTANCE.toPo(fooBo);
        }
        List<FooPo> fooPoList = fooDao.selectFoos(fooPo);
        return BeanUtil.copyList(fooPoList, FooBo::new);
    }

    @Override
    public void createFoo(FooBo fooBo) {
        FooPo fooPo = FooBoMapper.INSTANCE.toPo(fooBo);
        fooDao.insertFoo(fooPo);
    }

    /**
     * cache aside pattern
     *
     * @param fooBo
     * @return
     */
    @Override
    @Caching(
            evict = {
                    @CacheEvict(key = "#fooB0.id", beforeInvocation = true),
                    @CacheEvict(key = "#fooBo.id")
            })
    public void modifyFoo(FooBo fooBo) {
        FooPo fooPo = FooBoMapper.INSTANCE.toPo(fooBo);
        fooDao.updateFoo(fooPo);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(key = "#fooBo.id", beforeInvocation = true),
                    @CacheEvict(key = "#fooBo.id")
            })
    public void removeFoo(FooBo fooBo) {
        FooPo fooPo = FooBoMapper.INSTANCE.toPo(fooBo);
        fooDao.deleteFoo(fooPo);
    }
}
