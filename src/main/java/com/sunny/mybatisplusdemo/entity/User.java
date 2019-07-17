package com.sunny.mybatisplusdemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description user entity
 * @Author sunny
 * @Date 2019-07-16 11:25
 */
@Data
public class User {
    @TableId("id")
    private Long userId;

    @TableField("name")
    private String realName;

    private Integer age;

    private LocalDateTime birth;

    //用transient 编辑的字段不参与序列化，不保存到数据库
    private transient String desc;

    private static String desc1;

    @TableField(exist = false) //不是数据库中的字段
    private String desc2;
}
