package org.charlie.example.vo.mappers;

public interface BinaryMapper<U, L> {
    U fromLower(L t);
    L toLower(U t);
}
