package com.lym;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="AdminAuth对象")
public class AdminAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer authId;

    @ApiModelProperty(value = "菜单名称")
    private String authName;

    @ApiModelProperty(value = "菜单层级")
    private String authLevel;

    @ApiModelProperty(value = "菜单的父id   -1为1级菜单")
    private Integer authPid;

    @ApiModelProperty(value = "菜单的类型   权限/按钮")
    private String authTypr;

    @ApiModelProperty(value = "排序序号")
    private Integer authSort;



}
