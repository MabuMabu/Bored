package com.cssl.xml;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLManger {

	public static void main(String[] args) {
		/*List<Student> stu=readXML();
		for (Student s1 : stu) {
			System.out.print(s1.getId());
			System.out.print(s1.getName());
			System.out.print(s1.getAge());
			System.out.println(s1.getSex());
		}*/
		XMLManger manager=new XMLManger();
		List<Student> list=manager.readXML();
		for (Student stu : list) {
			System.out.print(stu.getId());
			System.out.print(stu.getName());
			System.out.print(stu.getAge());
			System.out.println(stu.getSex());
		}
	}
	
	//��XML�ĵ��е����ݱ��浽������
	public static List<Student> readXML(){
		//��������������
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		//��������Student�ļ���
		List<Student> list=new ArrayList<Student>();
		try {
			//ͨ����������������
			DocumentBuilder build=factory.newDocumentBuilder();
			//Ҫ������XML�ĵ�
			Document doc=build.parse("src/student.xml");
			//��ȡ���ڵ�
			Element root=doc.getDocumentElement();
			//��ȡ�ø��ڵ���ӽڵ㼯��
			NodeList no=root.getChildNodes();
			//�����ӽڵ㼯��
			for(int i=0;i<no.getLength();i++) {
				Node node=no.item(i);
				//�ж��Ƿ��ǽڵ�
				if(node.getNodeType()==Node.ELEMENT_NODE) {
					//����Student����
					Student stu=new Student();
					//���˶�����ӵ�List<Student>��
					list.add(stu);
					//��nodeǿת��Element
					Element e=(Element)node;
					//�ٻ�ȡe�µ������ӽڵ�
					NodeList de=e.getChildNodes();
					//����de����
					for(int j=0;j<de.getLength();j++) {
						Node node1=de.item(j);
						//�ж��Ƿ��ǽڵ�
						if(node1.getNodeType()==Node.ELEMENT_NODE) {
							Element e1=(Element)node1;
							//��ͬ��ֵ��ӵ���ͬ��������
							if(e1.getTagName().equals("id")) {
								stu.setId(Integer.parseInt(e1.getTextContent()));
							}else if(e1.getTagName().equals("name")) {
								stu.setName(e1.getTextContent());
							}else if(e1.getTagName().equals("sex")) {
								stu.setSex(e1.getTextContent());
							}else if(e1.getTagName().equals("age")) {
								stu.setAge(Integer.parseInt(e1.getTextContent()));
							}
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	
	//���÷����������XML�ĵ�
	public static <T> List<T> fansheXML(){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		List<T> list=new ArrayList<T>();
		try {
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse("src/student.xml");
			Element root=doc.getDocumentElement();
			//��ȡ����ٸ��ڵ��ϵ�Student��·��
			String calssName=root.getAttribute("id");
			//���÷����ȡClass
			Class clazz=Class.forName(calssName);
			NodeList node=root.getChildNodes();
			for(int i=0;i<node.getLength();i++) {
				Node no=node.item(i);
				if(no.getNodeType()==Node.ELEMENT_NODE) {
					T obj=(T)clazz.newInstance();
					list.add(obj);
					Element e=(Element)no;
					NodeList ls=e.getChildNodes();
					for(int j=0;j<ls.getLength();j++) {
						Node de=ls.item(j);
						if(de.getNodeType()==Node.ELEMENT_NODE) {
							Element ele=(Element)de;
							//��ȡ�ڵ�����
							String tagName=ele.getTagName();
							//��ȡ�ڵ�����
							String value=ele.getTextContent();
							//��ȡ������
							String methodNmae="set"+tagName.substring(0,1).toUpperCase()+tagName.substring(1);
							//���÷����ȡ���з���
							Method[] methods=clazz.getMethods();
							//����
							for (Method med : methods) {
								//�жϻ�ȡ�ķ����Ƿ���Student���д���
								if(med.getName().equals(methodNmae)) {
									//����һ��Class������   �˲��費����������������
									Class cls=med.getParameterTypes()[0];
									//
									Object param=cls.getConstructor(String.class).newInstance(value);
									//���÷���
									med.invoke(obj, param);
								}
							}
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return list;
	}
}
