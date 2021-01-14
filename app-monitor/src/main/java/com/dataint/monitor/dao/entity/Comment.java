package com.dataint.monitor.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @Description  评论表
 * @Author  Jiangxc
 * @Date 2020-06-22 
 */

@Entity
@Table ( name ="comment" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BasePO {

	private static final long serialVersionUID =  2596282133756690139L;

 	@Column(name = "article_id" )
	private Long articleId;  // 舆情id

 	@Column(name = "content" )
	private String content;  // 评论内容

	@NotNull
 	@Column(name = "user_id" )
	private Long userId;  // 评论用户id
}
