package com.dataint.topic.model;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * <p>.</p>
 *
 * @author Magic Joey
 * @version PageParam.java 1.0 Created@2019-06-14 09:29 $
 */
@Getter
@Setter
public class PageParam {

    private Integer currentPage;

    private Integer pageSize;

    public PageRequest toPageRequest(){
        return PageRequest.of(currentPage-1, pageSize);
    }

    public PageRequest toPageRequest(String field) {
        return PageRequest.of(currentPage-1, pageSize, Sort.by(field).descending());
    }

    public PageRequest toPageRequest(Sort sort) {
        return PageRequest.of(currentPage-1, pageSize, sort);
    }

}
