package cf.nathanpb.Spelling3.item;

import cf.nathanpb.Spelling3.ActionType;
import cf.nathanpb.Spelling3.Core;
import cf.nathanpb.Spelling3.entity.SpellingEntity;
import cf.nathanpb.Spelling3.entity.SpellingPlayer;
import cf.nathanpb.Spelling3.events.InteractSpellEvent;
import cf.nathanpb.Spelling3.recipes.SpellingRecipe;
import cf.nathanpb.Spelling3.spell.Spell;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nathanpb on 9/10/17.
 */
public class SpellingItem implements Listener{
    /*
    ITEM DATA
     */
    public ItemStack itemStack;
    private HashMap<Event, Runnable> actions = new HashMap<>();
    private void callExecutor(){
        String mname = Thread.currentThread().getStackTrace()[2].getMethodName();
        Event e = null;
        if(mname.equals("onRightClick")) e = Event.RIGHT_CLICK;
        if(mname.equals("onLeftClick")) e = Event.LEFT_CLICK;
        if(mname.equals("onInventoryClick")) e = Event.INVENTORY_CLICK;
        if(mname.equals("onDropped")) e = Event.DROPPED;
        if(mname.equals("onBroke")) e = Event.BROKE;
        if(mname.equals("onMine")) e = Event.MINE;
        if(mname.equals("onEnchanted")) e = Event.ENCHANTED;
        if(mname.equals("onInteractEntity")) e = Event.ENTITY_CLICK;
        if(mname.equals("onEaten")) e = Event.EATEN;
        if(mname.equals("onSpellEvent")) e = Event.SPELL;
        if(actions.containsKey(e)) actions.get(e).run();
    }
    private SpellingRecipe spellingRecipe;
    private String description = "Where is my description? Please tell my owner =) NathanPB#1957";

    public SpellingItem setItemStack(ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        if(stack.getTag() == null) stack.setTag(new NBTTagCompound());
        stack.getTag().setString("SpellingItem", this.getClass().getName());
        this.itemStack = CraftItemStack.asBukkitCopy(stack);
        return this;
    }
    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    /*
    UTILS
     */
    public SpellingItem setDisplayName(String name){
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return this;
    }
    public void setExecutor(Event e, Runnable r){
        actions.put(e, r);
    }
    public SpellingItem addLore(String lore){
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore2 = meta.getLore();
        if(lore2 == null) lore2 = new ArrayList<>();
        lore2.add(lore);
        meta.setLore(lore2);
        itemStack.setItemMeta(meta);
        return this;
    }

