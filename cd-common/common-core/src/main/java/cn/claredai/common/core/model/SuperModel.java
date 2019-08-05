package cn.claredai.common.core.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/7/5 15:46
 **/
@Data
public class SuperModel extends Model {
    public final static String DEFAULT_USERNAME = "system";
    @TableId
    private Long id;
//    private String createdBy;
//    private String updatedBy;
    private Date createTime;
    private Date updateTime;

    @JSONField(serialize = false)
    @TableField(exist = false)
    private int pageNum;
    @JSONField(serialize = false)
    @TableField(exist = false)
    private int pageSize;
    @JSONField(serialize = false)
    @TableField(exist = false)
    private String orderBy;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
