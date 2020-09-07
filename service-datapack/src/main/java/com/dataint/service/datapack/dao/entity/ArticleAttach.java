package com.dataint.service.datapack.dao.entity;

import com.dataint.service.datapack.model.form.ArticleAttachForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity
@Table(name = "article_attach")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAttach {

    public ArticleAttach(ArticleAttachForm articleAttachForm) {
        BeanUtils.copyProperties(articleAttachForm, this);

        // attacheType
        this.attachType = articleAttachForm.getAttachType().getCode();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "article_id", nullable = false)
    private Integer articleId;

    @Column(name = "attach_type", nullable = false, length = 10)
    private String attachType;

    @Column(name = "attach_url", length = 1000)
    private String attachUrl;
}