    public SpellingRecipe getSpellingRecipe(){
        return this.spellingRecipe;
    }
    public void setRecipe(SpellingRecipe spellingRecipe) {
        this.spellingRecipe = spellingRecipe;
        spellingRecipe.register();
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public String getRegistry(){
        return this.getClass().getName().replace(Core.class.getPackage().getName()+".", "");
    }


    /*
                RAW BUKKIT EVENTS
                 */
    @EventHandler
    public void onPIE(PlayerInteractEvent e) {
        if (!instanceOf(e.getItem())) return;
        if(this instanceof Spell){
            onSpellEvent(new InteractSpellEvent() {
                    @Override
                    public SpellingEntity getTrigger() {
                        return new SpellingPlayer(e.getPlayer());
                    }

                    @Override
                    public Block getClickedBlock() {
                        if(e.getAction().name().contains("BLOCK")) return e.getClickedBlock();
                        return super.getClickedBlock();
                    }

                    @Override
                    public ActionType getAction() {
                        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) return  cf.nathanpb.Spelling3.ActionType.CLICK_BLOCK;
                        if(e.getAction() == Action.RIGHT_CLICK_AIR) return  cf.nathanpb.Spelling3.ActionType.CLICK_AIR;
                        if(e.getAction() == Action.LEFT_CLICK_AIR) return cf.nathanpb.Spelling3.ActionType.HIT_AIR;
                        if(e.getAction() == Action.LEFT_CLICK_BLOCK) return cf.nathanpb.Spelling3.ActionType.HIT_BLOCK;
                        return null;
                    }
                });
        }
        if (e.getAction().name().contains("RIGHT")) onRightClick(e);
        if (e.getAction().name().contains("LEFT")) onLeftClick(e);
    }
    @EventHandler
    public void onICE(InventoryClickEvent e) {
        if (instanceOf(e.getCurrentItem())) onInventoryClick(e);
    }
    @EventHandler
    public void onPDIE(PlayerDropItemEvent e) {
        if (instanceOf(e.getItemDrop().getItemStack())) onDropped(e);
    }
    @EventHandler
    public void onPIBE(PlayerItemBreakEvent e) {
        if (instanceOf(e.getBrokenItem())) onBroke(e);
    }
    @EventHandler
    public void onBBE(BlockBreakEvent e){
        if (e.getPlayer() != null && e.getPlayer().getItemInHand() != null && instanceOf(e.getPlayer().getItemInHand())) onMine(e);
    }
    @EventHandler
    public void onEIE(EnchantItemEvent e) {
        if (instanceOf(e.getItem())) onEnchanted(e);
    }
    @EventHandler
    public void onPIEE(PlayerInteractEntityEvent e){
        if(!instanceOf(e.getPlayer().getItemInHand())) return;
        if(e.getRightClicked() instanceof LivingEntity){
            if (this instanceof Spell) {
                onSpellEvent(new InteractSpellEvent() {
                    @Override
                    public SpellingEntity getTrigger() {
                        return new SpellingPlayer(e.getPlayer());
                    }

                    @Override
                    public LivingEntity getClickedEntity() {
                        return (LivingEntity) e.getRightClicked();
                    }

                    @Override
                    public ActionType getAction() {
                        return cf.nathanpb.Spelling3.ActionType.CLICK_ENTITY;
                    }
                });
            }
            onInteractEntity(e);
        }
    }
    @EventHandler
    public void onEATER(PlayerItemConsumeEvent e){
        if(instanceOf(e.getItem())) onEaten(e);
    }
    @EventHandler
    public void onPHEE(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof LivingEntity && e.getEntity() instanceof LivingEntity){
            if(!instanceOf(((LivingEntity)e.getDamager()).getEquipment().getItemInMainHand())) return;
            onHurtEntity(e);
            if (this instanceof Spell) {
                onSpellEvent(new InteractSpellEvent() {
                    @Override
                    public SpellingEntity getTrigger() {
                        if(e.getDamager() instanceof Player) return new SpellingPlayer((Player) e.getDamager());
                        return new SpellingEntity((LivingEntity)e.getDamager());
                    }

                    @Override
                    public LivingEntity getClickedEntity() {
                        return (LivingEntity) e.getEntity();
                    }

                    @Override
                    public ActionType getAction() {
                        return cf.nathanpb.Spelling3.ActionType.HIT_ENTITY;
                    }
                });
            }
        }
    }

    /*
    ITEM CUSTOM EVENTS
     */
    protected void onRightClick(PlayerInteractEvent e){
        callExecutor();
    }
    protected void onLeftClick(PlayerInteractEvent e){ callExecutor();}
    protected void onInventoryClick(InventoryClickEvent e){callExecutor();}
    protected void onDropped(PlayerDropItemEvent e){ callExecutor();}
    protected void onBroke(PlayerItemBreakEvent e){ callExecutor();}
    protected void onMine(BlockBreakEvent e){callExecutor();}
    protected void onEnchanted(EnchantItemEvent e){callExecutor();}
    protected void onInteractEntity(PlayerInteractEntityEvent e){callExecutor();}
    protected void onEaten(PlayerItemConsumeEvent e){callExecutor();}
    protected void onSpellEvent(InteractSpellEvent e){
        callExecutor();
    }
    protected void onHurtEntity(EntityDamageByEntityEvent e){callExecutor();}

    /*
    INSTANCE MANAGERS
     */
    private static HashMap<Class, SpellingItem> Items = new HashMap<>();
    public static void register(Class<? extends SpellingItem> clz){
        try {
            //Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SPELLING] Registered Item "+clz.getName().replace(Core.class.getPackage().getName()+".", ""));
            SpellingItem instance = clz.newInstance();
            Items.put(clz, instance);
            Bukkit.getPluginManager().registerEvents(instance, Core.getInstance());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static <T>T getInstance(Class<T> clz){
        return (T) Items.get(clz);
    }
    protected boolean instanceOf(ItemStack stack){
        if(stack == null) return false;
        if(CraftItemStack.asNMSCopy(stack).getTag() == null) return false;
        if(!CraftItemStack.asNMSCopy(stack).getTag().hasKey("SpellingItem")) return false;
        return CraftItemStack.asNMSCopy(stack).getTag().getString("SpellingItem").equals(this.getClass().getName());
    }
    public static ArrayList<SpellingItem> getItems() {
        ArrayList<SpellingItem> items = new ArrayList<>();
        Items.entrySet().stream().forEach(e -> items.add(e.getValue()));
        return items;
    }
    public static SpellingItem getInstance(ItemStack item){
        net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(item);
        if(stack.getTag() == null) return null;
        if(!stack.getTag().hasKey("SpellingItem")) return null;
        try{
            return Items.get(Class.forName(stack.getTag().getString("SpellingItem")));
        }catch (Exception e){
            return null;
        }
    }

    public enum Event{
        RIGHT_CLICK,
        LEFT_CLICK,
        INVENTORY_CLICK,
        DROPPED,
        BROKE,
        MINE,
        ENCHANTED,
        ENTITY_CLICK,
        EATEN,
        SPELL;
    }
}
