package com.example.accounts.queries.infrustructe;

import com.example.sqrs.core.domaine.BaseEntity;
import com.example.sqrs.core.queries.BaseQuery;
import com.example.sqrs.core.queries.QueryHandlerMethod;
import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> queryHandlerMethod);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
