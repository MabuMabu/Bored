package com.cssl.pull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.cssl.xml.Student;

public class XMLPull {

	public static void main(String[] args) {
		/*List<Student> list=readXML();
		for (Student stu : list) {
			System.out.print(stu.getId());
			System.out.print(stu.getName());
			System.out.print(stu.getAge());
			System.out.println(stu.getSex());
		}*/
		Student stu=new Student(01,"王五",22,"男");
		writeXml(stu);
	}
	
	//用pull解析XML文档
	public static List<Student> readXML(){
		//创建LinkedList集合保存数据
		LinkedList<Student> list=new LinkedList<Student>();
		try {
			//创建解析XML工厂
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			//通过工厂产生解析XML工具
			XmlPullParser parser=factory.newPullParser();
			//通过IO流获取需要解析XML文档的位置
			InputStream input=new FileInputStream("src/student.xml");
			//将XML文档放入解析工具
			parser.setInput(input, "UTF-8");
			//定义变量用于判断进入那个节点
			int event;
			//定义变量用于接收节点名
			String tagName=null;
			//用do while判断一个完整的节点是否完成解析   解析完成退出循环
			do {
				//开始读取  读取完进入下一个节点
				event=parser.next();
				///读取开头节点
				if(event==XmlPullParser.START_TAG) {
					//获取节点名
					tagName=parser.getName();
					//判断节点
					if(tagName.equals("student")) {
						Student stu=new Student();
						list.add(stu);
					}
				}
					//读取结尾节点  将tagName变量赋值为空
					if(event==XmlPullParser.END_TAG) {
						tagName=null;
					}
					//读取到节点中间的文本内容
					if(event==XmlPullParser.TEXT) {
						System.out.println(tagName);
						//当获取的值tagName和list集合为空时跳出循环
						if(tagName==null||list.size()==0) {
							continue;
						}
						//获取list集合的最后一个对象
						Student stu=list.getLast();
						if(tagName.equals("id")) {
							stu.setId(Integer.valueOf(parser.getText()));
						}else if(tagName.equals("name")) {
							stu.setName(parser.getText());
						}else if(tagName.equals("sex")) {
							stu.setSex(parser.getText());
						}else if(tagName.equals("age")) {
							stu.setAge(Integer.valueOf(parser.getText()));
						}
					}
			}while(event!=XmlPullParser.END_DOCUMENT);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//通过Pull新建XML文档
	public static void writeXml(Student stu) {
		try {
			//创建XML解析工厂
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			//通过工厂建立创建XML文档的工具
			XmlSerializer xs=factory.newSerializer();
			//通过输出流将要插入XML文档的内容插入
			OutputStream out=new FileOutputStream("src/student1.xml");
			//开始
			xs.setOutput(out, "UTF-8");
			xs.startDocument(null, true);
			xs.startTag(null, "students");
			xs.startTag(null, "student");
			xs.attribute(null, "id", String.valueOf(stu.getId()));
			xs.startTag(null, "name");
			xs.text(stu.getName());
			xs.endTag(null, "name");
			xs.startTag(null, "sex");
			xs.text(stu.getSex());
			xs.endTag(null, "sex");
			xs.startTag(null, "age");
			xs.text(String.valueOf(stu.getAge()));
			xs.endTag(null, "age");
			xs.endTag(null, "student");
			xs.endTag(null, "students");
			xs.endDocument();
			out.flush();
			out.close();
			System.out.println("完成");
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
