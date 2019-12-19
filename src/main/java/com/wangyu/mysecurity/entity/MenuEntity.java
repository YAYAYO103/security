package com.wangyu.mysecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author YAYAYO
 * @since 2019-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("menu")
public class MenuEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "m_id", type = IdType.AUTO)
    private Integer mId;

    /**
     * 菜单名称
     */
    private String mName;

    /**
     * 权限字符串
     */
    private String mUrl;

    /**
     * 父id
     */
    @TableField("m_parentId")
    private Integer mParentid;

    /**
     * 是否需要判断权限（true 需要, false 不需要）
     */
    @TableField("m_needPermission")
    private Integer mNeedpermission;

    /**
     * 是否正常(true 正常，false 已删除)
     */
    private Integer mDelete;


}
