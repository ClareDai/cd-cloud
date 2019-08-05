package cn.claredai.auth.model;

import lombok.Data;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/7/4 19:39
 **/
@Data
public class UserInfoDTO {
    private String username;
    private String openId;
    private String mobile;
    private String password;
}
