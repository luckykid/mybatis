package cn.mybatis.dao;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import cn.mybatis.po.User;

public class UserDao {

	//会话工厂
	private SqlSessionFactory sqlSessionFactory;
	
	
	//创建工厂
	@Before
	public void init() throws Exception{
		//配置文件(SqlMapConfig.xml)
    	String resource="SqlMapConfig.xml";
    	//加载配置文件到输入流
    	InputStream inputStream = Resources.getResourceAsStream(resource);
    	//创建会话工厂
    	sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
//测试------------------------------------------------------------------------
	//测试根据Id查询用户
	@Test
	public void testFindUserById(){
		//通过sqlSessionFactory创建工厂
		SqlSession sqlSession= sqlSessionFactory.openSession();
		User user =null;
		try{
			/*
			 * selectOne(statement,parameter): 
			 *   statement:id
			 *   parameter：参数
			 */
			user = sqlSession.selectOne("myspace.findUserById",1);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		System.out.println(user);
	}
	
	//测试根据用户名查询用户
	@Test
	public void testFindUserByName(){
		SqlSession sqlSession= sqlSessionFactory.openSession();
		List<User> list=null;
		try{
			String name = "小明";
			list = sqlSession.selectList("myspace.findUserByName",name);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		System.out.println(list.get(1));
	}
	
	//添加用户
	@Test
	public void testInsertUser(){
		SqlSession sqlSession= sqlSessionFactory.openSession();
		User user = new User();
		user.setId(26);
		user.setUsername("ly");
		user.setSex("女");
		user.setBirthday(new Date());
		user.setAddress("shanghai");
		try{
		    int i = sqlSession.insert("myspace.insertUser", user);
		    sqlSession.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
	}
	
	//修改用户
	@Test
	public void testUpdateUser(){
		SqlSession sqlSession= sqlSessionFactory.openSession();
		User user = new User();
		user.setId(25);
		user.setUsername("陆雪琪");
		try{
			sqlSession.update("myspace.updateUser", user);
			sqlSession.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
	}
	
	//删除用户
	@Test
	public void testdeleteUser(){
		SqlSession sqlSession= sqlSessionFactory.openSession();
		try{
			sqlSession.delete("myspace.deleteUser", 31);
			sqlSession.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
	}
}