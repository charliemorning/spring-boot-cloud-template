package org.charlie.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.charlie.example.bo.entities.FooBo;
import org.charlie.example.bo.mappers.FooBoConverter;
import org.charlie.example.dao.FooDao;
import org.charlie.example.po.Foo;
import org.charlie.example.service.api.FooService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("fooServiceImpl")
@CacheConfig(cacheNames = "foo-cache")
public class FooServiceImpl extends ServiceImpl<FooDao, Foo> implements FooService {

    @Override
    @Cacheable(key = "#fooBo.id", unless = "#result == null")
    public List<FooBo> selectWithCache(FooBo fooBo) {
        List<Foo> fooList = baseMapper.selectList(null);
        return FooBoConverter.INSTANCE.toBoList(fooList);
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
                    @CacheEvict(key = "#fooBo.id", beforeInvocation = true),
                    @CacheEvict(key = "#fooBo.id")
            })
    public boolean updateWithCache(FooBo fooBo) {
        Foo foo = FooBoConverter.INSTANCE.toPo(fooBo);
        baseMapper.update(foo, null);
        return true;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(key = "#fooBo.id", beforeInvocation = true),
                    @CacheEvict(key = "#fooBo.id")
            })
    public boolean deleteWithCache(FooBo fooBo) {
        Foo foo = FooBoConverter.INSTANCE.toPo(fooBo);
        baseMapper.deleteById(foo);
        return true;
    }
}
