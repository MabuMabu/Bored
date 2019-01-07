package com.cssl.stax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.cssl.xml.Student;

public class XMLStaxTest {

	public static void main(String[] args) throws Exception{
		/*List<Student> list=readXml();
		System.out.println(list.size());
		for (Student stu : list) {
			System.out.print(stu.getId());
			System.out.print(stu.getName());
			System.out.print(stu.getAge());
			System.out.println(stu.getSex());
		}*/
		Student stu=new Student(01,"王五",22,"男");
		writeXML(stu);
	}
	
	//使用Stax解析XML文档
	public static List<Student> readXml() throws Exception{
		//建立LinkeedList集合用来保存数据
		LinkedList<Student> list=new LinkedList<Student>();
		//创建解析XML文档的工厂
		XMLInputFactory factory=XMLInputFactory.newInstance();
		//需要解析的XML文档
		InputStream input=new FileInputStream("src/student.xml");
		//开始解析
		XMLStreamReader reader=factory.createXMLStreamReader(input);
		//设置变量用来接收节点名
		String tagName=null;
		//遍历
		while(reader.hasNext()) {
			//得到事件  得到当前下一个事件的代码【int】
			int event=reader.next();
			//判断触发的什么事件   遇到开头节点触发的事件XMLStreamReader.START_ELEMENT ==1
			if(event==XMLStreamReader.START_ELEMENT) {
				tagName=reader.getLocalName();
				if(tagName.equals("student")) {
					Student stu=new Student();
					list.add(stu);
				}
			}
			//遇到节点结尾触发的事件XMLStreamReader.END_ELEMENT==2
			if(event==XMLStreamReader.END_ELEMENT) {
				tagName=null;
			}
			//如果解析节点中间的文本内容,判断节点名是否为空,如果不为空,就把文本内容赋给相应的属性
			//XMLStreamReader.CHARACTERS==4
			if(event==XMLStreamReader.CHARACTERS) {
				//无值跳出循环
				if(tagName==null) {
					continue;
				}else if(tagName.equals("id")) {
					Student stu=list.getLast();
					stu.setId(Integer.parseInt(reader.getText()));
				}else if(tagName.equals("name")) {
					Student stu=list.getLast();
					stu.setName(reader.getText());
				}else if(tagName.equals("age")) {
					Student stu=list.getLast();
					stu.setAge(Integer.parseInt(reader.getText()));
				}else if(tagName.equals("sex")) {
					Student stu=list.getLast();
					stu.setSex(reader.getText());
				}
			}
		}
		return list;
	}
	
	//用Stax创建一个全新的XML文档
	public static void writeXML(Student stu) {
		try {
			//创建解析XML工厂
			XMLOutputFactory factory=XMLOutputFactory.newInstance();
			//要创建XML文档的路径
			OutputStream output=new FileOutputStream("src/students.xml");
			//新建XML文档
			XMLStreamWriter out=factory.createXMLStreamWriter(output,"UTF-8");
			//设置XML文档的格式
			out.writeStartDocument("UTF-8", "1.0");
			out.writeCharacters("\n");
			//创建根节点
			out.writeStartElement("students");
			out.writeCharacters("\n\t");
			//创建子节点
			out.writeStartElement("student");
			//创建子节点的属性
			out.writeAttribute("id", String.valueOf(stu.getId()));
			out.writeCharacters("\n\t\t");
			//创建孙节点
			out.writeStartElement("name");
			//设置孙节点name的值
			out.writeCharacters(stu.getName());
			//创建孙节点的关闭节点
			out.writeEndElement();
			out.writeCharacters("\n\t\t");
			//同上步骤
			out.writeStartElement("age");
			out.writeCharacters(String.valueOf(stu.getAge()));
			out.writeEndElement();
			out.writeCharacters("\n\t\t");
			out.writeStartElement("sex");
			out.writeCharacters(stu.getSex());
			out.writeEndElement();
			out.writeCharacters("\n\t");
			//创建子节点关闭节点
			out.writeEndElement();
			out.writeCharacters("\n");
			//创建根节点关闭节点
			out.writeEndDocument();
			out.flush();
			out.close();
			System.out.println("完成!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
