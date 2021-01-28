package com.encgls.bigdata.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.encgls.bigdata.dao.IMyUserDAO;

@Controller
public class MyController {
	
	@RequestMapping("/")
	public  String root() throws Exception{
		return "redirect:list";
	}
	
	@RequestMapping("/list")
	public  String userlistPage(Model model) throws Exception{
		//model.addAttribute("users",dao.list());

		return "/userlist";
	}
	
	@RequestMapping("/view")
	public  String view(HttpServletRequest request, Model model) throws Exception{
		String sId = request.getParameter("id");
		//model.addAttribute("dto",dao.viewDao(sId));
		return "/view";
	}
	
	@RequestMapping("/writeForm")
	public  String writeForm() throws Exception{

		return "/writeForm";
	}
	
	@RequestMapping("/write")
	public  String write(HttpServletRequest request, Model model) throws Exception{
		/*
		 * dao.writeDao(request.getParameter("writer"), request.getParameter("title"),
		 * request.getParameter("content"));
		 */

		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public  String delete(HttpServletRequest request, Model model) throws Exception{
		//dao.deleteDao(request.getParameter("id"));
		
		return "redirect:list";
	}
}
