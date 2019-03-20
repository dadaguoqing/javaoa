/**   
* @Title: RoleTag.java 
* @Package com.hj.oa.tag 
* @Description: TODO 
* @author mlsong   
* @date 2018年6月28日 上午9:47:53 
* @version V1.0   
*/
package com.hj.oa.tag;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.hj.oa.bean.Role;

/** 
* @ClassName: RoleTag 
* @Description: 自定义角色判断标签
* @author mlsong 
* @date 2018年6月28日 上午9:47:53 
*  
*/
public class RoleTag extends BodyTagSupport {
	private String hasRole;
	
	/** 
	* @return roleName 
	*/
	public String getHasRole() {
		return hasRole;
	}

	/** 
	* @param roleName 要设置的 roleName 
	*/
	public void setHasRole(String hasRole) {
		this.hasRole = hasRole;
	}

	@Override
	public int doStartTag() throws JspException {
        //获取session中存放的角色
		HttpSession session = (HttpSession) pageContext.getSession();
		List<Role> roles = (List<Role>)session.getAttribute("loginUserRoles");
        
        //判断是否有权限访问
        if (hasRole(hasRole, roles)) {
            //允许访问标签body
            return BodyTagSupport.EVAL_BODY_INCLUDE;// 返回此则执行标签body中内容，SKIP_BODY则不执行
        } else {
            return BodyTagSupport.SKIP_BODY;
        }
	}

	private boolean hasRole(String roleName, List<Role> roles) {
		if (roles != null && !roles.isEmpty()) {
			for (Role role : roles) {
				if (role.getName().equals(roleName)) {
					return true;
				}
			}
		}
		return false;
	}
	
}
