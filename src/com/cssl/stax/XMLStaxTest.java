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
		Student stu=new Student(01,"����",22,"��");
		writeXML(stu);
	}
	
	//ʹ��Stax����XML�ĵ�
	public static List<Student> readXml() throws Exception{
		//����LinkeedList����������������
		LinkedList<Student> list=new LinkedList<Student>();
		//��������XML�ĵ��Ĺ���
		XMLInputFactory factory=XMLInputFactory.newInstance();
		//��Ҫ������XML�ĵ�
		InputStream input=new FileInputStream("src/student.xml");
		//��ʼ����
		XMLStreamReader reader=factory.createXMLStreamReader(input);
		//���ñ����������սڵ���
		String tagName=null;
		//����
		while(reader.hasNext()) {
			//�õ��¼�  �õ���ǰ��һ���¼��Ĵ��롾int��
			int event=reader.next();
			//�жϴ�����ʲô�¼�   ������ͷ�ڵ㴥�����¼�XMLStreamReader.START_ELEMENT ==1
			if(event==XMLStreamReader.START_ELEMENT) {
				tagName=reader.getLocalName();
				if(tagName.equals("student")) {
					Student stu=new Student();
					list.add(stu);
				}
			}
			//�����ڵ��β�������¼�XMLStreamReader.END_ELEMENT==2
			if(event==XMLStreamReader.END_ELEMENT) {
				tagName=null;
			}
			//��������ڵ��м���ı�����,�жϽڵ����Ƿ�Ϊ��,�����Ϊ��,�Ͱ��ı����ݸ�����Ӧ������
			//XMLStreamReader.CHARACTERS==4
			if(event==XMLStreamReader.CHARACTERS) {
				//��ֵ����ѭ��
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
	
	//��Stax����һ��ȫ�µ�XML�ĵ�
	public static void writeXML(Student stu) {
		try {
			//��������XML����
			XMLOutputFactory factory=XMLOutputFactory.newInstance();
			//Ҫ����XML�ĵ���·��
			OutputStream output=new FileOutputStream("src/students.xml");
			//�½�XML�ĵ�
			XMLStreamWriter out=factory.createXMLStreamWriter(output,"UTF-8");
			//����XML�ĵ��ĸ�ʽ
			out.writeStartDocument("UTF-8", "1.0");
			out.writeCharacters("\n");
			//�������ڵ�
			out.writeStartElement("students");
			out.writeCharacters("\n\t");
			//�����ӽڵ�
			out.writeStartElement("student");
			//�����ӽڵ������
			out.writeAttribute("id", String.valueOf(stu.getId()));
			out.writeCharacters("\n\t\t");
			//������ڵ�
			out.writeStartElement("name");
			//������ڵ�name��ֵ
			out.writeCharacters(stu.getName());
			//������ڵ�Ĺرսڵ�
			out.writeEndElement();
			out.writeCharacters("\n\t\t");
			//ͬ�ϲ���
			out.writeStartElement("age");
			out.writeCharacters(String.valueOf(stu.getAge()));
			out.writeEndElement();
			out.writeCharacters("\n\t\t");
			out.writeStartElement("sex");
			out.writeCharacters(stu.getSex());
			out.writeEndElement();
			out.writeCharacters("\n\t");
			//�����ӽڵ�رսڵ�
			out.writeEndElement();
			out.writeCharacters("\n");
			//�������ڵ�رսڵ�
			out.writeEndDocument();
			out.flush();
			out.close();
			System.out.println("���!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
