package com.wrx.codeplatform.domain.common;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;

/**
 * 路径权限关联表(SysRequestPathPermissionRelation)实体类
 *
 * @author makejava
 * @since 2019-09-04 17:11:53
 */
@Data
public class SysRequestPathPermissionRelation implements Serializable {
    private static final long serialVersionUID = -59197738311147860L;
    //主键id
    private Integer id;
    //请求路径id
    private Integer urlId;
    //权限id
    private Integer permissionId;


}