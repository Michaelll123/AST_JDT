package exercise1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import xmlutil.ClassEntity;
import xmlutil.FieldEntity;
import xmlutil.MethodEntity;

//����class�ڵ�İ��������������ԡ�����
public class DemoVisitor extends ASTVisitor {
	
//	private String outputFilePath = "E:/Eclipse/AST/AST_Exercise/src/output/result3.txt";
	private FileWriter fileWriter = null;
	private BufferedWriter bufferedWriter = null;
	
	private ClassEntity classEntity;
	
	private ArrayList<FieldEntity> fieldList;
	private ArrayList<MethodEntity> methodList;
	
	public DemoVisitor() {
		super();
		this.classEntity = new ClassEntity();
		this.fieldList = new ArrayList<>();
		this.methodList = new ArrayList<>();
	}
	
	//���ʰ���
	public boolean visit(PackageDeclaration node) {
		
		/*
		try {
			fileWriter = new FileWriter(outputFilePath, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			packageMap.put("Package", node.getName().toString());
			
//			String packageStr = "{\"Package\":" + node.getName() + ",";
			bufferedWriter.write(packageStr);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("package:\t" + node.getName());
		*/
		
		classEntity.setPackageName(node.getName().toString());
		return true;
		
		
	}
	
	//��������
	@Override
	public boolean visit(TypeDeclaration node) {

		/*
		try {
			fileWriter = new FileWriter(outputFilePath, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			classMap.put("Class", node.getName().toString());
			String classStr = "\"Class\":" + node.getName() + ",";
			String superClassStr = "\"SuperClass\":" + node.getSuperclassType() + ",";
			bufferedWriter.write(classStr);
			bufferedWriter.newLine();
			bufferedWriter.write(superClassStr);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Class:\t" + node.getName());
//		System.out.println("SuperClass:\t" + node.getSuperclassType());
 * 
 */
		//��ȡ����
		classEntity.setClassName(node.getName().toString());
		
		//��ȡ����
		Type superClassType = node.getSuperclassType();
		if(superClassType == null) {
			classEntity.setSuperClassName("");
		}
		else {
			//�������Ϊ����
			if(superClassType.isParameterizedType()) {
				ParameterizedType parameterizedType = (ParameterizedType) superClassType;
				String parameterizedType1 = parameterizedType.getType().toString();
//				String parameterizedType2 = parameterizedType.typeArguments().get(0).toString();
				classEntity.setSuperClassName(parameterizedType1);
			}
			else {
				classEntity.setSuperClassName(superClassType.toString());
			}
		}
		//System.out.println("node.superInterfaceTypes()-------"+node.superInterfaceTypes());
		
		//��ȡʵ�ֵĽӿ�
		List<String> superInterface = new ArrayList<>();
		List<Type> superInterfaceList = node.superInterfaceTypes();
		if(superInterfaceList == null || superInterfaceList.size() == 0) {
			superInterface.add("");
		}
		else {
			for(Type superInterfaceType : superInterfaceList) {
				
				
//				System.out.println("isParameterizedType---"+type.isParameterizedType());
//				System.out.println("type.toString()---"+type.toString());
				//���ʵ�ֵĽӿ�Ϊ����
				if(superInterfaceType.isParameterizedType()) {
					ParameterizedType parameterizedType = (ParameterizedType) superInterfaceType;
//					System.out.println("parameterizedType1---"+parameterizedType.getType());
//					System.out.println("parameterizedType2---"+parameterizedType.typeArguments().get(0));
//					System.out.println("parameterizedTypeSize---"+parameterizedType.typeArguments().size());
					String parameterizedType1 = parameterizedType.getType().toString();
					String parameterizedType2 = parameterizedType.typeArguments().get(0).toString();
					superInterface.add(parameterizedType1);
					superInterface.add(parameterizedType2);
				}
				else {
					superInterface.add(superInterfaceType.toString());
				}
			}
		}
		
		classEntity.setSuperInterface(superInterface);
		return true;
	}

