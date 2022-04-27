package org.charlie.example.configs.rdbs;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;


public class AutoFillMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = this.getFieldValByName("create_time", metaObject);
        if (createTime == null) {
            this.strictInsertFill(metaObject, "create_time", LocalDateTime.class, LocalDateTime.now());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = this.getFieldValByName("update_time", metaObject);
        if (updateTime == null) {
            this.strictUpdateFill(metaObject, "update_time", LocalDateTime.class, LocalDateTime.now());
        }
    }
}