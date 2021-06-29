package org.charlie.template;


import org.charlie.template.dao.FooDAO;
import org.charlie.template.po.FooPO;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@MybatisTest

@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@WebAppConfiguration
@Transactional
@SpringBootTest
public class FooDAOTest {

    @Autowired
    FooDAO fooDAO;

    @org.junit.Test
    public  void test(){
        List<FooPO> select = fooDAO.selectFoos(null);
        for (FooPO fooPO: select) {
            System.out.println(fooPO);
        }
    }
}
