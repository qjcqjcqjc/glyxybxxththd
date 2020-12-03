package com.glyxybxhtxt.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class GetSqlSession {
	private GetSqlSession(){}
	
	private static SqlSessionFactory  fac = null;
	
	public static SqlSession getSession() throws IOException{
		if(fac==null)
		{
			String resource = "mybatis-config.xml";
	        Reader reader = Resources.getResourceAsReader(resource);
	        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	        fac = builder.build(reader);
		}
		return fac.openSession();
	}
	
}
