package com.inubot.script.herbcleaner;

import com.google.inject.Singleton;
import com.inubot.script.herbcleaner.commons.SilentService;
import org.rspeer.event.Subscribe;
import org.rspeer.game.event.TickEvent;

@Singleton
public class Domain implements SilentService {

  private boolean stopping = false;
  private boolean forceBank = false;
  private int lastHerbId = -1;
  private int tick = 0;

  public boolean isStopping() {
    return stopping;
  }

  public void setStopping(boolean stopping) {
    this.stopping = stopping;
  }

  public boolean isForceBank() {
    return forceBank;
  }

  public void setForceBank(boolean forceBank) {
    this.forceBank = forceBank;
  }

  public int getLastHerbId() {
    return lastHerbId;
  }

  public void setLastHerbId(int lastHerbId) {
    this.lastHerbId = lastHerbId;
  }

  public int ticksSince(int tick) {
    return this.tick - tick;
  }

  @Subscribe
  public void tick(TickEvent event) {
    tick++;
  }
}
