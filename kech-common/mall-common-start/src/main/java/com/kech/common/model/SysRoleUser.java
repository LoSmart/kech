package com.kech.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kech.common.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zscat
 * @since 2019-05-01
 */
@Data
@TableName("sys_role_user")
public class SysRoleUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private Long userId;

    @TableField("role_id")
    private Long roleId;



    @Override
    public String toString() {
        return "SysRoleUser{" +
        ", userId=" + userId +
        ", roleId=" + roleId +
        "}";
    }
}
