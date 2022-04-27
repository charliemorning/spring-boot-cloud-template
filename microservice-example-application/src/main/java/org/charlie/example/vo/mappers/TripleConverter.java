package org.charlie.example.vo.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.charlie.example.bo.entities.BaseBo;
import org.charlie.example.bo.mappers.BoConverter;
import org.charlie.example.po.BasePo;
import org.charlie.example.vo.entities.PagedVo;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

public interface TripleConverter<V, B extends BaseBo, P extends BasePo> extends BoConverter<B, P> {


    P voToPo(V v);

    List<P> voListToPoList(List<V> v);

    B voToBo(V v);

    List<B> voListToBoList(List<V> v);

    V poToVo(P p);

    List<V> poListToVoList(List<P> p);

    V boToVo(B b);

    List<V> boListToVoList(List<B> b);

    @Mappings({
            @Mapping(source = "records", target="data"),
            @Mapping(source = "total", target="total"),
            @Mapping(source = "size", target="pageSize"),
            @Mapping(source = "current", target="pageIndex"),
    })
    PagedVo<V> toPagedVo(Page<P> pagedUserPo);
}
