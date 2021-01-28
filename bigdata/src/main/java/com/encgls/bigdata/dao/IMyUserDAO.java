package com.encgls.bigdata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.encgls.bigdata.dto.SimpleBbsDto;

public interface IMyUserDAO {
	
	public List<SimpleBbsDto> list();
	public SimpleBbsDto viewDao(String id);
	public int writeDao(String writer, String title, String content);
	public int deleteDao(String id);
	

}
