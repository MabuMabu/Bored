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
		Student stu=new Student(01,"����",22,"��");
		writeXml(stu);
	}
	
	//��pull����XML�ĵ�
	public static List<Student> readXML(){
		//����LinkedList���ϱ�������
		LinkedList<Student> list=new LinkedList<Student>();
		try {
			//��������XML����
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			//ͨ��������������XML����
			XmlPullParser parser=factory.newPullParser();
			//ͨ��IO����ȡ��Ҫ����XML�ĵ���λ��
			InputStream input=new FileInputStream("src/student.xml");
			//��XML�ĵ������������
			parser.setInput(input, "UTF-8");
			//������������жϽ����Ǹ��ڵ�
			int event;
			//����������ڽ��սڵ���
			String tagName=null;
			//��do while�ж�һ�������Ľڵ��Ƿ���ɽ���   ��������˳�ѭ��
			do {
				//��ʼ��ȡ  ��ȡ�������һ���ڵ�
				event=parser.next();
				///��ȡ��ͷ�ڵ�
				if(event==XmlPullParser.START_TAG) {
					//��ȡ�ڵ���
					tagName=parser.getName();
					//�жϽڵ�
					if(tagName.equals("student")) {
						Student stu=new Student();
						list.add(stu);
					}
				}
					//��ȡ��β�ڵ�  ��tagName������ֵΪ��
					if(event==XmlPullParser.END_TAG) {
						tagName=null;
					}
					//��ȡ���ڵ��м���ı�����
					if(event==XmlPullParser.TEXT) {
						System.out.println(tagName);
						//����ȡ��ֵtagName��list����Ϊ��ʱ����ѭ��
						if(tagName==null||list.size()==0) {
							continue;
						}
						//��ȡlist���ϵ����һ������
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
	
	//ͨ��Pull�½�XML�ĵ�
	public static void writeXml(Student stu) {
		try {
			//����XML��������
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			//ͨ��������������XML�ĵ��Ĺ���
			XmlSerializer xs=factory.newSerializer();
			//ͨ���������Ҫ����XML�ĵ������ݲ���
			OutputStream out=new FileOutputStream("src/student1.xml");
			//��ʼ
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
			System.out.println("���");
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
