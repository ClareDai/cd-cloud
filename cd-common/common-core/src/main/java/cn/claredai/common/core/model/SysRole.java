package cn.claredai.common.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 角色实体
 * @Author ClareDai
 * @Date create in 2019/6/2 22:44
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole extends SuperModel {
    private static final long serialVersionUID = -562031214363329261L;

    private String code;
    private String name;
    private String createBy;
    private String updateBy;
}
