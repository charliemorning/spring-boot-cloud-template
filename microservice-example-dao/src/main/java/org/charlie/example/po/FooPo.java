package org.charlie.example.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@TableName("foo")
public class FooPo {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField("name")
    private String name;
}
