package com.tedu.controller;

import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

/**
 * @˵�� ��Ϸ�����̣߳����ڿ�����Ϸ���أ���Ϸ�ؿ�����Ϸ����ʱ�Զ���
 * 		��Ϸ�ж�����Ϸ��ͼ�л� ��Դ�ͷź����¶�ȡ������
 * @author renjj
 * @�̳� ʹ�ü̳еķ�ʽʵ�ֶ��߳�(һ�㽨��ʹ�ýӿ�ʵ��)
 */
public class GameThread extends Thread{
	private ElementManager em;
	
	public GameThread() {
		em=ElementManager.getManager();
	}
	@Override
	public void run() {//��Ϸ��run����  ���߳�
		while(true) { //��չ,���Խ�true��Ϊһ���������ڿ��ƽ���
//		��Ϸ��ʼǰ   ����������������Ϸ��Դ(������Դ)
			gameLoad();
//		��Ϸ����ʱ   ��Ϸ������
			gameRun();
//		��Ϸ��������  ��Ϸ��Դ����(������Դ)
			gameOver();
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * ��Ϸ�ļ���
	 */
	private void gameLoad() {
		GameLoad.loadImg(); //����ͼƬ
//		GameLoad.MapLoad(5);//���Ա�Ϊ ������ÿһ�����¼���  ���ص�ͼ
//		��������
		GameLoad.loadPlay();//Ҳ���Դ���������������2��
//		���ص���NPC��
		
//		ȫ��������ɣ���Ϸ����
	}
	/**
	 * @˵��  ��Ϸ����ʱ
	 * @����˵��  ��Ϸ��������Ҫ�������飺1.�Զ�����ҵ��ƶ�����ײ������
	 *                                 2.��Ԫ�ص�����(NPC��������ֵ���)
	 *                                 3.��ͣ�ȵȡ���������
	 * ��ʵ�����ǵ��ƶ�
	 * */
	
	private void gameRun() {
		long gameTime=0L;//��int���;Ϳ�����
		while(true) {// Ԥ����չ   true���Ա�Ϊ���������ڿ��ƹܹؿ�������
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			List<ElementObj> enemys = em.getElementsByKey(GameElement.ENEMY);
			List<ElementObj> files = em.getElementsByKey(GameElement.PLAYFILE);
			List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
			moveAndUpdate(all,gameTime);//	��ϷԪ���Զ�������
			
			ElementPK(enemys,files);
			ElementPK(files,maps);
			
			gameTime++;//Ψһ��ʱ�����
			try {
				sleep(10);//Ĭ�����Ϊ 1��ˢ��100�� 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void ElementPK(List<ElementObj> listA,List<ElementObj>listB) {
//		����������ʹ��ѭ������һ��һ�ж������Ϊ�棬������2�����������״̬
		for(int i=0;i<listA.size();i++) {
			ElementObj enemy=listA.get(i);
			for(int j=0;j<listB.size();j++) {
				ElementObj file=listB.get(j);
				if(enemy.pk(file)) {
//					���⣺ �����boos����ôҲһǹһ���𣿣�����
//					�� setLive(false) ��Ϊһ���ܹ��������������Դ�������һ������Ĺ�����
//					���չ���������ִ��ʱ�����Ѫ����Ϊ0 �ٽ�����������Ϊ false
//					��չ �������
					System.out.println(listB);
					enemy.setLive(false);
					file.setLive(false);
					break;
				}
			}
		}
	}
	
	
	
	
//	��ϷԪ���Զ�������
	public void moveAndUpdate(Map<GameElement, List<ElementObj>> all,long gameTime) {
//		GameElement.values();//���ط���  ����ֵ��һ������,�����˳����Ƕ���ö�ٵ�˳��
		for(GameElement ge:GameElement.values()) {
			List<ElementObj> list = all.get(ge);
//			��д����ֱ�Ӳ����������ݵĴ��뽨�鲻Ҫʹ�õ�������
//			for(int i=0;i<list.size();i++) {
			for(int i=list.size()-1;i>=0;i--){	
				ElementObj obj=list.get(i);//��ȡΪ����
				if(!obj.isLive()) {//�������
//					list.remove(i--);  //����ʹ�������ķ�ʽ
//					����һ����������(�����п�������������:�������� ,��װ��)
					obj.die();//��Ҫ����Լ�����
					list.remove(i);
					continue;
				}
				obj.model(gameTime);//���õ�ģ�巽�� ����move
			}
		}	
	}
	

	
	/**��Ϸ�л��ؿ�*/
	private void gameOver() {
		
	}
	
}





