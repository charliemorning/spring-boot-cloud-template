package org.charlie.example.vo.mappers;

public interface BinaryConverter<U, L> {
    U fromLower(L t);
    L toLower(U t);
}
