package com.inubot.script.herbcleaner.task;

import com.google.inject.Inject;
import com.inubot.script.herbcleaner.Domain;
import com.inubot.script.herbcleaner.data.Herb;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.adapter.component.inventory.Inventory;
import org.rspeer.game.component.Interfaces;
import org.rspeer.game.component.Item;
import org.rspeer.game.query.results.ItemQueryResults;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;
import org.rspeer.game.service.inventory.InventoryCache;

@TaskDescriptor(name = "Cleaning herbs")
public class CleanTask extends Task {

  private final Domain domain;
  private final InventoryCache inventory;

  @Inject
  public CleanTask(Domain domain, InventoryCache inventory) {
    this.domain = domain;
    this.inventory = inventory;
  }

  @Override
  public boolean execute() {
    Inventory inv = Inventory.backpack();
    ItemQueryResults iqr = inv.query().ids(Herb.GRIMY_IDS).results();
    if (iqr.isEmpty()) {
      return false;
    }

    if (iqr.size() < 9) {
      //We can do 9 actions per tick.
      //The trick is to withdraw 26 herbs per inventory
      //Tick 1: Clean 9 herbs
      //Tick 2: Clean 9 herbs
      //Tick 3: Clean 8 herbs and open bank
      domain.setForceBank(true);
    }

    ItemQueryResults cache = inventory.query().results();
    Item firstCached = cache.first();
    if (domain.getLastHerbId() == -1 || (firstCached != null && firstCached.getName().toLowerCase().contains("grimy"))) {
      //If we know the herb id before hand we can force an action before the components are even loaded
      domain.setLastHerbId(firstCached.getId());
    }

    int cleaned = 0;
    for (int i = 0; i < cache.size(); ++i) {
      if (cleaned == 9 || cleaned == cache.size()) {
        return true;
      }

      Item cachedItem = cache.get(i);
      if (cachedItem != null && cachedItem.getId() == domain.getLastHerbId()) {
        InterfaceComponent itemComponent = Interfaces.getDirect(149, 0, i);
        if (itemComponent != null) {
          itemComponent.interact("Clean");
          cleaned++;
        }
      }
    }

    return true;
  }
}
