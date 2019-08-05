package cn.claredai.auth.model;

import cn.claredai.common.core.model.SuperModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/6/20 23:47
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_client_details")
public class Client extends SuperModel {
    private static final long serialVersionUID = 3509597010128602462L;

    private String clientId;
    private String resourceIds = "";
    private String clientSecret;
    private String clientSecretStr;
    private String scope = "all";
    private String authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials";
    private String webServerRedirectUri;
    private String authorities = "";
    private Integer accessTokenValidity = 18000;
    private Integer refreshTokenValidity = 28800;
    private String additionalInformation = "{}";
    private String autoapprove = "true";
}
