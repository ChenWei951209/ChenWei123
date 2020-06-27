package com.cy.pj.sys.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;
import com.cy.pj.sys.vo.SysUserDeptVo;

import lombok.extern.slf4j.Slf4j;
@Service
//@Slf4j
public class SysUserServiceImpl implements SysUserService {
	//private static final Logger log=
	//LoggerFactory.getLogger(SysUserServiceImpl.class);
	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public Map<String, Object> findObjectById(Integer userId) {
		//log.info("method start {}", System.currentTimeMillis());
		//1.参数校验
		if(userId==null||userId<1)
			throw new IllegalArgumentException("用户id不正确");
		//2.查询用户信息以及对应部门信息
		SysUserDeptVo user=sysUserDao.findObjectById(userId);
		if(user==null)
			throw new ServiceException("用户可能已经不存在");
		//3.查询用户对应的角色
		List<Integer> roleIds=
		sysUserRoleDao.findRoleIdsByUserId(userId);
		//4.对数据进行封装
		Map<String,Object> map=new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		//log.info("method end {} ",System.currentTimeMillis());
		return map;
	}
	
	@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		//log.info("method start {}",System.currentTimeMillis());
		//1.参数校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不允许为空");
		if(roleIds==null||roleIds.length==0)
             throw new IllegalArgumentException("必须为用户指定角色");
		//2.更新用户自身信息
		int rows=sysUserDao.updateObject(entity);
		//3.更新用户和角色关系数据
		//3.1先删除原有关系数据
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		//3.2再添加新的关系数据
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		//log.info("method end {}",System.currentTimeMillis());
		//4.返回结果
		return rows;
	}
	
	
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		//log.info("method start:"+System.currentTimeMillis());
		//1.参数校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不允许为空");
		if(StringUtils.isEmpty(entity.getPassword()))
			throw new IllegalArgumentException("密码不允许为空");
		if(roleIds==null||roleIds.length==0)
			throw new IllegalArgumentException("必须为用户指定角色");
		//2.保存用户自身信息
		//2.1对密码进行加密
		String salt=UUID.randomUUID().toString();
		//String hashedPassword=DigestUtils.md5DigestAsHex((entity.getPassword()+salt).getBytes());
		SimpleHash simpleHash=new SimpleHash(
				"MD5",//algorithmName 算法名称
				entity.getPassword(), //source原先的密码
				salt,//salt盐值
				1);//hashIterations加密次数
		String hashedPassword=simpleHash.toHex();
		//2.2将密码，盐值存储到entity对象
		entity.setSalt(salt);
		entity.setPassword(hashedPassword);
		//2.3将entity对象写入到数据库
		int rows=sysUserDao.insertObject(entity);
		//3.保存用户和角色关系数据
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		//log.info("method end:"+System.currentTimeMillis());
		//4.返回结果
		return rows;
	}
	
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		//1.参数校验
		if(id==null||id<1)
			throw new IllegalArgumentException("用户id不正确");
		if(valid!=1&&valid!=0)
			throw new IllegalArgumentException("用户状态不正确");
		//....
		//2.修改用户状态
		int rows=sysUserDao.validById(id, valid, modifiedUser);
		if(rows==0)
			throw new ServiceException("用户可能不存在");
		//3.返回结果
		return rows;
	}
	
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("页码值无效");
		//2.查询总记录数并校验
		int rowCount=sysUserDao.getRowCount(username);
		if(rowCount==0)
			throw new ServiceException("没有找到对应记录");
		//3.查询当前页要呈现的记录
		int pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysUserDeptVo> records=
		sysUserDao.findPageObjects(username, startIndex, pageSize);
		
		//4.封装结果并返回
		return new PageObject<SysUserDeptVo>(rowCount, records, pageCurrent, pageSize);
		
	}

}
