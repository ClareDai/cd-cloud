package cn.claredai.common.core.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description 用户实体
 * @Author ClareDai
 * @Date create in 2019/6/2 22:25
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser extends SuperModel {
    private static final long serialVersionUID = 6152806489406388813L;

    private String username;
    private String password;
    private String realname;
    private String headImgUrl;
    private String mobile;
    private Integer sex;
    private Boolean enabled;
    private String type;
    private String openId;
    private boolean isDel;

    @TableField(exist = false)
    private List<SysRole> roles;
    @TableField(exist = false)
    private String roleId;
    @TableField(exist = false)
    private String oldPassword;
    @TableField(exist = false)
    private String newPassword;
}
