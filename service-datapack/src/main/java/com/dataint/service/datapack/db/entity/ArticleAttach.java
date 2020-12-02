package com.dataint.service.datapack.db.entity;

import com.dataint.service.datapack.model.form.ArticleAttachForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "article_attach")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAttach implements Serializable {

    private static final long serialVersionUID = -3358787006404697718L;

    public ArticleAttach(ArticleAttachForm articleAttachForm) {
        BeanUtils.copyProperties(articleAttachForm, this);

        // attacheType
        this.attachType = articleAttachForm.getAttachType().getCode();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "article_id", nullable = false)
    private Long articleId;

    @Column(name = "attach_type", nullable = false)
    private String attachType;

    @Column(name = "attach_url", length = 1024)
    private String attachUrl;
}
