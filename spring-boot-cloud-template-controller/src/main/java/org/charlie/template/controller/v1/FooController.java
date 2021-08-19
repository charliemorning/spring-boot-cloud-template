package org.charlie.template.controller.v1;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.charlie.template.bo.FooBO;
import org.charlie.template.framework.utils.bean.BeanUtil;
import org.charlie.template.service.FooService;
import org.charlie.template.entities.vo.FooVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/foo")
public class FooController {

    FooService fooService;

    @Autowired
    public void setFooService(FooService fooService) {
        this.fooService = fooService;
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

        List<FooVO> fooVOs = Lists.transform(fooService.queryFoos(fooBO), new Function<FooBO, FooVO>() {
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
        fooService.removeFoo(fooBO);
    }

}
