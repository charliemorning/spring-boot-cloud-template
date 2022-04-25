package org.charlie.example.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author charlie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BasePo {
    @TableId(type = IdType.AUTO)
    Long id;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    LocalDateTime updateTime;
}
