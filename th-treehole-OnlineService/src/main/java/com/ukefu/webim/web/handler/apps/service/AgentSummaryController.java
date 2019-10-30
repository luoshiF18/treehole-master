/*
package com.ukefu.webim.web.handler.apps.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.core.UKDataContext;
import com.ukefu.util.Menu;
import com.ukefu.util.UKTools;
*/
/*import com.ukefu.util.task.export.ExcelExporterProcess;*//*

import com.ukefu.webim.service.repository.AgentServiceRepository;
import com.ukefu.webim.service.repository.ContactsRepository;
import com.ukefu.webim.service.repository.ServiceSummaryRepository;
import com.ukefu.webim.web.handler.Handler;
import com.ukefu.webim.web.model.AgentService;
import com.ukefu.webim.web.model.AgentServiceSummary;
import com.ukefu.webim.web.model.Contacts;

@Controller
@RequestMapping("/apps/agent/summary")
public class AgentSummaryController extends Handler{
	
	@Autowired
	private ServiceSummaryRepository serviceSummaryRes ;
	
	*/
/*@Autowired
	private MetadataRepository metadataRes ;*//*

	
	@Autowired
	private AgentServiceRepository agentServiceRes ;
	
	*/
/*@Autowired
	private TagRepository tagRes ;*//*

	
	@Autowired
	private ContactsRepository contactsRes ;
	
	*/
/**
	 * 按条件查询
	 * @param map
	 * @param request
	 * @param
	 * @param
	 * @param begin
	 * @param end
	 * @param
	 * @return
	 *//*

	@RequestMapping(value = "/index")
    @Menu(type = "agent" , subtype = "agentsummary" , access = false)
    public ModelAndView index(ModelMap map , HttpServletRequest request , @Valid final String begin , @Valid final String end ) {
		Page<AgentServiceSummary> page = serviceSummaryRes.findAll(new Specification<AgentServiceSummary>(){
			@Override
			public Predicate toPredicate(Root<AgentServiceSummary> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();  
				list.add(cb.equal(root.get("process").as(boolean.class), 0)) ;
				list.add(cb.notEqual(root.get("channel").as(String.class), UKDataContext.ChannelTypeEnum.PHONE.toString())) ;
				try {
					if(!StringUtils.isBlank(begin) && begin.matches("[\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}:[\\d]{2}")){
						list.add(cb.greaterThan(root.get("createtime").as(Date.class), UKTools.dateFormate.parse(begin))) ;
					}
					if(!StringUtils.isBlank(end) && end.matches("[\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}:[\\d]{2}")){
						list.add(cb.lessThan(root.get("createtime").as(Date.class), UKTools.dateFormate.parse(end))) ;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Predicate[] p = new Predicate[list.size()];  
			    return cb.and(list.toArray(p));  
			}}, new PageRequest(super.getP(request), super.getPs(request) , Sort.Direction.DESC, "createtime")) ;
		map.addAttribute("summaryList", page) ;
		map.addAttribute("begin", begin) ;
		map.addAttribute("end", end) ;
		
*/
/*
		map.addAttribute("tags", tagRes.findByOrgiAndTagtype(super.getOrgi(request) , UKDataContext.ModelType.SUMMARY.toString())) ;
*//*


    	return request(super.createAppsTempletResponse("/apps/service/summary/index"));
    }
	
	@RequestMapping(value = "/process")
    @Menu(type = "agent" , subtype = "agentsummary" , access = false)
    public ModelAndView process(ModelMap map , HttpServletRequest request , @Valid final String id) {
		AgentServiceSummary summary = serviceSummaryRes.findByIdAndOrgi(id, super.getOrgi(request)) ;
		map.addAttribute("summary",summary) ;
*/
/*
		map.put("summaryTags", tagRes.findByOrgiAndTagtype(super.getOrgi(request) , UKDataContext.ModelType.SUMMARY.toString())) ;
*//*

		if(summary!=null && !StringUtils.isBlank(summary.getAgentserviceid())){
			AgentService service = agentServiceRes.findByIdAndOrgi(summary.getAgentserviceid(), super.getOrgi(request)) ;
			map.addAttribute("service",service) ;
			if(!StringUtils.isBlank(summary.getContactsid())){
				Contacts contacts = contactsRes.findOne(summary.getContactsid()) ;
				map.addAttribute("contacts",contacts) ;
			}
		}
		
		return request(super.createRequestPageTempletResponse("/apps/service/summary/process"));
	}
	
	@RequestMapping(value = "/save")
    @Menu(type = "agent" , subtype = "agentsummary" , access = false)
    public ModelAndView save(ModelMap map , HttpServletRequest request , @Valid final AgentServiceSummary summary) {
		AgentServiceSummary oldSummary = serviceSummaryRes.findByIdAndOrgi(summary.getId(), super.getOrgi(request)) ;
		if(oldSummary!=null){
			oldSummary.setProcess(true);
			oldSummary.setUpdatetime(new Date());
			oldSummary.setUpdateuser(super.getUser(request).getId());
			oldSummary.setProcessmemo(summary.getProcessmemo());
			serviceSummaryRes.save(oldSummary) ;
		}
		
		return request(super.createRequestPageTempletResponse("redirect:/apps/agent/summary/index.html"));
	}
	
	*/
