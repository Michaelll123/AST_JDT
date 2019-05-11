package getrelation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xmlutil.ClassEntity;
import xmlutil.JaxbUtil;
import xmlutil.MethodEntity;

public class ClassMethodRelation {

	private String outputId;
	private String outputDirectory;
	private String outputReturnXmlFile;
	private String outputReturnTxtFile;
	private String outputParamXmlFile;
	private String outputParamTxtFile;
	private String outputExceptionXmlFile;
	private String outputExceptionTxtFile;
	
	public ClassMethodRelation() {
		this.outputId = ClassUtil.outputId;
		this.outputDirectory = "E:/Eclipse/AST/AST_Exercise/src/output/eTOUR/";
//		this.outputReturnXmlFile = this.outputDirectory+"Return/xml/classMethodReturn"+this.outputId+".xml";
//		this.outputReturnTxtFile = this.outputDirectory+"Return/txt/nodeMethodReturn"+this.outputId+".txt";
//		this.outputParamXmlFile = this.outputDirectory+"Param/xml/classMethodParameter"+this.outputId+".xml";
//		this.outputParamTxtFile = this.outputDirectory+"Param/txt/nodeMethodParameter"+this.outputId+".txt";
//		this.outputExceptionXmlFile = this.outputDirectory+"Exception/xml/classMethodException"+this.outputId+".xml";
//		this.outputExceptionTxtFile = this.outputDirectory+"Exception/txt/nodeMethodException"+this.outputId+".txt";
		
		this.outputReturnXmlFile = this.outputDirectory+"classMethodReturn"+this.outputId+".xml";
		this.outputReturnTxtFile = this.outputDirectory+"nodeMethodReturn"+this.outputId+".txt";
		this.outputParamXmlFile = this.outputDirectory+"classMethodParameter"+this.outputId+".xml";
		this.outputParamTxtFile = this.outputDirectory+"nodeMethodParameter"+this.outputId+".txt";
		this.outputExceptionXmlFile = this.outputDirectory+"classMethodException"+this.outputId+".xml";
		this.outputExceptionTxtFile = this.outputDirectory+"nodeMethodException"+this.outputId+".txt";
	}
	
	//��÷���ֵ��ϵ����������ļ�
	public ClassRelationListEntity getClassMethodReturnRelation(List<ClassEntity> classListParam) {
		ClassRelationListEntity classRelationListEntity = new ClassRelationListEntity();
		List<ClassRelationEntity> classRelationList = new ArrayList<>();
		for(ClassEntity classEntity : classListParam) {
			String class1Name = classEntity.getClassName();
			List<MethodEntity> methodList = classEntity.getMethod();
			
			//�õ�����ֵ��Ϣ
			for(MethodEntity methodEntity : methodList) {
				String returnType = methodEntity.getReturnType();
				if(returnType == null || returnType.equals("")) {
					continue;
				}
				
				ClassRelationEntity classRelationEntity = new ClassRelationEntity();
				classRelationEntity.setClass1(class1Name);
				classRelationEntity.setClass2(returnType);
				classRelationEntity.setRelationType("return");
				classRelationList.add(classRelationEntity);
				
			}
			
		}
		classRelationListEntity.setRelationList(classRelationList);
		
		//���xml���ļ�
		String xml = JaxbUtil.beanToXml(classRelationListEntity);
		String outputFile = this.outputReturnXmlFile;
		ClassUtil.writeXmlToFile(xml, outputFile);
		
		return classRelationListEntity;
	}
	
	//��ò�����ϵ����������ļ�
	public ClassRelationListEntity getClassMethodParameterRelation(List<ClassEntity> classListParam) {
		ClassRelationListEntity classRelationListEntity = new ClassRelationListEntity();
		List<ClassRelationEntity> classRelationList = new ArrayList<>();
		for(ClassEntity classEntity : classListParam) {
			String class1Name = classEntity.getClassName();
			List<MethodEntity> methodList = classEntity.getMethod();
			
			//��ò�����Ϣ
			for(MethodEntity methodEntity : methodList) {
				List<String> parameterList = methodEntity.getParametersList();
				if(parameterList == null || parameterList.size() == 0) {
					continue;
				}
				for(String parameterType : parameterList) {
					if(parameterType == null || parameterType.equals("")) {
						continue;
					}
					ClassRelationEntity classRelationEntity = new ClassRelationEntity();
					classRelationEntity.setClass1(class1Name);
					classRelationEntity.setClass2(parameterType);
					classRelationEntity.setRelationType("parameter");
					classRelationList.add(classRelationEntity);
				}
			}
			
			
		}
		classRelationListEntity.setRelationList(classRelationList);
		
		//���xml���ļ�
		String xml = JaxbUtil.beanToXml(classRelationListEntity);
		String outputFile = this.outputParamXmlFile;
		ClassUtil.writeXmlToFile(xml, outputFile);
		
		return classRelationListEntity;
	}
	
