package cf.nathanpb.Spelling3.entity;

import cf.nathanpb.ProjectMetadata.ProjectMetadata;
import cf.nathanpb.Spelling3.Core;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathanpb on 9/10/17.
 */
public class SpellingPlayer extends SpellingEntity{
    private ProjectMetadata dbProfile = new ProjectMetadata(getPlayer().getUniqueId().toString(), Core.PlayerDatabase);

    public SpellingPlayer(Player p){
        super(p);
    }

    public Player getPlayer(){
        return (Player)getEntity();
    }
    public ProjectMetadata getProfile(){
        return dbProfile;
    }

    public int getMana(){
        if(!getProfile().hasKey("mana")) getProfile().put("mana", 0);
        return getProfile().get("mana", Integer.class);
    }
    public void setMana(int mana){
        getProfile().put("mana", mana);
    }
    public void addMana(int mana){
        setMana(getMana()+mana);
    }

    public int getBurnout(){
        if(!getProfile().hasKey("burnout")) getProfile().put("burnout", 0);
        return getProfile().get("burnout", Integer.class);
    }
    public void setBurnout(int burnout){
        getProfile().put("burnout", burnout);
    }
    public void addBurnout(int burnout){
        setBurnout(getBurnout()-burnout);
    }

    public int getLevel(){
        if(!getProfile().hasKey("level")) getProfile().put("level", 1);
        return getProfile().get("level", Integer.class);
    }
    public void setLevel(int level){
        getProfile().put("level", level);
    }
    public void addLevel(int level){
        setLevel(getLevel()+level);
    }

    public int getExp(){
        if(!getProfile().hasKey("exp")) getProfile().put("exp", 1);
        return getProfile().get("exp", Integer.class);
    }
    public void setExp(int exp){
        getProfile().put("exp", exp);
    }
    public void addExp(int exp){
        setExp(getExp()+exp);
        if(getExp() > getExpToLevel(getLevel())){
            int level = getLevel();
            int next = (level + 1);
            int exp2 = getExp();
            setLevel(next);
            setExp(exp2-getExpToLevel(level));
            ((Player)getEntity()).sendTitle(ChatColor.GREEN+"Level Up!", ChatColor.BLUE+"From "+ChatColor.GOLD+
                    level+ChatColor.BLUE+" to "+ChatColor.GOLD+next);
        }

    }

    public int getMaxBurnout(){
        return getLevel()*25;
    }
    public int getMaxMana(){
        return getMaxBurnout()*2;
    }

    public static int getExpToLevel(int i){
        return (int)Math.round(Math.pow(i, 4));
    }

