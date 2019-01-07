package com.cssl.sax;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.cssl.xml.Student;

public class XMLSax extends DefaultHandler{
	
	private List<Student> list;
	
	public List<Student> getList(){
		return list;
	}
	/**
	 * 开始解析XML文档时触发的事件
	 */
	@Override
	public void startDocument() throws SAXException {
		//开始解析XML文档时初始化list
		list = new ArrayList<Student>();
	}
	/**
	 * 解析完XML文档时触发的事件
	 */
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}
	/**
	 * 解析XML文档遇到开头标签时触发的事件
	 */
	//接收获取的节点名
	private String 	tagName;
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//当解析student节点时创建对象  解析student的子节点 name id sex age时将节点名保存到一个变量中
		if(qName.equals("student")) {
			Student stu=new Student();
			list.add(stu);
		}else if(qName.equals("id") || qName.equals("name")|| qName.equals("sex") || qName.equals("age")) {
			tagName=qName;
		}
	}
	/**
	 * 解析XML文档遇到结尾标签触发此事件
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		//将tagName的值设为null
		tagName=null;
	}
	/**
	 * 解析XML文档中的字符时触发的事件
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		//当list中没有值和tagName为null时跳出方法
		if(list.size()==0||tagName==null) {
			return;
		}
		//将ch数组中的值组成字符串
		String str=new String(ch,start,length);
		//取出list集合中最后一个Student对象
		Student stu=list.get(list.size()-1);
		//根据节点名判断给Student对象不同的属性赋值
		if(tagName.equals("id")) {
			stu.setId(Integer.parseInt(str));
		}else if(tagName.equals("name")) {
			stu.setName(str);
		}else if(tagName.equals("age")) {
			stu.setAge(Integer.parseInt(str));
		}else if(tagName.equals("sex")) {
			stu.setSex(str);
		}
	}
}