	//��������
	@Override
	public boolean visit(FieldDeclaration node) {
		
//		try {
//			fileWriter = new FileWriter(outputFilePath, true);
//			bufferedWriter = new BufferedWriter(fileWriter);
			
			for (Object obj: node.fragments()) {
				VariableDeclarationFragment v = (VariableDeclarationFragment)obj;
//				fieldStr += v.getName();
//				fieldStr += ",";
//				System.out.println(fieldStr);
				Type fieldType = node.getType();
				//������Ե�����Ϊ����
				if(fieldType.isParameterizedType()) {
					ParameterizedType parameterizedType = (ParameterizedType) fieldType;
					String parameterizedType1 = parameterizedType.getType().toString();
					String parameterizedType2 = parameterizedType.typeArguments().get(0).toString();
					FieldEntity fieldEntity1 = new FieldEntity();
					fieldEntity1.setFieldName(v.getName().toString());
					fieldEntity1.setFieldType(parameterizedType1);
					fieldList.add(fieldEntity1);
					FieldEntity fieldEntity2 = new FieldEntity();
					fieldEntity2.setFieldName(v.getName().toString());
					fieldEntity2.setFieldType(parameterizedType2);
					fieldList.add(fieldEntity2);
				}
				else {
					FieldEntity fieldEntity = new FieldEntity();
					fieldEntity.setFieldName(v.getName().toString());
					fieldEntity.setFieldType(fieldType.toString());
					fieldList.add(fieldEntity);
				}
				
				
			}
//			bufferedWriter.write(fieldStr);
//			bufferedWriter.newLine();
//			bufferedWriter.flush();
//			bufferedWriter.close();
			
			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return true;
	}
 
	//���ʷ���
	@Override
	public boolean visit(MethodDeclaration node) {

//		try {
//			fileWriter = new FileWriter(outputFilePath, true);
//			bufferedWriter = new BufferedWriter(fileWriter);
//			String methodStr = "Method:\t" + node.getName();
//			System.out.println(methodStr);
//			bufferedWriter.write(methodStr);
//			bufferedWriter.newLine();
//			bufferedWriter.flush();
//			bufferedWriter.close();
		
			
			MethodEntity methodEntity = new MethodEntity();
			//������
			methodEntity.setMethodName(node.getName().toString());
			
			//��������ֵ
			Type returnType = node.getReturnType2();
			if(returnType == null) {
				methodEntity.setReturnType("");
			}
			else {
				//�������ֵΪ����
				if(returnType.isParameterizedType()) {
					ParameterizedType parameterizedType = (ParameterizedType) returnType;
					String parameterizedType2 = parameterizedType.typeArguments().get(0).toString();
					methodEntity.setReturnType(parameterizedType2);
				}
				else {
					methodEntity.setReturnType(returnType.toString());
				}
				
			}
			
			//��������
			List<String> paramters = new ArrayList<>();
			List<SingleVariableDeclaration> parameterList = node.parameters();
			//System.out.println("parameterList---------"+parameterList);
			if(parameterList == null || parameterList.size() == 0) {
				paramters.add("");
			}
			else {
				for(SingleVariableDeclaration SingleVariableDeclaration : parameterList) {
					Type paramterType = SingleVariableDeclaration.getType();
					//�����������Ϊ����
					if(paramterType.isParameterizedType()) {
						ParameterizedType parameterizedType = (ParameterizedType) paramterType;
						String parameterizedType2 = parameterizedType.typeArguments().get(0).toString();
						paramters.add(parameterizedType2);
						
					}
					else {
						paramters.add(paramterType.toString());
					}
					
				}
			}
			methodEntity.setParametersList(paramters);
			
			//�����׳����쳣
			List<String> exceptions = new ArrayList<>();
			List<Type> exceptionList = node.thrownExceptionTypes();
			if(exceptionList == null || exceptionList.size() == 0) {
				exceptions.add("");
			}
			else {
				for(Type type : exceptionList) {
					exceptions.add(type.toString());
				}
			}
			methodEntity.setThrowException(exceptions);
			
			methodList.add(methodEntity);
			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		return true;
	}
 
	
	
	

	public ClassEntity getClassEntity() {
		return classEntity;
	}

	public void setClassEntity(ClassEntity classEntity) {
		this.classEntity = classEntity;
	}

	public ArrayList<FieldEntity> getFieldList() {
		return fieldList;
	}

	public void setFieldList(ArrayList<FieldEntity> fieldList) {
		this.fieldList = fieldList;
	}

	public ArrayList<MethodEntity> getMethodList() {
		return methodList;
	}

	public void setMethodList(ArrayList<MethodEntity> methodList) {
		this.methodList = methodList;
	}
	
	
}
