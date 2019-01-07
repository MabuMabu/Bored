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
			//�����¼�ִ����
			XMLSax sax=new XMLSax();
			//����XML������
			XMLReader reader=XMLReaderFactory.createXMLReader();
			//����sax�¼�ִ��������
			reader.setContentHandler(sax);
			//��Ҫ�Ľ�����XML�ĵ�
			InputSource source=new InputSource(new FileInputStream("src/student.xml"));
		    //��ʼ����
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
