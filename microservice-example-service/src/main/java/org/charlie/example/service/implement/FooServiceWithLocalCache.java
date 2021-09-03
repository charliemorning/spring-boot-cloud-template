package org.charlie.example.service.implement;

import org.charlie.example.bo.FooBO;
import org.charlie.example.dao.FooDAO;
import org.charlie.example.framework.constants.cache.CacheConstants;
import org.charlie.example.framework.utils.bean.BeanUtil;
import org.charlie.example.po.FooPO;
import org.charlie.example.service.FooService;
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
public class FooServiceWithLocalCache implements FooService {
    private FooDAO fooDAO;

    @Autowired
    public void setFooDAO(FooDAO fooDAO) {
        this.fooDAO = fooDAO;
    }

    @Cacheable(key = "#fooBO.id", unless = "#result == null", cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER)
    @Override
    public List<FooBO> queryFoos(FooBO fooBO) {
        FooPO fooPO = FooPO.builder().build();
        if (Objects.isNull(fooBO)) {
            fooPO = null;
        } else {
            BeanUtil.copy(fooBO, fooPO);
        }

        List<FooPO> fooPOList = fooDAO.selectFoos(fooPO);

        return BeanUtil.copyList(fooPOList, FooBO::new);
    }

    @Override
    public FooBO createFoo(FooBO fooBO) {
        FooPO fooPO = FooPO.builder().build();
        BeanUtil.copy(fooBO, fooPO);
        fooDAO.insertFoo(fooPO);
        return fooBO;
    }


    /**
     * cache aside pattern
     *
     * @param fooBO
     * @return
     */
    @Override
    @Caching(
            evict = {
                    @CacheEvict(key = "#fooBO.id", beforeInvocation = true,  cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER),
                    @CacheEvict(key = "#fooBO.id", cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER)
            })
    public FooBO modifyFoo(FooBO fooBO) {
        FooPO fooPO = FooPO.builder().build();
        BeanUtil.copy(fooBO, fooPO);
        fooDAO.updateFoo(fooPO);
        return fooBO;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(key = "#fooBO.id", beforeInvocation = true, cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER),
                    @CacheEvict(key = "#fooBO.id", cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER)
            })
    public FooBO removeFoo(FooBO fooBO) {
        FooPO fooPO = FooPO.builder().build();
        BeanUtil.copy(fooBO, fooPO);
        fooDAO.deleteFoo(fooPO);
        return fooBO;
    }
}
