package cn.claredai.common.auth.constants;

/**
 * @Description 权限常量
 * @Author ClareDai
 * @Date create in 2019/6/19 15:21
 **/
public enum AuthoritiesEnum {
    /**
     * 管理员
     */
    ADMIN("ROLE_ADMIN"),
    /**
     * 普通用户
     */
    USER("ROLE_USER"),
    /**
     * 匿名用户
     */
    ANONYMOUS("ROLE_ANONYMOUS");

    private String role;

    AuthoritiesEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
