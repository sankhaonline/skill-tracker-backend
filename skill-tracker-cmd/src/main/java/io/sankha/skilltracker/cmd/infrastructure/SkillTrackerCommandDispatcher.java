package io.sankha.skilltracker.cmd.infrastructure;

import io.sankha.skilltracker.cqrs.core.commands.BaseCommand;
import io.sankha.skilltracker.cqrs.core.commands.CommandHandlerMethod;
import io.sankha.skilltracker.cqrs.core.infrastructure.CommandDispatcher;
import io.sankha.skilltracker.exception.BusinessException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class SkillTrackerCommandDispatcher implements CommandDispatcher {

  private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes =
      new HashMap<>();

  @Override
  public <T extends BaseCommand> void registerHandler(
      Class<T> type, CommandHandlerMethod<T> handler) {
    var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
    handlers.add(handler);
  }

  @Override
  public void send(BaseCommand command) {
    var handlers = routes.get(command.getClass());
    if (CollectionUtils.isEmpty(handlers)) {
      throw new BusinessException("No command handler was registered!");
    }
    if (handlers.size() > 1) {
      throw new BusinessException("Cannot send command to more than one handler!");
    }
    handlers.get(0).handle(command);
  }
}
