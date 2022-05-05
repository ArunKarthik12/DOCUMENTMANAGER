package com.documentmanger.documentManager.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Doc {
	
 @Id
 private String name;
 private String desc;
 
 
 
public Doc() {
	super();
	// TODO Auto-generated constructor stub
}

public Doc(String name, String desc) {
	super();
	this.name = name;
	this.desc = desc;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
 
 
}
