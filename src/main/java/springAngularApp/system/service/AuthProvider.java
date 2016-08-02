package springAngularApp.system.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component	//把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
public class AuthProvider {

    public boolean hasRole(String role) {
        SecurityContext context = getContext();
        /*
         *   Authentication是一个接口，用来表示用户认证信息的，
         *   在用户登录认证之前相关信息会封装为一个Authentication具体实现类的对象，
         *   在登录认证成功之后又会生成一个信息更全面，包含用户权限等信息的Authentication对象，
         *   然后把它保存在SecurityContextHolder所持有的SecurityContext中，供后续的程序进行调用，如访问权限的鉴定等。*/
        Authentication authentication = context.getAuthentication();//身份验证
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals(role);
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }

    protected SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }

}
