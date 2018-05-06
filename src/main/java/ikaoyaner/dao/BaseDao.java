package ikaoyaner.dao;

import org.mybatis.spring.SqlSessionTemplate;

public class BaseDao {
	
	private SqlSessionTemplate sqlSession;

	public SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	

}
