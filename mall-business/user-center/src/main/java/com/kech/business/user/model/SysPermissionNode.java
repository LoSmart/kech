package com.kech.business.user.model;

import com.kech.common.model.SysPermission;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SysPermissionNode extends SysPermission {
    @Getter
    @Setter
    private List<SysPermissionNode> children;
}
