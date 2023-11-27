package com.inubot.script.herbcleaner;

import com.inubot.script.herbcleaner.task.*;
import org.rspeer.commons.ArrayUtils;
import org.rspeer.commons.StopWatch;
import org.rspeer.commons.logging.Log;
import org.rspeer.event.ScriptService;
import org.rspeer.event.Subscribe;
import org.rspeer.game.adapter.component.inventory.Equipment;
import org.rspeer.game.component.InventoryType;
import org.rspeer.game.component.tdi.Skill;
import org.rspeer.game.event.ChatMessageEvent;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskScript;
import org.rspeer.game.script.meta.ScriptMeta;
import org.rspeer.game.script.meta.paint.PaintBinding;
import org.rspeer.game.script.meta.paint.PaintScheme;
import org.rspeer.game.script.meta.ui.ScriptOption;
import org.rspeer.game.script.meta.ui.ScriptUI;
import org.rspeer.game.script.tools.RestockTask;
import org.rspeer.game.service.inventory.InventoryCache;
import org.rspeer.game.service.stockmarket.StockMarketService;

import java.util.function.Supplier;

@ScriptService({InventoryCache.class, StockMarketService.class, Domain.class})
@ScriptUI(@ScriptOption(name = "Ignore herbs above X level, -1 to not use this setting", type = int.class))
@ScriptMeta(
    name = "HARAM Herb Cleaner",
    regions = -3,
    paint = PaintScheme.class,
    desc = "Cleans herbs",
    developer = "Doga",
    version = 1.01
)
public class HerbCleaner extends TaskScript {

  @PaintBinding("Runtime")
  private final StopWatch runtime = StopWatch.start();

  @PaintBinding("XP")
  private final Skill skill = Skill.HERBLORE;

  @PaintBinding("Last task")
  private final Supplier<String> task = () -> manager.getLastTaskName();

  @PaintBinding(value = "Cleaned", rate = true)
  private int cleaned = 0;

  @Override
  protected Class<? extends Task>[] tasks() {
    return ArrayUtils.getTypeSafeArray(
        StopTask.class,
        RestockTask.class,
        CleanTask.class,
        BankTask.class
    );
  }

  @Override
  public void initialize() {
    InventoryCache cache = injector.getInstance(InventoryCache.class);
    cache.submit(InventoryType.BACKPACK, 28);
    cache.submit(InventoryType.BANK, 1220);
    cache.submit(InventoryType.EQUIPMENT, Equipment.Slot.values().length);
  }

  @Subscribe
  public void notify(ChatMessageEvent event) {
    String message = event.getContents();
    if (message.contains("You clean ")) {
      cleaned++;
    } else if (message.contains("You haven't got enough")) {
      Log.info("haha broke boy");
      injector.getInstance(Domain.class).setStopping(true);
    }
  }
}
