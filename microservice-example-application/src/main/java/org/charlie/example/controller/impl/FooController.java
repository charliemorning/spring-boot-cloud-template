package org.charlie.example.controller.impl;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.charlie.example.bo.entities.FooBo;
import org.charlie.example.common.utils.bean.BeanUtil;
import org.charlie.example.service.FooService;
import org.charlie.example.vo.entities.FooVo;
import org.charlie.example.vo.mappers.FooVoMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/v1/foo")
public class FooController {

    FooService fooService;

    FooService fooServiceWithLocalCache;

    @Resource(name = "fooServiceImpl")
    public void setFooService(FooService fooService) {
        this.fooService = fooService;
    }

    @Resource(name = "fooServiceWithLocalCache")
    public void setFooServiceWithLocalCache(FooService fooServiceWithLocalCache) {
        this.fooServiceWithLocalCache = fooServiceWithLocalCache;
    }

    @GetMapping("")
    public List<FooVo> getFoosPagintated() {

        List<FooVo> fooVOs = Lists.transform(fooService.queryFoos(null), new Function<FooBo, FooVo>() {
            @Nullable
            @Override
            public FooVo apply(@Nullable FooBo fooBO) {
                FooVo fooVO = FooVo.builder().build();
                BeanUtil.copy(fooBO, fooVO);
                return fooVO;
            }
        });

        return fooVOs;
    }

    @GetMapping("/{id:\\d+}")
    public List<FooVo> getFoos(@PathVariable int id) {
        FooBo fooBO = FooBo.builder().id(id).build();
        List<FooBo> fooBos = fooService.queryFoos(fooBO);
        List<FooVo> fooVOs = BeanUtil.copyList(fooBos, FooVo::new);
        List<FooBo> fooBOs2 = fooServiceWithLocalCache.queryFoos(fooBO);
        return fooVOs;
    }

    @PutMapping(value = "/")
    public void createFoo(@RequestBody FooVo fooVO) {
        FooBo fooBO = FooVoMapper.INSTANCE.vToB(fooVO);
        fooService.createFoo(fooBO);
    }

    @PostMapping(value = "/")
    public void updateFoo(@RequestBody FooVo fooVO) {
        FooBo fooBO = FooVoMapper.INSTANCE.vToB(fooVO);
        fooService.modifyFoo(fooBO);
    }

    @PatchMapping(value = "/{id:\\d+}")
    public void patiallyUpdateFoo(@PathVariable int id, @RequestBody FooVo fooVO) {
        FooBo fooBO = FooVoMapper.INSTANCE.vToB(fooVO);
        fooBO.setId(id);
        fooService.modifyFoo(fooBO);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    public void deleteFoo(@PathVariable int id) {
        FooBo fooBO = FooBo.builder().id(id).build();
        fooServiceWithLocalCache.removeFoo(fooBO);
    }
}
