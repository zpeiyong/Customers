package com.dataint.monitor.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description  主题点赞表
 * @Author  Jiangxc
 * @Date 2020-06-22 
 */

@Entity
@Table ( name ="article_like" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLike implements Serializable {

	private static final long serialVersionUID =  4659693364565994319L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;  // 点赞表主键id

 	@Column(name = "user_id" )
	private Long userId;  // 点赞用户id

 	@Column(name = "article_id" )
	private Long articleId;  // 点赞主题库id

 	@Column(name = "created_time" )
	private Date createdTime;  // 点赞时间

}
