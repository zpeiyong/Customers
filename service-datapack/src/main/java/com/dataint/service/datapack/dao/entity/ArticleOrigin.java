package com.dataint.service.datapack.dao.entity;

import com.dataint.cloud.common.model.Constants;
import com.dataint.service.datapack.model.form.ArticleOriginForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;

@Entity
@Table(name = "article_origin")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleOrigin {

    public ArticleOrigin(ArticleOriginForm articleOriginForm) {
        BeanUtils.copyProperties(articleOriginForm, this);

        // keywords
        if (!CollectionUtils.isEmpty(articleOriginForm.getOrigKeywordList()))
            this.origKeywords = StringUtils.join(articleOriginForm.getOrigKeywordList(), Constants.JOINER);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "orig_title")
    private String origTitle;

    @Column(name = "orig_author", length = 32)
    private String origAuthor;

    @Column(name = "orig_editor", length = 32)
    private String origEditor;

    @Column(name = "orig_summary", length = 1000)
    private String origSummary;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "orig_content", columnDefinition = "MediumText")
    private String origContent;

    @Column(name = "orig_keywords", length = 500)
    private String origKeywords;

    /**
     *
     */
    @OneToOne(mappedBy = "articleOrigin")
    private Article article;

    @Override
    public String toString() {
        return "ArticleOrigin [id=" + id + ", origTitle=" + origTitle + ", origAuthor=" +
                origAuthor + ", origEditor=" + origEditor + ", origSummary=" + origSummary + ", origContent=" +
                origContent + ", origKeywords=" + origKeywords + "]";
    }
}