    public String getInfoBar(){
        String mana = ChatColor.BLUE+"Mana: "+ChatColor.GOLD+getPercent(getMana(), getMaxMana())+"%";
        String burnout = ChatColor.BLUE+"Burnout: "+ChatColor.GOLD+getPercent(getBurnout(), getMaxBurnout())+"%";
        String level = ChatColor.BLUE+"Level: "+ChatColor.GOLD+""+getLevel();
        int atual = getExp();
        int target = getExpToLevel(getLevel()+1);
        int percent = getPercent(atual, target);
        String exp = ChatColor.BLUE+"EXP: "+ChatColor.GOLD+percent+"%";
        String splitter = ChatColor.DARK_PURPLE+" | ";
        String s = mana+splitter+burnout+splitter+level+splitter+exp;
        return s;
    }
    public static int getPercent(int x, int y){
        int i = 0;
        try {
            i = x * (100) / y;
        }catch (ArithmeticException e){
            return 0;
        }
        return i;
    }
    public void sendActionbar(String message) {
        ((Player)getEntity()).spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(message).create());
    }

    public  String getInfo(){
        String mana = ChatColor.BLUE+"Mana: "+ChatColor.GOLD+""+getMana()+""+ChatColor.BLUE+"/"+ChatColor.GOLD+""+getMaxMana()+ChatColor.DARK_PURPLE+" - "+ChatColor.GOLD+getPercent(getMana(), getMaxMana())+"%";
        String burnout = ChatColor.BLUE+"Burnout: "+ChatColor.GOLD+""+getBurnout()+""+ChatColor.BLUE+"/"+ChatColor.GOLD+""+getMaxBurnout()+ChatColor.DARK_PURPLE+" - "+ChatColor.GOLD+getPercent(getBurnout(), getMaxBurnout())+"%";
        String level = ChatColor.BLUE+"Level: "+ChatColor.GOLD+""+getLevel()+"                          ";
        int atual = getExp();
        int target = getExpToLevel(getLevel());
        int percent = getPercent(atual, target);
        String exp = ChatColor.BLUE+"EXP: "+ChatColor.GOLD+""+getExp()+""+ChatColor.BLUE+"/"+ChatColor.GOLD+target+ChatColor.DARK_PURPLE+" - "+ChatColor.GOLD+percent+"%";
        String splitter = "\n";
        String start = ChatColor.DARK_PURPLE+"\n======================\n";
        String s = start+mana+splitter+burnout+splitter+exp+splitter+level+start;
        return s;
    }

    public void forceUse(ItemStack item){
        int slot = getPlayer().getInventory().getHeldItemSlot();
        ItemStack old = getPlayer().getInventory().getItem(slot);
        getPlayer().getInventory().setItem(slot, item);

        ByteBuf buf = Unpooled.buffer(256);
        buf.setByte(0, (byte)0);
        buf.writerIndex(1);

        PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buf));
        ((CraftPlayer)getPlayer()).getHandle().playerConnection.sendPacket(packet);
        getPlayer().getInventory().setItem(slot, old);
    }

    public int getTotalExperience() {
        int experience = 0;
        int level = getPlayer().getLevel();
        if(level >= 0 && level <= 15) {
            experience = (int) Math.ceil(Math.pow(level, 2) + (6 * level));
            int requiredExperience = 2 * level + 7;
            double currentExp = Double.parseDouble(Float.toString(getPlayer().getExp()));
            experience += Math.ceil(currentExp * requiredExperience);
            return experience;
        } else if(level > 15 && level <= 30) {
            experience = (int) Math.ceil((2.5 * Math.pow(level, 2) - (40.5 * level) + 360));
            int requiredExperience = 5 * level - 38;
            double currentExp = Double.parseDouble(Float.toString(getPlayer().getExp()));
            experience += Math.ceil(currentExp * requiredExperience);
            return experience;
        } else {
            experience = (int) Math.ceil(((4.5 * Math.pow(level, 2) - (162.5 * level) + 2220)));
            int requiredExperience = 9 * level - 158;
            double currentExp = Double.parseDouble(Float.toString(getPlayer().getExp()));
            experience += Math.ceil(currentExp * requiredExperience);
            return experience;
        }
    }
    public void setTotalExperience(int xp) {
        //Levels 0 through 15
        if(xp >= 0 && xp < 351) {
            //Calculate Everything
            int a = 1; int b = 6; int c = -xp;
            int level = (int) (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
            int xpForLevel = (int) (Math.pow(level, 2) + (6 * level));
            int remainder = xp - xpForLevel;
            int experienceNeeded = (2 * level) + 7;
            float experience = (float) remainder / (float) experienceNeeded;
            experience = round(experience, 2);;

            //Set Everything
            getPlayer().setLevel(level);
            getPlayer().setExp(experience);
            //Levels 16 through 30
        } else if(xp >= 352 && xp < 1507) {
            //Calculate Everything
            double a = 2.5; double b = -40.5; int c = -xp + 360;
            double dLevel = (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
            int level = (int) Math.floor(dLevel);
            int xpForLevel = (int) (2.5 * Math.pow(level, 2) - (40.5 * level) + 360);
            int remainder = xp - xpForLevel;
            int experienceNeeded = (5 * level) - 38;
            float experience = (float) remainder / (float) experienceNeeded;
            experience = round(experience, 2);

            //Set Everything
            getPlayer().setLevel(level);
            getPlayer().setExp(experience);
            //Level 31 and greater
        } else {
            //Calculate Everything
            double a = 4.5; double b = -162.5; int c = -xp + 2220;
            double dLevel = (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
            int level = (int) Math.floor(dLevel);
            int xpForLevel = (int) (4.5 * Math.pow(level, 2) - (162.5 * level) + 2220);
            int remainder = xp - xpForLevel;
            int experienceNeeded = (9 * level) - 158;
            float experience = (float) remainder / (float) experienceNeeded;
            experience = round(experience, 2);
            System.out.println("xpForLevel: " + xpForLevel);
            System.out.println(experience);

            //Set Everything
            getPlayer().setLevel(level);
            getPlayer().setExp(experience);
        }
    }
    private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_DOWN);
        return bd.floatValue();
    }
}
