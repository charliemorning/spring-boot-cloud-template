package org.charlie.example.service.implement;

import org.charlie.example.bo.FooBo;
import org.charlie.example.dao.FooDao;
import org.charlie.example.framework.constants.cache.CacheConstants;
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


@Service("fooServiceWithLocalCache")
@CacheConfig(cacheNames = "foo-cache")
public class FooServiceWithLocalCache implements FooService {
    private FooDao fooDAO;

    @Autowired
    public void setFooDAO(FooDao fooDAO) {
        this.fooDAO = fooDAO;
    }

    @Cacheable(key = "#fooBO.id", unless = "#result == null", cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER)
    @Override
    public List<FooBo> queryFoos(FooBo fooBO) {
        FooPo fooPO = FooPo.builder().build();
        if (Objects.isNull(fooBO)) {
            fooPO = null;
        } else {
            BeanUtil.copy(fooBO, fooPO);
        }

        List<FooPo> fooPoList = fooDAO.selectFoos(fooPO);

        return BeanUtil.copyList(fooPoList, FooBo::new);
    }

    @Override
    public void createFoo(FooBo fooBO) {
        FooPo fooPO = FooPo.builder().build();
        BeanUtil.copy(fooBO, fooPO);
        fooDAO.insertFoo(fooPO);
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
    public void modifyFoo(FooBo fooBO) {
        FooPo fooPO = FooPo.builder().build();
        BeanUtil.copy(fooBO, fooPO);
        fooDAO.updateFoo(fooPO);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(key = "#fooBO.id", beforeInvocation = true, cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER),
                    @CacheEvict(key = "#fooBO.id", cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER)
            })
    public void removeFoo(FooBo fooBO) {
        FooPo fooPO = FooPo.builder().build();
        BeanUtil.copy(fooBO, fooPO);
        fooDAO.deleteFoo(fooPO);
    }
}
