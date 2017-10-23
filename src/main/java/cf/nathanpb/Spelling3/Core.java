package cf.nathanpb.Spelling3;

import cf.nathanpb.Spelling3.book.BookArea;
import cf.nathanpb.Spelling3.book.BookItem;
import cf.nathanpb.Spelling3.commands.Spelling;
import cf.nathanpb.Spelling3.entity.SpellingPlayer;
import cf.nathanpb.Spelling3.eventHandler.*;
import cf.nathanpb.Spelling3.events.EventBus;
import cf.nathanpb.Spelling3.item.ItemInitializator;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.recipes.SpellingRecipe;
import cf.nathanpb.Spelling3.structures.SpellTable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.Reflections;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nathanpb on 9/10/17.
 */
public class Core extends JavaPlugin{
    public static File PluginDatabase = new File("plugins/Spelling3");
    public static File PlayerDatabase = new File(PluginDatabase, "PlayerDatabase");
    public static ArrayList<SpellingRecipe> RegisteredRecipes = new ArrayList<>();

    //Preciso que essa merda fique aqui pra poder usar na lambida
    private static int phase = 1;
    private final static String prefix = ChatColor.LIGHT_PURPLE+"[SPELLING]";

    private static Phase[] Enable = new Phase[]{
            new Phase("Setting up directories", () -> {
                if(!PlayerDatabase.exists()) PlayerDatabase.mkdirs();
                if(!PluginDatabase.exists()) PluginDatabase.mkdirs();
            }),
            new Phase("Setting up Bukkit commands", () -> Core.getInstance().getCommand("spelling").setExecutor(new Spelling())),
            new Phase("Setting up Bukkit listeners", () -> {
            Bukkit.getServer().getPluginManager().registerEvents(new ManaOnHit(), getInstance());
            Bukkit.getServer().getPluginManager().registerEvents(new InventoryClick(), getInstance());
            Bukkit.getServer().getPluginManager().registerEvents(new VillagerSpawn(), getInstance());
            Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteract(), getInstance());
        }),
            new Phase("Setting up Spelling Items", () -> {
                new Reflections("cf.nathanpb.Spelling3").getSubTypesOf(SpellingItem.class).stream()
                        .filter(c -> c.isAnnotationPresent(ItemInitializator.class))
                        .sorted((c1, c2) -> {
                            if(c1.getAnnotation(ItemInitializator.class).priority() > c2.getAnnotation(ItemInitializator.class).priority()) return 1;
                            if(c1.getAnnotation(ItemInitializator.class).priority() < c2.getAnnotation(ItemInitializator.class).priority()) return -1;
                            return 0;
                        }).forEach(SpellingItem::register);

                SpellingItem.getItems().stream().filter(i -> i instanceof BookItem).forEach(i -> ((BookItem)i).getArea().addItem(i));

            }),
            new Phase("Setting up Burnout System", () -> {
                new BukkitRunnable(){
                    public void run(){
                        for(Player p : Bukkit.getOnlinePlayers()){
                            SpellingPlayer pl = new SpellingPlayer(p);
                            pl.sendActionbar(pl.getInfoBar());
                            if(pl.getBurnout() > 0) {
                                if(pl.getBurnout() - pl.getLevel() >= 0) {
                                    pl.addBurnout(-pl.getLevel());
                                }else{
                                    pl.setBurnout(0);
                                }
                            }
                        }
                    }
                }.runTaskTimerAsynchronously(getInstance(), 40, 0);
            }),
            new Phase("Setting up Mage's Guide", () -> SpellingItem.getItems().stream().filter(e -> e instanceof BookArea).forEach(e -> ((BookArea)e).setup())),

            //todo Fazer os selfs
            new Phase("Setting up Self Spells", () -> {
                 /*
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()){
                    SpellingPlayer sp = new SpellingPlayer(p);
                    sp.applySelf(sp.getActiveSelfs());
                }
            }
        }.runTaskTimerAsynchronously(getInstance(), 40, 0);
        */
            }).setWip(true)
    };

    private static Phase[] Disable = new Phase[]{
            new Phase("Unregistering target", ()-> HandlerList.unregisterAll()),
            new Phase("Disassembling Spell Tables", () -> SpellTable.tableList.stream().forEach(SpellTable::dropAll))
    };

    @Override
    public void onEnable() {
        super.onEnable();

        long time = System.currentTimeMillis();
        try {
            Arrays.stream(Enable).forEach(i -> {
                long time2 = System.currentTimeMillis();
                consoleMessage(ChatColor.YELLOW+"["+phase+"/"+Enable.length+"] "+i.getMessage());
                i.run();
                consoleMessage(ChatColor.GREEN + "[" + phase + "/" + Enable.length + "] DONE IN " + (System.currentTimeMillis() - time2) + "ms");
                phase++;
            });
        }catch (Exception e){
            consoleMessage(ChatColor.RED+"\nInitialization failed ("+phase+"/"+ Enable.length+", "+(System.currentTimeMillis()-time)+"ms)");
            Bukkit.getPluginManager().disablePlugin(getInstance());
            e.printStackTrace();
        }
        consoleMessage(ChatColor.GREEN+"Initialized in "+(System.currentTimeMillis()-time)+"ms");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Arrays.stream(Disable).forEach(Phase::run);
    }

    public static JavaPlugin getInstance(){
        return (JavaPlugin) Bukkit.getServer().getPluginManager().getPlugin("Spelling3");
    }
    public static <T>List<T> getRecipesFor(Class<T> clz){
        List<T> list = new ArrayList<>();
        RegisteredRecipes.stream().filter(r -> r.getClass().isAssignableFrom(clz
        )).forEach(r -> list.add((T)r));
        return list;
    }
    public static void consoleMessage(String message){
        Bukkit.getServer().getConsoleSender().sendMessage(prefix+ChatColor.RESET+" "+message);
    }


}
