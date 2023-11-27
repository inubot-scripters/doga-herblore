package com.inubot.script.herbcleaner.task;

import com.google.inject.Inject;
import com.inubot.script.herbcleaner.Domain;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(
    name = "Stopping",
    stoppable = true,
    priority = Integer.MAX_VALUE
)
public class StopTask extends Task {

  private final Domain domain;

  @Inject
  public StopTask(Domain domain) {
    this.domain = domain;
  }

  @Override
  public boolean execute() {
    return domain.isStopping();
  }
}
