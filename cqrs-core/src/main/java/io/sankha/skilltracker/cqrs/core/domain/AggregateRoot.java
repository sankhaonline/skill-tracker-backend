package io.sankha.skilltracker.cqrs.core.domain;

import io.sankha.skilltracker.cqrs.core.events.BaseEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;

public abstract class AggregateRoot {
  @Getter protected String id;

  @Getter @Setter private int version = -1;

  private final List<BaseEvent> changes = new ArrayList<>();
  private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());

  public List<BaseEvent> getUncommittedChanges() {
    return this.changes;
  }

  public void markChangesAsCommitted() {
    this.changes.clear();
  }

  protected void applyChange(BaseEvent event, boolean isNewEvent) {
    try {
      var method = getClass().getDeclaredMethod("apply", event.getClass());
      method.invoke(this, event);
    } catch (NoSuchMethodException e) {
      logger.log(
          Level.WARNING,
          MessageFormat.format(
              "The apply method was not found in the aggregate for {0}",
              event.getClass().getName()));
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Error applying event to aggregate", e);
    } finally {
      if (isNewEvent) {
        changes.add(event);
      }
    }
  }

  public void raiseEvent(BaseEvent event) {
    applyChange(event, true);
  }

  public void replayEvents(Iterable<BaseEvent> events) {
    events.forEach(event -> applyChange(event, false));
  }
}
