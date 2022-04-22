package org.charlie.example.vo.mappers;

public interface TripleConverter<V, B, P> {

    P vToP(V v);
    P bToP(B b);
    B vToB(V v);
    B pToB(P p);
    V pToV(P p);
    V bToV(B b);
}
