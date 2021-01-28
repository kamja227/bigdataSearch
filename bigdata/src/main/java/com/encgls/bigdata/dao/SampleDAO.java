package com.encgls.bigdata.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleDAO {
	
	// ���̺� ������ ��ü �÷� �������� 
	public List<Map<String, String>> columnList(String tableName)throws Exception;
	// ���̺��, Į�������� Į�� Ÿ�� ��������
	public List<Map<String, String>> getTableInfo(Map<String, Object> paramMap);
	// �˻� ��, ���̺� ��ü ������
	public List<Map<String, String>> selectTable(Map<String, Object> paramMap);
	// �˻� ��, ���ε� �α� ���̺� ��ü ������
	public List<Map<String, String>> selectLogTable(Map<String, Object> paramMap);
	// �˻� ��, (��õ ������) ���̺� ��ü row count
	public int cntAllRecords(Map<String, String> cntParam); // ����¡ ���� Į�� �� ����
	// �˻� ��, ���ε� �α� ���̺� ��ü row count
	public int cntUploadLogRecords(Map<String, Object> paramMap); // ����¡ ���� Į�� �� ����
	
	
	// ��õ������ ���̺� list, �����ͼ� ���̺� list
	public List<Map<String, String>> selectEncTableList(); 
	
	//�����Ľ��԰�S
	public void insertSkxInwS(Map<String,Object> paramMap)throws Exception;
	//�����Ľ��԰�B
	public void insertSkxInwB(Map<String,Object> paramMap)throws Exception;
	//�����Ľ����S
	public void insertSkxExwS(Map<String,Object> paramMap)throws Exception;
	//�����Ľ����B
	public void insertSkxExwB(Map<String,Object> paramMap)throws Exception;
	//�����Ľ���ǰS
	public void insertSkxRtnS(Map<String,Object> paramMap)throws Exception;
	//�����Ľ���ǰB
	public void insertSkxRtnB(Map<String,Object> paramMap)throws Exception;
	//���Ͼ����
	public void insertSomExw(Map<String,Object> paramMap)throws Exception;
	//���Ͼ��԰�
	public void insertSomInw(Map<String,Object> paramMap)throws Exception;
	//���Ͼƹ�ǰ
	public void insertSomRtn(Map<String,Object> paramMap)throws Exception;
	//���빰�����
	public void insertLogisExw(Map<String,Object> paramMap)throws Exception;
	//���빰���԰�
	public void insertLogisInw(Map<String,Object> paramMap)throws Exception;
	//���빰����ǰ
	public void insertLogisRtn(Map<String,Object> paramMap)throws Exception;
	//����â���ǰ
	public void insertWareRtn(Map<String,Object> paramMap)throws Exception;
	//����â�����
	public void insertWareExw(Map<String,Object> paramMap)throws Exception;
	//����â���԰�
	public void insertWareInw(Map<String,Object> paramMap)throws Exception;
	
}
