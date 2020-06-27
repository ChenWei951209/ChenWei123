package com.cy.pj.sys.service;

import java.util.Map;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;

public interface SysUserService {
	/**
	 * 基于用户id查询用户，用户对应的部门，用户对应的角色信息。
	 * @param userId
	 * @return
	 */
	Map<String,Object> findObjectById(Integer userId);
	
	/**
	 * 更新用户以及用户对应的角色关系数据
	 * @param entity
	 * @param roleIds
	 * @return
	 */
	int updateObject(SysUser entity,Integer[]roleIds);
	
	/**
	 * 保存用户以及用户对应的角色关系数据
	 * @param entity
	 * @param roleIds
	 * @return
	 */
	int saveObject(SysUser entity,Integer[]roleIds);
	
	/**
	 * 修改用户状态
	 * @param id
	 * @param valid
	 * @param modifiedUser
	 * @return
	 */
	int validById(Integer id,Integer valid,String modifiedUser);
	/**
	 * 基于条件查询用户以及用户对应的部门信息，一行记录映射为一个SysUserDeptVo对象
	 * @param username
	 * @param pageCurrent
	 * @return
	 */
	PageObject<SysUserDeptVo> findPageObjects(String username,Integer pageCurrent);
	 
}
