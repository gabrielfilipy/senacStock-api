package com.senac.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@Builder
public class Problema {

private Integer status;
	
	private String type; 
	
	private String title;
	
	private String detail;
	
	private String userMessage;
	
	private OffsetDateTime timeStamp;
	
	private List<Object> fields;
	
	@Getter
	@Setter
	@Builder 
	public static class Object {
		
		private String userMessage;
		private String name;
		
	}
	
}
