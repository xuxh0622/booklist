package com.alice.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 设置通知
 * Joinpoint：连接点，通知触发的点
 * Pointcut：一系列连一个接点的集合
 * Advice：通知，在连接点处扩展的操作
 * Target Object：目标对象，被通知或代理的对象
 * Aspect：一个编程思想，通过通知或拦截器实现
 * @autor xuxhm
 *
 */
@Configuration
@EnableAspectJAutoProxy
@Aspect
@Component
public class Adivisor {
	
	@Pointcut
	public void test(){
		System.out.println("test");
	}

	@Before(value="test()")
	public void before(){
		System.out.println("before");
	}
	
	@After(value="test()")
	public void after(){
		System.out.println("after");
	}
	
	@After(value="test()")
	public void around(ProceedingJoinPoint p) throws Throwable{
		System.out.println("around before");
		p.proceed();
		System.out.println("around after");
	}
	
	@After(value="test()")
	public void finnaly(){
		System.out.println("finnaly");
	}
	
	public static void main(String[] args) {
		new Adivisor().test();
	}
}
