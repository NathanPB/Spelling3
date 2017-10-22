package cf.nathanpb.Spelling3.eventHandler;

import cf.nathanpb.Spelling3.structures.SpellTable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by nathanpb on 10/14/17.
 */
public class PlayerInteract implements Listener{
    @EventHandler
    public static void onPIE(PlayerInteractEvent e){
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(SpellTable.check(e.getClickedBlock())) new SpellTable(e.getClickedBlock().getLocation()).getGui().open(e.getPlayer());
        }
    }
}
