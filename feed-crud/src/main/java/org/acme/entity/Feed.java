package org.acme.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/*
 * panache entities needs public attribute to work
 */
@Entity
@Table(name = "feed")
public class Feed extends PanacheEntity{
	
		
	public String name;
	
	public String url;
	
	public String description;
	
	public boolean deleted;
	
	public Feed() {
	}

	public Feed(String name, String url, String description, boolean deleted) {
		super();
		this.name = name;
		this.url = url;
		this.description = description;
		this.deleted = deleted;
	}
	
}
