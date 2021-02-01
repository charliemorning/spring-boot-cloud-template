package org.charlie.template.repository;

import org.charlie.template.po.FooPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface FooRepository extends CrudRepository<FooPO, Long> {
    Page<FooPO> findByName(String name, Pageable pageable);
}
