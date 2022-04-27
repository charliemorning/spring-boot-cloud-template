package org.charlie.example.po.foo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.charlie.example.dao.handlers.ForeignKeyTypeHandler;
import org.charlie.example.po.BasePo;
import org.charlie.example.po.bar.Bar;

@Data
@Accessors(chain = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "foo_inf", autoResultMap = true)
public class Foo extends BasePo {

    @TableField
    String name;

    @TableField(value = "bar_id", typeHandler = ForeignKeyTypeHandler.class)
    Bar bar;
}

