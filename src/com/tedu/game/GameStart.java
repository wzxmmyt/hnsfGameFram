package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

public class GameStart {
	/**
	 * �����Ψһ���
	 */
	public static void main(String[] args) {
		GameJFrame gj=new GameJFrame();
		/**ʵ������壬ע�뵽jframe��*/
		GameMainJPanel jp=new GameMainJPanel();	
//		ʵ��������
		GameListener listener=new GameListener();
//		ʵ�������߳�
		GameThread th=new GameThread();
//		ע��
		gj.setjPanel(jp);
		gj.setKeyListener(listener);
		gj.setThead(th);
		
		gj.start();
		
	}

}

/**
 * 1.������Ϸ�������Ϸ�� �����ļ���ʽ���ļ���ȡ��ʽ��load��ʽ��
 * 2.�����Ϸ��ɫ��������Ϸ����(������ڻ���ļ̳�)
 * 3.����pojo��(Vo)....
 * 4.��Ҫ�ķ������ڸ�������д(������಻֧�֣����Բ����޸ĸ���)
 * 5.������ã���ɶ���� load��add��Manage.
 * 6.��ײ�ȵ�ϸ�ڴ��롣
 * 
 * web��ҳ��Ϸ
 */
















