package cf.nathanpb.Spelling3.commands;

import cf.nathanpb.Spelling3.entity.SpellingPlayer;
import cf.nathanpb.Spelling3.events.SpellingCommandEvent;
import cf.nathanpb.Spelling3.item.SpellingItem;
import cf.nathanpb.Spelling3.utils.InventoryUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathanpb on 9/10/17.
 */
public class Spelling implements CommandExecutor{
    private static String prefix = ChatColor.DARK_PURPLE+"[SPELLING] >>> "+ChatColor.DARK_RED;

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] strs) {
        boolean found = false;
        ArrayList<String> args = new ArrayList<>();
        if(strs.length == 0){
            sender.sendMessage(prefix+"Check help with command /Spelling plzSendHelp");
            return true;
        }
        String cmdName = strs[0];
        for(String s : strs){
            if(s.equals(cmdName)) continue;
            args.add(s);
        }


        for(Method m : Spelling.class.getDeclaredMethods()){
            if(!m.isAccessible()) m.setAccessible(true);
            Command an = m.getAnnotation(Command.class);
            if(an == null) continue;
            if(!an.Trigger().equalsIgnoreCase(cmdName)) continue;

            if(an.bePlayer() && !(sender instanceof Player)){
                sender.sendMessage(prefix+"This command can be executed only by players!");
                return true;
            }
            if(an.op() && !sender.isOp()){
                sender.sendMessage(prefix+"You don't have permissions to execute this command!");
                return true;
            }
            if(args.size() < an.ArgsSize()){
                sender.sendMessage(prefix+"This command needs at least "+an.ArgsSize()+" arguments to work! Check the help page on /Spelling plzSendHelp");
                return true;
            }
            try {
                found = true;
                m.invoke(null, (Object) new SpellingCommandEvent(sender, args));
            }catch (Exception e){
                sender.sendMessage(prefix+"Oops, my owner is dumb! This shit isn't working, plz tell NathanPB#1957");
            }

        }

        if(!found) plzSendHelp(sender);
        return true;
    }

    @Command(
            Trigger = "get",
            Example = "spell.AuraShockwave",
            op = true,
            Description = "Gives you a Spelling Item",
            ArgsSize = 1,
            bePlayer = true
    )
    private static void get(SpellingCommandEvent e){
        try{
            System.out.println(e.getArgs().get(0));
            Class c = Class.forName("cf.nathanpb.Spelling3.item."+e.getArgs().get(0));
            e.getPlayer().getInventory().addItem(((SpellingItem) SpellingItem.getInstance(c)).getItemStack());
        }catch (Exception ex){
            e.getSender().sendMessage(ChatColor.RED+"Opps! This item doesn't exists!");
        }
    }

    @Command(
            Trigger = "plzSendHelp",
            Example = "",
            Description = "Sends help",
            bePlayer = false
    )
    private static void help(SpellingCommandEvent e){
        if(e.getArgs().size() > 0){
            for(Method m : Spelling.class.getDeclaredMethods()){
                if(!m.isAccessible()) m.setAccessible(true);
                Command an = m.getAnnotation(Command.class);
                if(an == null) continue;
                if(an.Trigger().equalsIgnoreCase(e.getArgs().get(0))){
                    plzSendHelp(e.getSender(), an);
                    return;
                }
            }
        }
        plzSendHelp(e.getSender());
    }





    private static void plzSendHelp(CommandSender sender){
        if(sender instanceof Player){
            String cmds = ChatColor.BLUE+"";
            for(Command cmd : getAllCommands()){
                cmds+=cmd.Trigger()+"\n";
            }

            SpellingPlayer p = new SpellingPlayer((Player) sender);
            p.forceUse(InventoryUtils.makeBook("Spelling Guide", "NathanPB",
                    ChatColor.GOLD+" Welcome to "+ChatColor.DARK_PURPLE+"SPELLING\n"+
                            ChatColor.RESET+"Spelling is a Spigot plugin developed by NathanPB#1957 with emphasis on " +
                            ChatColor.DARK_PURPLE+"MAGIC!"+ChatColor.RESET+
                            "\n\n"+ChatColor.GOLD+"     Getting Start\n"+ChatColor.RESET+
                            "To start with Spelling, you need to make a "+ChatColor.GOLD+"Mage's Guide"+ChatColor.RESET+" (a simple book on recipes grid), this is a book that would explain you" +
                            "all the features about Spelling Plugin."

                    ));
            return;
        }
        sender.sendMessage(prefix+"This fucking help message isn't done yet");
    }

    public static void plzSendHelp(CommandSender sender, Command an){
        Player p = ((Player) sender);
        p.sendMessage(ChatColor.DARK_PURPLE+"=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        p.sendMessage(ChatColor.BLUE+"Help to "+ChatColor.GOLD+an.Trigger()+ChatColor.BLUE+" command");
        p.sendMessage(ChatColor.BLUE+"Description: "+ChatColor.GOLD+an.Description());
        p.sendMessage(ChatColor.BLUE+"Example: "+ChatColor.GOLD+"/Spelling "+an.Trigger()+" "+an.Example());
        p.sendMessage(ChatColor.BLUE+"Player Only: "+ChatColor.GOLD+an.bePlayer());
        p.sendMessage(ChatColor.BLUE+"OP Only: "+ChatColor.GOLD+an.op());
        p.sendMessage(ChatColor.BLUE+"Args size: "+ChatColor.GOLD+an.ArgsSize());
        p.sendMessage(ChatColor.DARK_PURPLE+"=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Command{
            String Description();
            String Example();
            String Trigger();
            boolean op() default false;
            boolean bePlayer();
            int ArgsSize() default 0;
        }


    public static List<Command> getAllCommands(){
        List<Command> list = new ArrayList<>();
        for(Method m : Spelling.class.getDeclaredMethods()){
            if(!m.isAccessible()) m.setAccessible(true);
            Command cmd = m.getAnnotation(Command.class);
            if(cmd != null) list.add(cmd);
        }
        return list;
    }
}
