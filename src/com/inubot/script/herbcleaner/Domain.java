package com.inubot.script.herbcleaner;

import com.google.inject.Singleton;
import com.inubot.script.herbcleaner.commons.SilentService;
import org.rspeer.event.Subscribe;
import org.rspeer.game.event.TickEvent;
import org.rspeer.game.script.event.ScriptConfigEvent;

@Singleton
public class Domain implements SilentService {

  private boolean stopping = false;
  private boolean forceBank = false;
  private int lastHerbId = -1;
  private int tick = 0;

  private int ignoreLevel = -1;

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

  public int getIgnoreLevel() {
    return ignoreLevel;
  }

  @Subscribe
  public void tick(TickEvent event) {
    tick++;
  }

  @Subscribe
  public void configure(ScriptConfigEvent event) {
    this.ignoreLevel = event.getSource().getInteger("Ignore herbs above X level, -1 to not use this setting");
  }
}
