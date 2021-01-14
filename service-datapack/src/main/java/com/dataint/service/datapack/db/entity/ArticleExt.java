package com.dataint.service.datapack.db.entity;

import com.dataint.cloud.common.model.Constants;
import com.dataint.service.datapack.model.form.ArticleExtForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;

@Entity
@Table(name = "article_ext")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleExt {

    public ArticleExt(ArticleExtForm articleExtForm) {
        BeanUtils.copyProperties(articleExtForm, this);

        // locations
        if (!CollectionUtils.isEmpty(articleExtForm.getLocationList()))
            this.locations = StringUtils.join(articleExtForm.getLocationList(), Constants.JOINER);
        // organizations
        if (!CollectionUtils.isEmpty(articleExtForm.getOrganizationList()))
            this.organizations = StringUtils.join(articleExtForm.getOrganizationList(), Constants.JOINER);
        // persons
        if (!CollectionUtils.isEmpty(articleExtForm.getPersonList()))
            this.persons = StringUtils.join(articleExtForm.getPersonList(), Constants.JOINER);
        // tableHtml
        if (!CollectionUtils.isEmpty(articleExtForm.getTableHtmlList()))
            this.tableHtml = StringUtils.join(articleExtForm.getTableHtmlList(), Constants.JOINER);
        // TODO: extMap
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "locations")
    private String locations;

    @Column(name = "organizations")
    private String organizations;

    @Column(name = "persons")
    private String persons;

    @Column(name = "table_html")
    private String tableHtml;  // 表格html

    @Column(name = "ext_map")
    private String extMap;  // 扩展json对象

    @Column(name = "snapshot")
    private String snapshot;  // 快照

    /**
     *
     */
    @OneToOne(mappedBy = "articleExt")
    private Article article;

    @Override
    public String toString() {
        return "ArticleExt [id=" + id + ", locations=" + locations + ", organizations=" +
                organizations + ", persons=" + persons + ", tableHtml=" + tableHtml + ", extMap=" +
                extMap + ", snapshot=" + snapshot + "]";
    }
}
