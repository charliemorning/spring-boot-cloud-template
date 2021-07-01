package org.charlie.template.service.implement;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.charlie.template.bo.FooBO;
import org.charlie.template.common.utility.bean.BeanUtility;
import org.charlie.template.dao.FooDAO;
import org.charlie.template.po.FooPO;
import org.charlie.template.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;


@Service
public class FooServiceImpl implements FooService {

    private FooDAO fooDAO;

    @Autowired
    public void setFooDAO(FooDAO fooDAO) {
        this.fooDAO = fooDAO;
    }

    @Override
    public List<FooBO> queryFoos(FooBO fooBO) {
        FooPO fooPO = FooPO.builder().build();
        if (Objects.isNull(fooBO)) {
            fooPO = null;
        } else {
            BeanUtility.copy(fooBO, fooPO);
        }


        return Lists.transform(fooDAO.selectFoos(fooPO
        ), new Function<FooPO, FooBO>() {
            @Nullable
            @Override
            public FooBO apply(@Nullable FooPO fooPO) {
                FooBO fooBO = FooBO.builder().build();
                BeanUtility.copy(fooPO, fooBO);
                return fooBO;
            }
        });

    }

    @Override
    public void createFoo(FooBO fooBO) {
        FooPO fooPO = FooPO.builder().build();
        BeanUtility.copy(fooBO, fooPO);
        fooDAO.insertFoo(fooPO);
    }

    @Override
    public void modifyFoo(FooBO fooBO) {
        FooPO fooPO = FooPO.builder().build();
        BeanUtility.copy(fooBO, fooPO);
        fooDAO.updateFoo(fooPO);
    }

    @Override
    public void removeFoo(FooBO fooBO) {
        FooPO fooPO = FooPO.builder().build();
        BeanUtility.copy(fooBO, fooPO);
        fooDAO.deleteFoo(fooPO);
    }
}
