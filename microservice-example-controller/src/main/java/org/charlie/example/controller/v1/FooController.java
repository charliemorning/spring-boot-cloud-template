package org.charlie.example.controller.v1;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.charlie.example.bo.FooBo;
import org.charlie.example.entities.vo.FooVo;
import org.charlie.example.entities.vo.FooVoMapper;
import org.charlie.example.framework.utils.bean.BeanUtil;
import org.charlie.example.service.FooService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import javax.validation.Valid;
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

//        List<FooVo> fooVos = Lists.transform(fooService.queryFoos(null), new Function<FooBo, FooVo>() {
//            @Nullable
//            @Override
//            public FooVo apply(@Nullable FooBo fooBo) {
//                FooVo fooVo = FooVo.builder().build();
//                BeanUtil.copy(fooBo, fooVo);
//                return fooVo;
//            }
//        });

        return null;
    }

    @GetMapping("/{id:\\d+}")
    public List<FooVo> getFoos(@PathVariable int id) {
        FooBo fooBo = FooBo.builder().id(id).build();
        List<FooBo> fooBos = fooService.queryFoos(fooBo);
        List<FooVo> fooVos = BeanUtil.copyList(fooBos, FooVo::new);
        List<FooBo> fooBOs2 = fooServiceWithLocalCache.queryFoos(fooBo);
        return fooVos;
    }

    @PutMapping(value = "/")
    public void createFoo(@RequestBody @Valid FooVo fooVo) {
        FooBo fooBo = FooBo.builder().build();
        BeanUtil.copy(fooVo, fooBo);
        fooService.createFoo(fooBo);
    }

    @PostMapping(value = "/")
    public void updateFoo(@RequestBody @Valid FooVo fooVo) {
        FooBo fooBo = FooBo.builder().build();
        BeanUtil.copy(fooVo, fooBo);
        fooService.modifyFoo(fooBo);
    }

    @PatchMapping(value = "/{id:\\d+}")
    public void patiallyUpdateFoo(@PathVariable int id, @RequestBody @Valid FooVo fooVo) {
        FooBo fooBo = FooVoMapper.INSTANCE.getBo(fooVo);
        fooBo.setId(id);
        fooService.modifyFoo(fooBo);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    public void deleteFoo(@PathVariable int id) {
        FooBo fooBo = FooBo.builder().id(id).build();
        fooServiceWithLocalCache.removeFoo(fooBo);
    }
}
