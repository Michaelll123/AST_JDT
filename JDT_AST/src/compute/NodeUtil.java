package compute;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import getrelation.ClassUtil;

public class NodeUtil {

	//���������ڵ�֮���ŷ�Ͼ���
	public static double getNodeEuclideanDistance(String vector1, String vector2) {
		String[] nodeVector1 = vector1.split(" ");
		String[] nodeVector2 = vector2.split(" ");
		double sum = 0;
		//������1��ʼ����0��Ϊnode������
		for(int i = 1; i<nodeVector1.length; i++) {
			double ai = Double.parseDouble(nodeVector1[i]);
			double bi = Double.parseDouble(nodeVector2[i]);
			sum += Math.pow(ai-bi, 2);
		}
		double euclideanDistance = Math.sqrt(sum);
		return euclideanDistance;
	}
	
	//ͳ������link��һ���ж��ٸ��ڵ�(�ж��ٽڵ��б�,vector������)
	public static int countNode(String inputFile) {
		//������(�ڵ�)������
		int classAmount = ClassUtil.getClassAmount();
		int[] classId = new int[classAmount];
		int count = 0;
		for(int i=0; i<classId.length; i++) {
			classId[i]=-1;
		}
		try {
			File file = new File(inputFile);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line="";
			while((line = bufferedReader.readLine()) != null) {
				String[] strId = line.split(" ");
				int id1 = Integer.parseInt(strId[0]);
				int id2 = Integer.parseInt(strId[1]);
				if(classId[id1] == -1) {
					classId[id1] = 0;
				}
				if(classId[id2] == -1) {
					classId[id2] = 0;
				}
			}
			
			bufferedReader.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<classId.length; i++) {
			if(classId[i] == 0) {
				count++;
			}
		}
		return count;
	}
	
	//ͳ��ÿ��link�Ĵ���
	public static void mergeLinkCount(String inputFilePath, String outputFilePath) {
		//������(�ڵ�)������
		int classAmount = ClassUtil.getClassAmount();
		
		/*
		 * linkMatrix����¼ÿ��link��������
		 * linkMatrix[i][j]:��Id=i�Ĺ�����Id=j�Ĺ�����(linkMatrix[i][j])��link
		 * ��ΪΪ����ߣ�i-->j��j-->i��ͬ
		 * */
		int[][] linkMatrix = new int[classAmount][classAmount];
		for(int i=0; i<linkMatrix.length; i++) {
			for(int j=0; j<linkMatrix[i].length; j++) {
				linkMatrix[i][j]=0;
			}
		}
		
		try {
			File outputFile = new File(outputFilePath);
			FileWriter fileWriter = new FileWriter(outputFile);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			File inputfile = new File(inputFilePath);
			FileReader fileReader = new FileReader(inputfile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = "";
			while((line = bufferedReader.readLine()) != null) {
				String[] strNodeId = line.split(" ");
				int id1 = Integer.parseInt(strNodeId[0]);
				int id2 = Integer.parseInt(strNodeId[1]);
				//link��������1
				linkMatrix[id1][id2]=linkMatrix[id1][id2]+1;
			}
			
			bufferedReader.close();
			
			StringBuffer stringBuffer = new StringBuffer();
			//
			for(int i=0; i<linkMatrix.length; i++) {
				for(int j=0; j<linkMatrix[i].length; j++) {
					if(linkMatrix[i][j] > 0) {
						line = i+" "+j+" "+linkMatrix[i][j];
						bufferedWriter.write(line);
						bufferedWriter.newLine();
					}
				}
			}
			
			bufferedWriter.flush();
			bufferedWriter.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
