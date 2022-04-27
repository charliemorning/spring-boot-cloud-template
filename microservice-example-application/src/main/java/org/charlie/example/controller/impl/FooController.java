package org.charlie.example.controller.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.charlie.example.po.foo.Foo;
import org.charlie.example.service.api.FooService;
import org.charlie.example.vo.entities.PagedVo;
import org.charlie.example.vo.entities.ResponseWrapper;
import org.charlie.example.vo.entities.foo.FooVo;
import org.charlie.example.vo.mappers.foo.FooVoMapper;
import org.springframework.web.bind.annotation.*;

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
    public ResponseWrapper<PagedVo<FooVo>> getPagedFoo(@RequestParam int index, @RequestParam int size) {
        Page<Foo> page = new Page<>(index, size);
        Page<Foo> foos = fooService.page(page);
        PagedVo<FooVo> pagedFooVo = FooVoMapper.INSTANCE.toPagedVo(foos);
        return ResponseWrapper.<PagedVo<FooVo>>builder().resultData(pagedFooVo).build();
    }

    @GetMapping("/")
    public List<FooVo> getFoos(@PathVariable int id) {
        List<Foo> foos = fooService.list();
        return FooVoMapper.INSTANCE.poListToVoList(foos);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseWrapper<FooVo> getFoo(@PathVariable long id) {
        Foo foo = fooService.getById(id);
        return ResponseWrapper.<FooVo>builder().resultData(FooVoMapper.INSTANCE.poToVo(foo)).build();
    }

    @PutMapping(value = "")
    public void createFoo(@RequestBody FooVo fooVo) {
        Foo foo = FooVoMapper.INSTANCE.voToPo(fooVo);
        fooService.save(foo);
    }

    @PostMapping(value = "")
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
