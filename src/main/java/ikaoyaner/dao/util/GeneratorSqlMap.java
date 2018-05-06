package ikaoyaner.dao.util;

import java.io.File;

import java.util.ArrayList;

import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;

import org.mybatis.generator.config.Configuration;

import org.mybatis.generator.config.xml.ConfigurationParser;

import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratorSqlMap {

	private static Logger logger = LoggerFactory.getLogger(GeneratorSqlMap.class);
	
	public void generator() throws Exception {

		List<String> warnings = new ArrayList<String>();
		boolean overwriter = true;
		// 指定逆向工程配置文件
		File configFile = new File("src/main/java/ikaoyaner/dao/util/generatorConfig.xml");
		ConfigurationParser config = new ConfigurationParser(warnings);
		Configuration configuration = config.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwriter);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration,
				callback, warnings);
		myBatisGenerator.generate(null);

	}

	public static void main(String args[]) throws Exception {
		GeneratorSqlMap generatorSqlMap = new GeneratorSqlMap();
		generatorSqlMap.generator();
		logger.info("成功生成dao层代码");
		
	}

}