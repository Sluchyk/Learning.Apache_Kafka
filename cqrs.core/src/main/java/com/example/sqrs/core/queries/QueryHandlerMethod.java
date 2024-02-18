package com.example.sqrs.core.queries;

import com.example.sqrs.core.domaine.BaseEntity;
import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod <T extends BaseQuery>{
    List<BaseEntity> handle(T query);
}
