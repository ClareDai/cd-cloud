package cn.claredai.auth.controller;

import cn.claredai.auth.mobile.MobileAuthenticationToken;
import cn.claredai.auth.model.UserInfoDTO;
import cn.claredai.auth.openid.OpenIdAuthenticationToken;
import cn.claredai.auth.service.impl.RedisClientDetailsService;
import cn.claredai.common.auth.util.AuthUtils;
import cn.claredai.common.core.constant.SecurityConstants;
import cn.claredai.common.core.enums.ErrorType;
import cn.claredai.common.core.util.JsonResult;
import cn.claredai.common.core.util.SpringUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @Description OAuth2相关操作
 * @Author ClareDai
 * @Date create in 2019/6/24 22:38
 **/
@Api(tags = "OAuth2相关操作")
@Slf4j
@RestController
public class OAuth2Controller {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @ApiOperation(value = "用户名密码获取token")
    @PostMapping(SecurityConstants.PASSWORD_LOGIN_PRO_URL)
    public void getUserTokenInfo(@RequestBody UserInfoDTO userInfoDTO, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userInfoDTO.getUsername(), userInfoDTO.getPassword());
        writerToken(request, response, token, "用户名或密码错误");
    }

    @ApiOperation(value = "openId获取token")
    @PostMapping(SecurityConstants.OPENID_TOKEN_URL)
    public void getTokenByOpenId(@RequestBody UserInfoDTO userInfoDTO, HttpServletRequest request, HttpServletResponse response) throws IOException {
        OpenIdAuthenticationToken token = new OpenIdAuthenticationToken(userInfoDTO.getOpenId());
        writerToken(request, response, token, "openId错误");
    }

    @ApiOperation(value = "mobile获取token")
    @PostMapping(SecurityConstants.MOBILE_TOKEN_URL)
    public void getTokenByMobile(@RequestBody UserInfoDTO userInfoDTO, HttpServletRequest request, HttpServletResponse response) throws IOException {
        MobileAuthenticationToken token = new MobileAuthenticationToken(userInfoDTO.getMobile(), userInfoDTO.getPassword());
        writerToken(request, response, token, "手机号或密码错误");
    }

    private void writerToken(HttpServletRequest request, HttpServletResponse response, AbstractAuthenticationToken token
            , String badCredenbtialsMsg) throws IOException {
        try {
            final String[] clientInfos = AuthUtils.extractClient(request);
            String clientId = clientInfos[0];
            String clientSecret = clientInfos[1];

            ClientDetails clientDetails = getClient(clientId, clientSecret, null);
            TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "customer");
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
            OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
            oAuth2Authentication.setAuthenticated(true);
            writerObj(response, oAuth2AccessToken);
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            exceptionHandler(response, badCredenbtialsMsg);
        } catch (Exception e) {
            exceptionHandler(response, e);
        }
    }

    private void exceptionHandler(HttpServletResponse response, Exception e) throws IOException {
        log.error("exceptionHandler-error:", e);
        exceptionHandler(response, e.getMessage());
    }

    private void exceptionHandler(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        writerObj(response, JsonResult.fail(ErrorType.SYSTEM_NO_PERMISSION.getCode(), msg));
    }

    private void writerObj(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (
                Writer writer = response.getWriter()
        ) {
            writer.write(JSON.toJSONString(obj));
            writer.flush();
        }
    }

    private ClientDetails getClient(String clientId, String clientSecret, RedisClientDetailsService clientDetailsService) {
        if (clientDetailsService == null) {
            clientDetailsService = SpringUtil.getBean(RedisClientDetailsService.class);
        }
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的信息不存在");
        } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配");
        }
        return clientDetails;
    }
}
