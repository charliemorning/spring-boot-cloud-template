package org.charlie.example.vo.mappers;

import java.util.List;

public interface TripleConverter<V, B, P> {

    P voToPo(V v);
    List<P> voListToPoList(List<V> v);

    P boToPo(B b);
    List<P> boListToPoList(List<B> b);

    B voToBo(V v);
    List<B> voListToBoList(List<V> v);

    B poToBo(P p);
    List<B> poListToBoList(List<P> p);

    V poToVo(P p);
    List<V> poListToVoList(List<P> p);

    V boToVo(B b);
    List<V> boListToVoList(List<B> b);
}
