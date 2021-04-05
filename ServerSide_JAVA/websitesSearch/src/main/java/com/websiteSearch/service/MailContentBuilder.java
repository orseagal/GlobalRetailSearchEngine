package com.websiteSearch.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.websiteSearch.entity.Element;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailContentBuilder {
	 private final TemplateEngine templateEngine;
					
		
	    public String build(String message) {
	        Context context = new Context();
	        context.setVariable("message", message);
	        return templateEngine.process("mailTemplate", context);
	    }
	    
	    
	    public String buildWithList(String message, ArrayList<Element> items) {
	        Context context = new Context();
	        context.setVariable("message", message);
	        context.setVariable("allItems", items);
	        return templateEngine.process("mailTemplateWithList", context);
	    }
}
