package com.lsf.imf.shiro;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lsf.imf.entity.vo.UserInfo;
import com.lsf.imf.entity.vo.UserLoginInfo;
import com.lsf.imf.mapper.UserMapper;


/**
 * 获取用户的角色和权限信息
 */
public class ShiroRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private UserMapper userMapper;
    
    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为：" + token.toString());
        //查出是否有此用户
        List<UserInfo> users = userMapper.findUserInfoByLoginName(token.getUsername());
//        String md5Pwd = new Md5Hash("123", "lucare",2).toString();
        UserInfo user=null;
        if(users.size()>0) {
        	user=users.get(0);
        }
        if (user != null) {


            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("userLoginInfo", new UserLoginInfo(user));//成功则放入session
         // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            //数据库密码前16位是加密后的盐，解密获得盐         
            byte[] salt = Hex.decode(user.getPassword().substring(0,16));            
            //使用sha-1算法，加盐后重复1024次,对传入的密码进行加密
    		//SimpleHash hash =new SimpleHash("SHA-1", token.getPassword(), salt,1024);    		
    		//将数据库中的密码密文和盐传入SimpleAuthenticationInfo,密码明文将通过AuthenticationToken和SimpleAuthenticationInfo一起传给hashedCredentialsMatcher
            return new SimpleAuthenticationInfo(user, user.getPassword().substring(16),ByteSource.Util.bytes(salt), getName());
        }
        return null;
    }
    

    /**
     * 权限认证
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
//        String loginName = (String) super.getAvailablePrincipal(principalCollection);
        UserInfo user = (UserInfo) principalCollection.getPrimaryPrincipal();
//        //到数据库查是否有此对象
//        User user = null;// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//        user = userMapper.findByName(loginName);
        if (user != null) {
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //用户的角色集合
//            info.addRole(user.getRole());
//            List<String> roleStrlist=new ArrayList<String>();////用户的角色集合
//            roleStrlist.add("admin");
//            roleStrlist.add("reporter");
//            info.addRoles(roleStrlist); 
            
            //用户的权限集合
//          List<String> perminsStrlist=new ArrayList<String>();//用户的权限集合
//            info.addStringPermissions(user.getPerminsStrlist()); 

            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }

	public static String entryptPassword(String plainPassword) {

		byte[] salt = generateSalt(8);
		SimpleHash hash =new SimpleHash("SHA-1", plainPassword, salt,1024);  
		String saltString=Hex.encodeToString(salt);
		
		String pwd=Hex.encodeToString(hash.getBytes());
		
		logger.info("saltString:"+saltString);
		return saltString+pwd;
	}
	public static byte[] generateSalt(int numBytes) {
		byte[] bytes = new byte[numBytes];
		new SecureRandom().nextBytes(bytes);
		return bytes;
	}

	public static void main(String[] args) {
		String entryptPwd=entryptPassword("admin");
		logger.info("pwd:"+entryptPwd);
	}
}
