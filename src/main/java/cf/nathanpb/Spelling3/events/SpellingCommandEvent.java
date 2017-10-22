package cf.nathanpb.Spelling3.events;

import cf.nathanpb.Spelling3.entity.SpellingPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by nathanpb on 10/10/17.
 */
public class SpellingCommandEvent {
    private ArrayList<String> args;
    private CommandSender sender;
    public SpellingCommandEvent(CommandSender sender, ArrayList<String> args){
        this.args = args;
        this.sender = sender;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public Player getPlayer() {
        return Bukkit.getPlayerExact(sender.getName());
    }

    public SpellingPlayer getSpellingSender(){
        return new SpellingPlayer(getPlayer());
    }

    public CommandSender getSender() {
        return sender;
    }
}
