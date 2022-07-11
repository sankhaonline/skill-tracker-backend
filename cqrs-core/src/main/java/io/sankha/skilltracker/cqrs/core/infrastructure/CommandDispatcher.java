package io.sankha.skilltracker.cqrs.core.infrastructure;

import io.sankha.skilltracker.cqrs.core.commands.BaseCommand;
import io.sankha.skilltracker.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
  <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);

  void send(BaseCommand command);
}
