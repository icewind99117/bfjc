package com.lsf.imf.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;


/**
 * shiro配置项
 */
@Configuration
public class ShiroConfig {


	    //处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
	    //指定加密方式方式，也可以在这里加入缓存，当用户超过五次登陆错误就锁定该用户禁止不断尝试登陆
	    @Bean
	    public HashedCredentialsMatcher hashedCredentialsMatcher() {
	        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
	        credentialsMatcher.setHashAlgorithmName("SHA-1");
	        credentialsMatcher.setHashIterations(1024);
	        credentialsMatcher.setStoredCredentialsHexEncoded(true);//加盐加密后的密码将再次进行base64加密，与传入的数据库密码（16位开始）进行比对
	        return credentialsMatcher;
	    }

	    @Bean
	    public ShiroRealm shiroRealm() {
	        ShiroRealm realm = new ShiroRealm();
	        realm.setCredentialsMatcher(hashedCredentialsMatcher());
	        return realm;
	    }


	    @Bean
	    public DefaultWebSecurityManager  securityManager(){
	        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
	        securityManager.setRealm(shiroRealm());
	        return securityManager;
	    }

	    @Bean
	    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager  securityManager){
	        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
	        shiroFilterFactoryBean.setSecurityManager(securityManager);

		     // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
	        shiroFilterFactoryBean.setLoginUrl("/page/login");
	     // 登录成功后要跳转的链接
	        shiroFilterFactoryBean.setSuccessUrl("/");
	     // 未授权界面;
	        shiroFilterFactoryBean.setUnauthorizedUrl("/403.html");
	        
//	        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	     // 拦截器.
	        Map<String, String> filterChainDefinitionManager = new LinkedHashMap<String,String>();
	     // 配置不会被拦截的链接 顺序判断
	        filterChainDefinitionManager.put("/page/login","anon");
	        filterChainDefinitionManager.put("/login", "anon");//anon 可以理解为不拦截
//	        filterChainDefinitionManager.put("/ajaxLogin", "anon");//anon 可以理解为不拦截
	        filterChainDefinitionManager.put("/js/**",  "anon");//静态资源不拦截
	        
	     // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
	        filterChainDefinitionManager.put("/logout", "logout");
	        

	     // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
			// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//	        filterChainDefinitionManager.put("/page/report**",  "authc,roles[reporter]");//其他资源全部拦截
//	        filterChainDefinitionManager.put("/reportData/**",  "authc,roles[reporter]");//其他资源全部拦截
//	        filterChainDefinitionManager.put("/flowEngine/**",  "authc,roles[reporter]");//其他资源全部拦截	        
	        
//	        filterChainDefinitionMap.put("/add", "perms[权限添加]");
//	        filterChainDefinitionManager.put("/**", "authc,roles[admin]");
	        filterChainDefinitionManager.put("/**", "authc");
	        
	        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);

	        return shiroFilterFactoryBean;
	    }



	    //thymeleaf模板引擎和shiro整合时使用
	    @Bean
	    public ShiroDialect shiroDialect(){
	        return new ShiroDialect();
	    }

	
}
