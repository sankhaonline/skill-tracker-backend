package io.sankha.skilltracker.cqrs.core.queries;

import io.sankha.skilltracker.cqrs.core.domain.BaseEntity;
import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
  List<BaseEntity> handle(T query);
}
