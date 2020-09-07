package com.dataint.cloud.common.model.param;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
public class PageParam {

    private Integer current = 1;

    private Integer pageSize = 10;

    public PageRequest toPageRequest(){
        return PageRequest.of(current -1, pageSize);
    }

    public PageRequest toPageRequest(String field) {
        return PageRequest.of(current -1, pageSize, Sort.by(field).descending());
    }

    public PageRequest toPageRequest(Sort sort) {
        return PageRequest.of(current -1, pageSize, sort);
    }

}
