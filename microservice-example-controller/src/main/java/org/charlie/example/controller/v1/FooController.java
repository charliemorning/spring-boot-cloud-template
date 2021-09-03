package org.charlie.example.controller.v1;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.charlie.example.bo.FooBO;
import org.charlie.example.entities.vo.FooVO;
import org.charlie.example.framework.utils.bean.BeanUtil;
import org.charlie.example.service.FooService;
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
    public List<FooVO> getFoosPagintated() {

        List<FooVO> fooVOs = Lists.transform(fooService.queryFoos(null), new Function<FooBO, FooVO>() {
            @Nullable
            @Override
            public FooVO apply(@Nullable FooBO fooBO) {
                FooVO fooVO = FooVO.builder().build();
                BeanUtil.copy(fooBO, fooVO);
                return fooVO;
            }
        });

        return fooVOs;
    }

    @GetMapping("/{id:\\d+}")
    public List<FooVO> getFoos(@PathVariable int id) {
        FooBO fooBO = FooBO.builder().id(id).build();
        List<FooBO> fooBOs = fooService.queryFoos(fooBO);
        List<FooVO> fooVOs = BeanUtil.copyList(fooBOs, FooVO::new);
        List<FooBO> fooBOs2 = fooServiceWithLocalCache.queryFoos(fooBO);
        return fooVOs;
    }

    @PutMapping(value = "/")
    public void createFoo(@RequestBody FooVO fooVO) {
        FooBO fooBO = FooBO.builder().build();
        BeanUtil.copy(fooVO, fooBO);
        fooService.createFoo(fooBO);
    }

    @PostMapping(value = "/")
    public void updateFoo(@RequestBody FooVO fooVO) {
        FooBO fooBO = FooBO.builder().build();
        BeanUtil.copy(fooVO, fooBO);
        fooService.modifyFoo(fooBO);
    }

    @PatchMapping(value = "/{id:\\d+}")
    public void patiallyUpdateFoo(@PathVariable int id, @RequestBody FooVO fooVO) {
        FooBO fooBO = FooBO.builder().build();
        BeanUtil.copy(fooVO, fooBO);
        fooBO.setId(id);
        fooService.modifyFoo(fooBO);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    public void deleteFoo(@PathVariable int id) {
        FooBO fooBO = FooBO.builder().id(id).build();
        fooServiceWithLocalCache.removeFoo(fooBO);
    }
}
