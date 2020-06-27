package com.cy.pj.common.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
/**
 * 通过@Aspect注解描述的类型，为AOP中的一种切面类型。在这种切面类型中通常要
 * 定义两部分内容：
 * 1)切入点(PointCut):在什么位置进行功能增强
 * 2)通知(Advice) :功能增强
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {
    /**
     * @Pointcut 用于定义切入点
     * bean(sysUserServiceImpl) 为切入点表达式，sysUserServiceImpl为spring
     * 容器中某个bean的名字。在当前应用中,sysUserServiceImpl代表的bean对象中
     * 所有方法的集合为切入点(这个切入点中任意的一个方法执行时，都要进行功能扩展)。
     */
	@Pointcut("bean(sysUserServiceImpl)")
	public void doPointCut() {}//方法实现不需要写任何内容
	
	/**
	 * @Around 注解用于描述通知(Advice),切面中的方法除了切入点都是通知。通知中要实现你要扩展功能。
	 * @Around 注解描述的通知为Advice中的一种环绕通知。环绕通知中可以手动调用目标方法，可以在目标
	 * 方法之前和之后进行额外业务实现。
	 * @param jp 连接点，@Around描述的方法中，参数类型要求为ProceedingJoinPoint。
	 * @return 目标业务方法的返回值，对于@Around描述的方法，其返回值类型要求为Object。
	 * @throws Throwable
	 */
	@Around("doPointCut()")//描述doPointCut()方法的@Pointcut注解中内容为切入点表达式
	//@Around("bean(sysUserServiceImpl)")
	public Object logAround(ProceedingJoinPoint jp)throws Throwable{
		   log.info("method start {}",System.currentTimeMillis());
		   try {
		   Object result=jp.proceed();//调用本类中其它通知，或下一个切面，或目标方法。
		   log.info("method end {}",System.currentTimeMillis());
		   return result;
		   }catch(Throwable e) {
		   e.printStackTrace();
		   log.error("method error {}",System.currentTimeMillis());
		   throw e;
		   }
	}
}









