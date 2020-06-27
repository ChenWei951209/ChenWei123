package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;

@RequestMapping("/user/")
@RestController
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("doFindObjectById")
	public JsonResult doFindObjectById(Integer id) {
		return new JsonResult(sysUserService.findObjectById(id));
	}
	@RequestMapping("doUpdateObject")
	public JsonResult doUpdateObject(SysUser entity,Integer[]roleIds) {
		sysUserService.updateObject(entity, roleIds);
		return new JsonResult("update ok");
	}
	@RequestMapping("doSaveObject")
	public JsonResult doSaveObject(SysUser entity,Integer[]roleIds) {
		sysUserService.saveObject(entity, roleIds);
		return new JsonResult("save ok");
	}
	
	@RequestMapping("doValidById")
	public JsonResult doValidById(Integer id,Integer valid) {
	    sysUserService.validById(id, valid, "xiaoli");//“admin”后面会是登陆用户,现在是假数据
		return new JsonResult("update ok");
	}
	
	@RequestMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String username,
			Integer pageCurrent) {
		return new JsonResult(
	    sysUserService.findPageObjects(username, pageCurrent));
	}
}
