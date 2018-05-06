package ikaoyaner.util;

import org.springframework.context.ApplicationContext;

public class ServiceLocator
{
	private static ApplicationContext factory = null;
  
	public ServiceLocator(ApplicationContext ctx) {
		if (factory == null) {
			factory = ctx;
		}
	}
  
	public static void init(ApplicationContext ctx) {
		if (factory == null) {
			factory = ctx;
		}
	}
  
	public static Object getBean(String name) {
		if (factory == null) {
			throw new RuntimeException(
					"ServiceLocator没有被初始化，ApplicationContext为空！");
		}
		return factory.getBean(name);
	}
  
	public static <T> T getBean(Class<T> impClz) {
		return (T)factory.getBean(impClz);
	}
}
