package cn.claredai.common.core.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/19 16:52
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SysMenu extends SuperModel {
    private static final long serialVersionUID = -2311788696213666948L;
    private Long parentId;
    private String name;
    private String css;
    private String url;
    private String path;
    private Integer sort;
    private String type;
    private Boolean hidden;
    private String icon;
    /**
     * 请求的类型
     */
    private String pathMethod;
    private String createBy;
    private String updateBy;

    @TableField(exist = false)
    private List<SysMenu> subMenus;
    @TableField(exist = false)
    private Long roleId;
    @TableField(exist = false)
    private Set<Long> menuIds;
}
