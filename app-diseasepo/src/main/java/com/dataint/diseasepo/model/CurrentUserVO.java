package com.dataint.diseasepo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class CurrentUserVO {
    private Long id;

    private String username;

    private String name;

    private Set<String> currentAuthority;
}