	//����׳��쳣��ϵ����������ļ�
	public ClassRelationListEntity getClassMethodExceptionRelation(List<ClassEntity> classListParam) {
		ClassRelationListEntity classRelationListEntity = new ClassRelationListEntity();
		List<ClassRelationEntity> classRelationList = new ArrayList<>();
		for(ClassEntity classEntity : classListParam) {
			String class1Name = classEntity.getClassName();
			List<MethodEntity> methodList = classEntity.getMethod();
			
			//����׳��쳣����Ϣ
			for(MethodEntity methodEntity : methodList) {
				List<String> exceptionList = methodEntity.getThrowException();
				if(exceptionList == null || exceptionList.size() == 0) {
					continue;
				}
				for(String exceptionType : exceptionList) {
					if(exceptionType == null || exceptionType.equals("")) {
						continue;
					}
					ClassRelationEntity classRelationEntity = new ClassRelationEntity();
					classRelationEntity.setClass1(class1Name);
					classRelationEntity.setClass2(exceptionType);
					classRelationEntity.setRelationType("throwException");
					classRelationList.add(classRelationEntity);
				}
			}
			
			
		}
		classRelationListEntity.setRelationList(classRelationList);
		
		//���xml���ļ�
		String xml = JaxbUtil.beanToXml(classRelationListEntity);
		String outputFile = this.outputExceptionXmlFile;
		ClassUtil.writeXmlToFile(xml, outputFile);
		
		return classRelationListEntity;
	}
	
	
	//��ȡclass֮������Թ�ϵ(Id)
	public void getNodeMethodRelation(List<ClassEntity> classListParam, 
			ClassRelationListEntity classInheritListEntity, String fileType) {
		long id1;
		long id2;
//		long type=-1;
		List<ClassRelationEntity> classRelationList = classInheritListEntity.getRelationList();
		
		String outputFile = "";
		
		if(fileType.equals("Return")) {
			outputFile = this.outputReturnTxtFile;
		}
		if(fileType.equals("Parameter")) {
			outputFile = this.outputParamTxtFile;
		}
		if(fileType.equals("Exception")) {
			outputFile = this.outputExceptionTxtFile;
		}
		
		File file = new File(outputFile);
		try {
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			for(ClassRelationEntity classRelationEntity : classRelationList) {
				
				String class2 = classRelationEntity.getClass2();
				id2 = ClassUtil.getClassId(classListParam, class2);
				//���û���ҵ����࣬�Ͳ��ü�¼
				if(id2 == -1) {
					continue;
				}
				
				String class1 = classRelationEntity.getClass1();
				id1 = ClassUtil.getClassId(classListParam, class1);
				
//				String relationType = classRelationEntity.getRelationType();
//				if(relationType.equals("return")) {
//					type = 4;
//				}
//				if(relationType.equals("parameter")) {
//					type = 5;
//				}
//				if(relationType.equals("throwException")) {
//					type = 6;
//				}
				
//				bufferedWriter.write(String.valueOf(id1)+"\t"+
//						String.valueOf(id2)+"\t"+String.valueOf(type));
				bufferedWriter.write(String.valueOf(id1)+" "+
						String.valueOf(id2));
				bufferedWriter.newLine();
				
			}
			bufferedWriter.flush();
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ClassMethodRelation classMethodRelation = new ClassMethodRelation();
		String xmlString = ClassUtil.getXmlString(ClassUtil.filePath);
		List<ClassEntity> classList = ClassUtil.getClassList(xmlString);
		
		//���ɷ���ֵ�ļ�
		ClassRelationListEntity classRelationListEntity = classMethodRelation.getClassMethodReturnRelation(classList);
		classMethodRelation.getNodeMethodRelation(classList, classRelationListEntity, "Return");
		
		//���ɲ����ļ�
		classRelationListEntity = classMethodRelation.getClassMethodParameterRelation(classList);
		classMethodRelation.getNodeMethodRelation(classList, classRelationListEntity, "Parameter");
		
		//�����׳��쳣�ļ�
		classRelationListEntity = classMethodRelation.getClassMethodExceptionRelation(classList);
		classMethodRelation.getNodeMethodRelation(classList, classRelationListEntity, "Exception");
	}
}
