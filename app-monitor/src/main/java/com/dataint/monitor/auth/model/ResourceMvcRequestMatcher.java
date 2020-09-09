package com.dataint.monitor.auth.model;

import com.google.common.base.Objects;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Getter
public class ResourceMvcRequestMatcher extends MvcRequestMatcher {

    private String pattern;
    private String method;
    private String code;

    public ResourceMvcRequestMatcher(HandlerMappingIntrospector introspector, String pattern, String method, String code) {
        super(introspector, pattern);
        this.setMethod(HttpMethod.resolve(method));
        this.pattern = pattern;
        this.method = method;
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceMvcRequestMatcher that = (ResourceMvcRequestMatcher) o;
        return Objects.equal(pattern, that.pattern) &&
                Objects.equal(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pattern, method);
    }
}
