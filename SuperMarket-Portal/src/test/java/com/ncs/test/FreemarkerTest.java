package com.ncs.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class FreemarkerTest {
	
	@Test
	public void testFreemarker() throws IOException, TemplateException {
		
		//freemarker proj no need to run depanded web container,so that you can build a java file to run it
		
		//create congiguration object for the freemarker
		Configuration config=new Configuration(Configuration.getVersion());
		//tould the configuration where is the ftl template files
		config.setDirectoryForTemplateLoading(new File("D:\\Myself\\Git Workspace\\SuperMarket\\SuperMarket-Portal\\src\\main\\webapp\\WEB-INF\\ftl"));
		//set encoding 
		config.setDefaultEncoding("UTF-8");
		//specify the file name of the template file
		Template template = config.getTemplate("first.ftl");
		//create the template file's data list
		Map dataList=new HashMap<>();
		dataList.put("firstTitle", "FirstFreemarker");
		dataList.put("welcome", "Hello Freemarker");
		
		//create a write object to specify file path for the generated file
		Writer writer=new FileWriter(new File("D:\\Myself\\Html\\hello.html"));
		
		//call the template object process function and to params is need
		template.process(dataList, writer);
		
		//flush the data steam and close writer steam
		writer.flush();
		writer.close();
	}

}
