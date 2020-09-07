package com.dataint.cloud.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Barry Song
 * @create: 2019-12-23 11:01
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {

    private Integer pageSize;
    private Long total;
    private Integer current;
}
