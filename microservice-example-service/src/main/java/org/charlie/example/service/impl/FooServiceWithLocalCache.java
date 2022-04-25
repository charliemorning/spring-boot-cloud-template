package org.charlie.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.charlie.example.bo.entities.FooBo;
import org.charlie.example.bo.mappers.FooBoConverter;
import org.charlie.example.dao.FooDao;
import org.charlie.example.common.constants.cache.CacheConstants;
import org.charlie.example.common.utils.bean.BeanUtil;
import org.charlie.example.po.Foo;
import org.charlie.example.po.entities.FooPo;
import org.charlie.example.service.api.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service("fooServiceWithLocalCache")
@CacheConfig(cacheNames = "foo-cache")
public class FooServiceWithLocalCache extends ServiceImpl<FooDao, Foo> implements FooService {

    @Cacheable(key = "#fooBo.id", unless = "#result == null", cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER)
    @Override
    public List<FooBo> selectWithCache(FooBo fooBO) {
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
                    @CacheEvict(key = "#fooBo.id", beforeInvocation = true,  cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER),
                    @CacheEvict(key = "#fooBo.id", cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER)
            })
    public boolean updateWithCache(FooBo fooBo) {
        Foo foo = FooBoConverter.INSTANCE.toPo(fooBo);
        baseMapper.update(foo, null);
        return true;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(key = "#fooBo.id", beforeInvocation = true, cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER),
                    @CacheEvict(key = "#fooBo.id", cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER)
            })
    public boolean deleteWithCache(FooBo fooBo) {
        Foo foo = FooBoConverter.INSTANCE.toPo(fooBo);
        baseMapper.deleteById(foo);
        return true;
    }
}
