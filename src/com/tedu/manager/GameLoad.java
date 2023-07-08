package com.tedu.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;

/**
 * @˵��  ������(���ߣ��û���ȡ�����ļ��Ĺ���)������,����ṩ���� static����
 * @author renjj
 *
 */
public class GameLoad {
//	�õ���Դ������
	private static ElementManager em=ElementManager.getManager();
	
//	ͼƬ����  ʹ��map�����д洢     ö����������ƶ�(��չ)
	public static Map<String,ImageIcon> imgMap = new HashMap<>();
	
	public static Map<String,List<ImageIcon>> imgMaps;

//	�û���ȡ�ļ�����
	private static Properties pro =new Properties();	
	/**
	 * @˵�� �����ͼid�м��ط��������ļ������Զ�������ͼ�ļ����ƣ������ļ�
	 * @param mapId  �ļ���� �ļ�id
	 */
	public static void MapLoad(int mapId) {
//		�õ������ǵ��ļ�·��
		String mapName="com/tedu/text/"+mapId+".map";
//		ʹ��io������ȡ�ļ�����   �õ��������
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream maps = classLoader.getResourceAsStream(mapName);
		if(maps ==null) {
			System.out.println("�����ļ���ȡ�쳣,�����°�װ");
			return;
		}
		try {
//			�Ժ��õ� ���� xml �� json
			pro.clear();
			pro.load(maps);
//			����ֱ�Ӷ�̬�Ļ�ȡ���е�key����key�Ϳ��Ի�ȡ value
//			javaѧϰ����õ���ʦ �� java��API�ĵ���
			Enumeration<?> names = pro.propertyNames();
			while(names.hasMoreElements()) {//��ȡ�������
//				�����ĵ�������һ�����⣺һ�ε���һ��Ԫ�ء�
				String key=names.nextElement().toString();
				System.out.println(pro.getProperty(key));
//				�Ϳ����Զ��Ĵ����ͼ��� ���ǵĵ�ͼ�� 
				String [] arrs=pro.getProperty(key).split(";");
				for(int i=0;i<arrs.length;i++) {
					ElementObj obj=getObj("map");  
					ElementObj element = obj.createElement(key+","+arrs[i]);
					System.out.println(element);
					em.addElement(element, GameElement.MAPS);
				}
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *@˵�� ����ͼƬ����
	 *����ͼƬ �����ͼƬ֮��� һ�� ·������ 
	 */
	public static void loadImg() {//���Դ���������Ϊ��ͬ�Ĺ�Ҳ������Ҫ��һ����ͼƬ��Դ
		String texturl="com/tedu/text/GameData.pro";//�ļ����������Ը����й���
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(texturl);
//		imgMap���ڴ������
		pro.clear();
		try {
//			System.out.println(texts);
			pro.load(texts);
			Set<Object> set = pro.keySet();//��һ��set����
			for(Object o:set) {
				String url=pro.getProperty(o.toString());
				imgMap.put(o.toString(), new ImageIcon(url));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * �������
	 */
	public static void loadPlay() {
		loadObj();
		String playStr="200,200,paopao";//û�зŵ������ļ���
		ElementObj obj=getObj("paopao");  //��Ϊ�������������ַ�������ȡ�ʹ�������
//		����ַ�����key  Ҳ�� Ψһ id �൱��Ϊ ÿ��������һ��Ψһ��id����
//		����ַ�������һ��Ҫ�� obj.pro�е�key��ͬ
		ElementObj play = obj.createElement(playStr);
//		ElementObj play = new Play().createElement(playStr);
//		����,���ʹ���ʹ���֮�����϶� ����ֱ��ͨ�� �ӿڻ����ǳ�����Ϳ��Ի�ȡ��ʵ�����
//		ͨ�������ļ�����ϣ����ʹ������϶�
		em.addElement(play, GameElement.PLAY);
	}
	
	public static ElementObj getObj(String str) {
		try {
			Class<?> class1 = objMap.get(str);
			Object newInstance = class1.newInstance();
			if(newInstance instanceof ElementObj) {
				return (ElementObj)newInstance;   //�������ͺ� new Play()�ȼ�
//				�½�����һ����  GamePlay����
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��չ�� ʹ�������ļ�����ʵ�������� ͨ���̶���key(�ַ�����ʵ����)
	 * @param args
	 */
	private static Map<String,Class<?>> objMap=new HashMap<>();
	
	public static void loadObj() {
		String texturl="com/tedu/text/obj.pro";//�ļ����������Ը����й���
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(texturl);
		pro.clear();
		try {
			pro.load(texts);
			Set<Object> set = pro.keySet();//��һ��set����
			for(Object o:set) {
				String classUrl=pro.getProperty(o.toString());
//				ʹ�÷���ķ�ʽֱ�ӽ� ����л�ȡ
				Class<?> forName = Class.forName(classUrl);
				objMap.put(o.toString(), forName);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
//	���ڲ���
	public static void main(String[] args) {
		MapLoad(5);
		
		
		try {
//			ͨ����·�����ƣ� com.tedu.Play
			Class<?> forName = Class.forName("");
//			ͨ������  ����ֱ�ӷ��ʵ������
			Class<?> forName1=GameLoad.class;
//			ͨ��ʵ����� ��ȡ �������
			GameLoad gameLoad = new GameLoad();
			Class<? extends GameLoad> class1 = gameLoad.getClass();
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
