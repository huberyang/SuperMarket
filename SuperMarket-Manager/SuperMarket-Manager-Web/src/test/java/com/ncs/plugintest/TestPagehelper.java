package com.ncs.plugintest;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ncs.mapper.TbItemMapper;
import com.ncs.pojo.TbItem;
import com.ncs.pojo.TbItemExample;

public class TestPagehelper {

	@Test
	public void testPagehelper() {

		// 创建一个Spring IOC容器
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");

		// 由于我们已经在applicationContext文件中配置了mybatis的配置，将SqlSessionFactory对象交给Spring
		// IOC容器管理，
		// 并且我们开启了 mybatis mapper文件的扫描

		// 直接获取mapper对象
		TbItemMapper mapper = context.getBean(TbItemMapper.class);
		
		TbItemExample example =new TbItemExample();
		//分页处理 ,第一个参数代表第几页，第二个参数代表每页多少行
		PageHelper.startPage(1, 10);
		//执行操作
		List<TbItem> list = mapper.selectByExample(example);
		
		for(TbItem item: list) { 
			System.out.println(item.getTitle());
		}
		
		PageInfo<TbItem> info=new PageInfo<>(list);
		System.out.println("total:"+ info.getTotal());

	}

}
