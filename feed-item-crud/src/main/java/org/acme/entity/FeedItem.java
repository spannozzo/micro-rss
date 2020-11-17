package org.acme.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@NamedNativeQueries({
    @NamedNativeQuery(
    name = "findItemByTitleAndPublishedDate",
    query = "select * from item item where item.title =:title and item.date =:date ",
        resultClass = FeedItem.class
    )
})

@Entity
@Table(name = "item")
public class FeedItem extends PanacheEntity {
	@Column(columnDefinition="TEXT")
	public String title;
	
	public LocalDateTime date;
	
	@Column(columnDefinition="TEXT")
	public String description;
	
	@Column(columnDefinition="TEXT")
	public String imagePath;
	
	public String feedRef;
}
