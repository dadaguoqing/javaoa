package com.hj.oa.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.commons.AjaxResultV2;
import com.hj.commons.Constants;
import com.hj.oa.bean.Menu;
import com.hj.oa.bean.MenuPermission;
import com.hj.oa.bean.Role;
import com.hj.oa.service.MenuService;
import com.hj.oa.service.RoleService;
import com.hj.oa.service.UserService;
import com.hj.util.RoleUtil;

@Controller
public class MenuController extends BaseController {
	
	private final static String REDIRECT = "redirect:";

	@Autowired
	private MenuService menuService;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@RequestMapping("oa/menu/add")
	public String addMenu(Menu menu, Model model) {

		if (menu == null || menu.getName() == null) {// 添加界面
			List<Menu> list = menuService.findMenu(null);
			model.addAttribute("list", list);
			return "oa/menu/add";
		}
		if (menu.getPid() == 0) {
			menu.setPid(null);
		}
		menuService.addMenu(menu);
		return "redirect:/web/oa/menu/list";
	}

	@RequestMapping("oa/menu/list")
	public String listMenu(Integer id, Model model) {

		if (id != null) {// 二级菜单
			Menu menu = menuService.findMenuById(id);
			model.addAttribute("menu", menu);
		}

		List<Menu> list = menuService.findMenu(id);
		model.addAttribute("list", list);

		return "oa/menu/list";
	}

	/**
	 * OA管理界面权限分配
	 */
	@RequestMapping("oa/manage")
	public String productPage(HttpSession session) {
		List<Role> roles = getLoginUserRole(session);
		// 根据菜单ID查
		List<MenuPermission> menuPers = this.menuService.findMenuPerById(Constants.MENU_MANAGE);
		for(MenuPermission menuPer : menuPers) {
			if(RoleUtil.hasRole(roles, menuPer.getRoleId())) {
				return REDIRECT + menuPer.getUrl();
			}
		}
		return "redirect:/web/oa/index";
	}
	
	
	/**
	 * OA处理界面权限分配
	 */
	@RequestMapping("oa/deal")
	public String deal(HttpSession session) {
		List<Role> roles = getLoginUserRole(session);
		List<MenuPermission> menuPers = this.menuService.findMenuPerById(Constants.MENU_DEAL);
		for(MenuPermission menuPer : menuPers) {
			if(RoleUtil.hasRole(roles, menuPer.getRoleId())) {
				return REDIRECT + menuPer.getUrl();
			}
		}
		return "redirect:/web/oa/index";
	}
	
	/**
	 * @Title: search   
	 * @Description: OA查询界面权限分配
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "oa/search", method = RequestMethod.GET)
	public String search(HttpSession session) {
		List<Role> roles = getLoginUserRole(session);
		List<MenuPermission> menuPers = this.menuService.findMenuPerById(Constants.MENU_SEARCH);
		for(MenuPermission menuPer : menuPers) {
			if(RoleUtil.hasRole(roles, menuPer.getRoleId())) {
				return REDIRECT + menuPer.getUrl();
			}
		}
		return "redirect:/web/oa/index";
	}
	
	/**
	 * @Title: menuPermission   
	 * @Description: 权限分配 
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "oa/menu/permission", method = RequestMethod.GET)
	public String menuPermission(Model model) {
		List<Role> roles = this.roleService.findAllRoles();
		List<Menu> menus = this.menuService.findAll();
		List<MenuPermission> mps = this.menuService.findAllPermissions();
		Collections.reverse(mps);
		model.addAttribute("list", mps);
		model.addAttribute("roles", roles);
		model.addAttribute("pages", menus);
		return "oa/menu/permission";
	}
	
	@RequestMapping(value = "oa/menu/addPer", method = RequestMethod.POST)
	public String addPer(Integer menuId, Integer roleId, String page) {
		Menu menu = this.menuService.findMenuById(menuId);
		Role role = this.roleService.findRoleById(roleId);
		MenuPermission mp = new MenuPermission();
		mp.setMenuId(menu.getId());
		mp.setMenuName(menu.getName());
		mp.setRoleId(role.getId());
		mp.setRoleName(role.getName());
		mp.setUrl("/" + page);
		this.menuService.insertMenuPer(mp);
		return "redirect:/web/oa/menu/permission";
	}
	
	
	@RequestMapping(value = "oa/menu/delPermission", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResultV2<T> delPermission(Integer id) {
		AjaxResultV2<T> res = new AjaxResultV2<T>();
		try {
			this.menuService.updatePermission(id);
		} catch (Exception e) {
			e.printStackTrace();
			res.setMsg(e.getMessage());
			res.setSuccess(AjaxResultV2.FAIL);
		}
		return res;
	}
	
	
	
	
	

}
