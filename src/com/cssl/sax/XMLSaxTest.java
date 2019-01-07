package com.cssl.sax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.cssl.xml.Student;

public class XMLSaxTest {

	public static void main(String[] args) {
		try {
			//创建事件执行器
			XMLSax sax=new XMLSax();
			//创建XML解析器
			XMLReader reader=XMLReaderFactory.createXMLReader();
			//设置sax事件执行器对象
			reader.setContentHandler(sax);
			//需要的解析的XML文档
			InputSource source=new InputSource(new FileInputStream("src/student.xml"));
		    //开始解析
			reader.parse(source);
			List<Student> list=sax.getList();
			System.out.println(list.size());
			for (Student stu : list) {
				System.out.print(stu.getId());
				System.out.print(stu.getName());
				System.out.print(stu.getAge());
				System.out.println(stu.getSex());
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
