package com.trex.controller.board;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trex.controller.CalendarData;
import com.trex.dto.CalendarVO;
import com.trex.dto.EPViewVO;
import com.trex.dto.EmployeeVO;
import com.trex.dto.GmemberVO;
import com.trex.dto.MemberVO;
import com.trex.dto.TroupeVO;
import com.trex.service.CalendarService;
import com.trex.service.MemberService;


@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	private MemberService mservice;
	
	@Autowired
	private CalendarService calService;
	
	
	@RequestMapping(value="/myinfo")
	public void myinfoGET() {
	}
	
	
	
	@RequestMapping(value="/eplist" )
	public void listGET( Model model ) throws Exception {
		List<EPViewVO> eplist = mservice.getEmployeeList();
		model.addAttribute("eplist", eplist);
		
	}
	
	@RequestMapping(value="/epregist", method= RequestMethod.GET )
	public void epregistGET( ) throws Exception {
		
	}
	
	@RequestMapping(value = "/epregist", method = RequestMethod.POST)
	public String epregistPOST(MemberVO member, EmployeeVO employee, HttpServletResponse response) throws Exception {
		
		mservice.regist(member,employee);
		
		response.setContentType("text/html;charset=utf-8");
		
		return "redirect:/manager/eplist";
	}
	
	@RequestMapping(value = "/epdelete")
	public String epdeletrPOST(String code, HttpServletResponse response) throws Exception {
		
		mservice.delete(code);
		response.setContentType("text/html;charset=utf-8");
		
		return "redirect:/manager/eplist";
	}
	
	
	
	
	
	@RequestMapping(value="/memlist")
	public void memlistGET(Model model) throws Exception {
		List<GmemberVO> glist = mservice.getGmemberList();
		List<TroupeVO> tlist = mservice.getTroupeList();
		List<MemberVO> gmemlist = mservice.getMemberListlike("GM");
		List<MemberVO> tmemlist = mservice.getMemberListlike("TR");
		model.addAttribute("glist", glist);
		model.addAttribute("tlist",  tlist);
		model.addAttribute("gmemlist",  gmemlist);
		model.addAttribute("tmemlist",  tmemlist);
		
		System.out.println(gmemlist);
		
	}
	
	@RequestMapping(value="/calendar")
	public void calenderGET() {
	}
	
	@RequestMapping(value="/calendar/list",method=RequestMethod.GET)
	@ResponseBody
	public List<CalendarData> list()throws Exception{
		List<CalendarVO> calendarList=calService.calenderlist();
		
		List<CalendarData> dataList = new ArrayList<CalendarData>();
		for(CalendarVO calendar : calendarList) {
			dataList.add(new CalendarData(calendar));			
		}
		
		return dataList;
		
	}
	
	@RequestMapping(value = "/calendar/list", method = RequestMethod.POST)
	public ResponseEntity<String> registCal(@RequestBody CalendarVO calendar) throws Exception {

		ResponseEntity<String> entity = null;

		try {
			calService.create(calendar);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return entity;

	}
	
	
	@RequestMapping(value = "/calendar/regist", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody CalendarVO calendar) throws Exception {

		ResponseEntity<String> entity = null;

		try {
			calService.create(calendar);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return entity;

	}
	
	
	
	
	
	
	@ModelAttribute("submenuTitle")
	public String submenuTitle() {
		return "관리자!!";
	}
	
	
	
	@ModelAttribute("submenuList")
	public List<String[]> submenuModel(){
		List<String[]> submenuList = new ArrayList<String[]> ();
		
		submenuList.add(new String[] {"내정보","myinfo"});
		submenuList.add(new String[] {"직원관리","eplist"});
		submenuList.add(new String[] {"회원관리","memlist"});
		submenuList.add(new String[] {"일정","calendar"});
		
		return submenuList;
	}


}
