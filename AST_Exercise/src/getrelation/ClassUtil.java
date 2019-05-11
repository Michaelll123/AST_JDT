package getrelation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xmlutil.ClassEntity;
import xmlutil.JaxbUtil;
import xmlutil.ListEntity;

public class ClassUtil {
	
	public static String outputId = "4";
	public static String filePath = "src/output/eTOUR/XMLObject3.xml";

	//��Xml�ļ��ж�ȡString���͵�Xml
	public static String getXmlString(String filePath){
		StringBuffer stringBuffer = new StringBuffer();
		String line = "";
		String xml = "";
		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			xml = stringBuffer.toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return xml;
		
	}
	
	//��String���͵�Xmlת��ΪJava���󣬲�����ClassList
	public static List<ClassEntity> getClassList(String xmlString){
		List<ClassEntity> classList = new ArrayList<>();
		ListEntity listEntity = JaxbUtil.xmlToBean(xmlString, ListEntity.class);
		classList = listEntity.getClassList();
		return classList;
	}
	
	//��xmlд���ļ�
	public static void writeXmlToFile(String xml, String outputFilePath) {
		File outputFile = new File(outputFilePath);
		try {
			FileWriter fileWriter = new FileWriter(outputFile);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(xml);
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//����className���classId
	public static long getClassId(List<ClassEntity> classListParam, String className) {
		for(ClassEntity classEntity : classListParam) {
			if(classEntity.getClassName() == null) {
				continue;
			}
			if(classEntity.getClassName().equals(className)) {
				return classEntity.getId();
			}
		}
		return -1;
	}
	
	//��ȡ�ļ����µ������ļ�(�ݹ鷽��)
	public static List<String> getFileList(String strPath) {
		ArrayList<String> filelist = new ArrayList<String>();
        File dir = new File(strPath);
        // ���ļ�Ŀ¼���ļ�ȫ����������
        File[] files = dir.listFiles(); 
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                // �ж����ļ������ļ���
                if (files[i].isDirectory()) { 
                	// ��ȡ�ļ�����·��
                    filelist.addAll(getFileList(files[i].getAbsolutePath())); 
                } 
                // �ж��ļ����Ƿ���.txt��β
                else if (fileName.endsWith("txt")) { 
                    String strFileName = files[i].getAbsolutePath();
//                    System.out.println("---" + strFileName);
                    filelist.add(strFileName);
                } 
                else {
                    continue;
                }
                
            }

        }
        return filelist;
    }
	
	//������NodeId�ļ��ϲ���һ���ļ�
	public static void mergeNodeIdFile(List<String> inputFileList, String outputFilePath) {
		try {
			File outputFile = new File(outputFilePath);
//			FileWriter fileWriter = new FileWriter(outputFile, true);
			FileWriter fileWriter = new FileWriter(outputFile);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			File inputfile;
			FileReader fileReader;
			BufferedReader bufferedReader;
//			StringBuffer stringBuffer = new StringBuffer();
			String line = "";
			for(String file : inputFileList) {
				//ÿ��һ���ļ�
				inputfile = new File(file);
				fileReader = new FileReader(inputfile);
				bufferedReader = new BufferedReader(fileReader);
				while((line = bufferedReader.readLine()) != null) {
//					stringBuffer.append(line);
					bufferedWriter.write(line);
					bufferedWriter.newLine();
				}
				//ÿдһ���ļ�
//				bufferedWriter.write(stringBuffer.toString());
//				bufferedWriter.write("----------");
//				bufferedWriter.newLine();
				
//				bufferedWriter.write("----------");
//				bufferedWriter.newLine();
				
			}
			bufferedWriter.flush();
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//����������
	public static int getClassAmount() {
		String xmlString = ClassUtil.getXmlString(ClassUtil.filePath);
		List<ClassEntity> classList = ClassUtil.getClassList(xmlString);
		int classAmount = classList.size();
		return classAmount;
	}
	
}
