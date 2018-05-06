package ikaoyaner.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;


@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/applicationContext.xml", "file:src/main/resources/spring-mybatis.xml"})
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false) 
public class BaseTest {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 以JSON形式打印对象
	 * @param obj
	 */
	public void toJsonString(Object obj){
		logger.info("\n###################\n"+JSON.toJSON(obj).toString()+"\n###################");
	}
	
	@Test
	public void test() {
		System.out.println("this is a junit test");
	}
}
