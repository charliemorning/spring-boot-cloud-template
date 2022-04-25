package org.charlie.example.controller.impl;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.charlie.example.bo.entities.FooBo;
import org.charlie.example.common.utils.bean.BeanUtil;
import org.charlie.example.po.Foo;
import org.charlie.example.service.api.FooService;
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

    @Resource(name = "fooServiceImpl")
    public void setFooService(FooService fooService) {
        this.fooService = fooService;
    }



    @GetMapping("")
    public List<FooVo> getFoosPagintated() {
        List<Foo> foos = fooService.list();
        return FooVoMapper.INSTANCE.poListToVoList(foos);
    }

    @GetMapping("/{id:\\d+}")
    public List<FooVo> getFoos(@PathVariable int id) {
        List<Foo> foos = fooService.list();
        return FooVoMapper.INSTANCE.poListToVoList(foos);
    }

    @PutMapping(value = "/")
    public void createFoo(@RequestBody FooVo fooVo) {
        Foo foo = FooVoMapper.INSTANCE.voToPo(fooVo);
        fooService.save(foo);
    }

    @PostMapping(value = "/")
    public void updateFoo(@RequestBody FooVo fooVo) {
        Foo foo = FooVoMapper.INSTANCE.voToPo(fooVo);
        fooService.updateById(foo);
    }

    @PatchMapping(value = "/{id:\\d+}")
    public void patiallyUpdateFoo(@PathVariable long id, @RequestBody FooVo fooVo) {
        Foo foo = FooVoMapper.INSTANCE.voToPo(fooVo);
        foo.setId(id);
        fooService.updateById(foo);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    public void deleteFoo(@PathVariable long id) {
        Foo foo = Foo.builder().id(id).build();
        fooService.updateById(foo);
    }
}
