package org.charlie.template.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.charlie.template.bo.BarBO;
import org.charlie.template.bo.FooBO;
import org.charlie.template.dao.BarDAO;
import org.charlie.template.po.BarPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
@Cacheable(cacheNames = {"barCache"})
@Slf4j
public class BarServiceImpl implements BarService {

    private BarDAO barDAO;

    @Autowired
    public void setBarDAO(BarDAO barDAO) {
        this.barDAO = barDAO;
    }

    @Override
    @Cacheable("barList")
    public List<BarBO> queryBars(BarBO barBO) {
        log.info("Fail to hit bar cache.");
        return Lists.transform(barDAO.selectBars(), new Function<BarPO, BarBO>() {
            @Nullable
            @Override
            public BarBO apply(@Nullable BarPO barPO) {
                return BarBO.fromPO(barPO);
            }
        });
    }

    @Override
    @CachePut(cacheNames = { "bar" }, key = "#bar.barId")
    public void addBar(BarBO barBO) {
        log.info("Add to bar cache.");
        barDAO.insertBar(BarBO.toPO(barBO));
    }

    @Override
    @CacheEvict(cacheNames = { "bar" }, key = "#bar.fooId")
    public void modifyBar(BarBO barBO) {
        log.info("Update bar cache.");
        barDAO.updateBar(BarBO.toPO(barBO));
    }

    @Override
    @CacheEvict(cacheNames = { "bar" }, key = "#bar.fooId")
    public void removeBar(BarBO barBO) {
        log.info("Delete bar cache.");
        barDAO.deleteBar(BarBO.toPO(barBO));
    }


}