/* @RequestMapping("/expids")
	    @Menu(type = "agent" , subtype = "agentsummary" , access = false)
	    public void expids(ModelMap map , HttpServletRequest request , HttpServletResponse response , @Valid String[] ids) throws IOException {
	    	if(ids!=null && ids.length > 0){
	    		Iterable<AgentServiceSummary> statusEventList = serviceSummaryRes.findAll(Arrays.asList(ids)) ;
	    		MetadataTable table = metadataRes.findByTablename("uk_servicesummary") ;
	    		List<Map<String,Object>> values = new ArrayList<Map<String,Object>>();
	    		for(AgentServiceSummary event : statusEventList){
	    			values.add(UKTools.transBean2Map(event)) ;
	    		}
	    		
	    		response.setHeader("content-disposition", "attachment;filename=UCKeFu-Summary-History-"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".xls");  
	    		
	    		ExcelExporterProcess excelProcess = new ExcelExporterProcess( values, table, response.getOutputStream()) ;
	    		excelProcess.process();
	    	}
	    	
	        return ;
	    }*//*

	    
	   */
/* @RequestMapping("/expall")
	    @Menu(type = "agent" , subtype = "agentsummary" , access = false)
	    public void expall(ModelMap map , HttpServletRequest request , HttpServletResponse response) throws IOException {
	    	Iterable<AgentServiceSummary> statusEventList = serviceSummaryRes.findByChannelAndOrgi(UKDataContext.ChannelTypeEnum.PHONE.toString() , super.getOrgi(request) , new PageRequest(0, 10000));
	    	
	    	MetadataTable table = metadataRes.findByTablename("uk_servicesummary") ;
			List<Map<String,Object>> values = new ArrayList<Map<String,Object>>();
			for(AgentServiceSummary statusEvent : statusEventList){
				values.add(UKTools.transBean2Map(statusEvent)) ;
			}
			
			response.setHeader("content-disposition", "attachment;filename=UCKeFu-Summary-History-"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".xls");  
			
			ExcelExporterProcess excelProcess = new ExcelExporterProcess( values, table, response.getOutputStream()) ;
			excelProcess.process();
	        return ;
	    }
	    *//*

	   */
/* @RequestMapping("/expsearch")
	    @Menu(type = "agent" , subtype = "agentsummary" , access = false)
	    public void expall(ModelMap map , HttpServletRequest request  , HttpServletResponse response ,  @Valid final String begin , @Valid final String end ) throws IOException {
	    	
	    	Page<AgentServiceSummary> page = serviceSummaryRes.findAll(new Specification<AgentServiceSummary>(){
				@Override
				public Predicate toPredicate(Root<AgentServiceSummary> root, CriteriaQuery<?> query,
						CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<Predicate>();  
					list.add(cb.and(cb.equal(root.get("process").as(boolean.class), 0))) ;
					list.add(cb.and(cb.notEqual(root.get("channel").as(String.class), UKDataContext.ChannelTypeEnum.PHONE.toString()))) ;
					try {
						if(!StringUtils.isBlank(begin) && begin.matches("[\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}:[\\d]{2}")){
							list.add(cb.and(cb.greaterThan(root.get("createtime").as(Date.class), UKTools.dateFormate.parse(begin)))) ;
						}
						if(!StringUtils.isBlank(end) && end.matches("[\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}:[\\d]{2}")){
							list.add(cb.and(cb.lessThan(root.get("createtime").as(Date.class), UKTools.dateFormate.parse(end)))) ;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Predicate[] p = new Predicate[list.size()];  
				    return cb.and(list.toArray(p));  
				}}, new PageRequest(0, 10000 , Sort.Direction.DESC, "createtime")) ;
	    	
	    	List<Map<String,Object>> values = new ArrayList<Map<String,Object>>();
	    	for(AgentServiceSummary summary : page){
	    		values.add(UKTools.transBean2Map(summary)) ;
	    	}

	    	response.setHeader("content-disposition", "attachment;filename=UCKeFu-Summary-History-"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".xls");  

	    	MetadataTable table = metadataRes.findByTablename("uk_servicesummary") ;
	    	
	    	ExcelExporterProcess excelProcess = new ExcelExporterProcess( values, table, response.getOutputStream()) ;
	    	excelProcess.process();
	    	
	        return ;
	    }*//*

}
*/
