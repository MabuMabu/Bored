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
	
	//将XML文档中的数据保存到对象中
	public static List<Student> readXML(){
		//建立解析器工厂
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		//声明保存Student的集合
		List<Student> list=new ArrayList<Student>();
		try {
			//通过工厂建立解析器
			DocumentBuilder build=factory.newDocumentBuilder();
			//要解析的XML文档
			Document doc=build.parse("src/student.xml");
			//获取根节点
			Element root=doc.getDocumentElement();
			//获取该根节点的子节点集合
			NodeList no=root.getChildNodes();
			//遍历子节点集合
			for(int i=0;i<no.getLength();i++) {
				Node node=no.item(i);
				//判断是否是节点
				if(node.getNodeType()==Node.ELEMENT_NODE) {
					//建立Student对象
					Student stu=new Student();
					//将此对象添加到List<Student>中
					list.add(stu);
					//将node强转成Element
					Element e=(Element)node;
					//再获取e下的所有子节点
					NodeList de=e.getChildNodes();
					//便利de集合
					for(int j=0;j<de.getLength();j++) {
						Node node1=de.item(j);
						//判断是否是节点
						if(node1.getNodeType()==Node.ELEMENT_NODE) {
							Element e1=(Element)node1;
							//不同的值添加到不同的属性中
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

	
	//利用反射解析所有XML文档
	public static <T> List<T> fansheXML(){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		List<T> list=new ArrayList<T>();
		try {
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse("src/student.xml");
			Element root=doc.getDocumentElement();
			//获取存放再根节点上的Student的路劲
			String calssName=root.getAttribute("id");
			//利用反射获取Class
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
							//获取节点名字
							String tagName=ele.getTagName();
							//获取节点内容
							String value=ele.getTextContent();
							//获取方法名
							String methodNmae="set"+tagName.substring(0,1).toUpperCase()+tagName.substring(1);
							//利用反射获取所有方法
							Method[] methods=clazz.getMethods();
							//遍历
							for (Method med : methods) {
								//判断获取的方法是否在Student类中存在
								if(med.getName().equals(methodNmae)) {
									//声明一个Class的数组   此步骤不适用所有数据类型
									Class cls=med.getParameterTypes()[0];
									//
									Object param=cls.getConstructor(String.class).newInstance(value);
									//启用方法
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
