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
	 * ��ʼ����XML�ĵ�ʱ�������¼�
	 */
	@Override
	public void startDocument() throws SAXException {
		//��ʼ����XML�ĵ�ʱ��ʼ��list
		list = new ArrayList<Student>();
	}
	/**
	 * ������XML�ĵ�ʱ�������¼�
	 */
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}
	/**
	 * ����XML�ĵ�������ͷ��ǩʱ�������¼�
	 */
	//���ջ�ȡ�Ľڵ���
	private String 	tagName;
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//������student�ڵ�ʱ��������  ����student���ӽڵ� name id sex ageʱ���ڵ������浽һ��������
		if(qName.equals("student")) {
			Student stu=new Student();
			list.add(stu);
		}else if(qName.equals("id") || qName.equals("name")|| qName.equals("sex") || qName.equals("age")) {
			tagName=qName;
		}
	}
	/**
	 * ����XML�ĵ�������β��ǩ�������¼�
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		//��tagName��ֵ��Ϊnull
		tagName=null;
	}
	/**
	 * ����XML�ĵ��е��ַ�ʱ�������¼�
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		//��list��û��ֵ��tagNameΪnullʱ��������
		if(list.size()==0||tagName==null) {
			return;
		}
		//��ch�����е�ֵ����ַ���
		String str=new String(ch,start,length);
		//ȡ��list���������һ��Student����
		Student stu=list.get(list.size()-1);
		//���ݽڵ����жϸ�Student����ͬ�����Ը�ֵ
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
