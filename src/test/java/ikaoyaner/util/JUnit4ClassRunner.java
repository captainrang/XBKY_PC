package ikaoyaner.util;

import java.io.FileNotFoundException;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

public class JUnit4ClassRunner extends SpringJUnit4ClassRunner {

	static{
		//加载log4j配置文件
		try {
			Log4jConfigurer.initLogging("src/main/resources/log4j.xml");
		} catch (FileNotFoundException  e) {
			System.err.println("Cannot Initialize log4j");
		}
	}
	
	public JUnit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

}
