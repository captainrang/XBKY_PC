package ikaoyaner.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ApplicationUtil.applicationContext = applicationContext;
	}
	
	public static Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}
}
