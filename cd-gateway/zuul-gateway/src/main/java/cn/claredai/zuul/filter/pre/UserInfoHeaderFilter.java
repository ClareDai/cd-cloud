package cn.claredai.zuul.filter.pre;

import cn.claredai.common.core.constant.SecurityConstants;
import cn.hutool.core.collection.CollectionUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/7/15 22:19
 **/
@Component
public class UserInfoHeaderFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FORM_BODY_WRAPPER_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();

            OAuth2Authentication oauth2Authentication = (OAuth2Authentication)authentication;
            String clientId = oauth2Authentication.getOAuth2Request().getClientId();

            RequestContext ctx = RequestContext.getCurrentContext();
            ctx.addZuulRequestHeader(SecurityConstants.USER_HEADER, username);
            ctx.addZuulRequestHeader(SecurityConstants.CLIENT_HEADER, clientId);
            ctx.addZuulRequestHeader(SecurityConstants.ROLE_HEADER, CollectionUtil.join(authentication.getAuthorities(), ","));
        }
        return null;
    }
}
