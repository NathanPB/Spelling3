package cf.nathanpb.Spelling3.events;

import cf.nathanpb.Spelling3.ActionType;
import cf.nathanpb.Spelling3.entity.SpellingEntity;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

/**
 * Created by nathanpb on 10/8/17.
 */
public abstract class InteractSpellEvent {
    public SpellingEntity getTrigger(){
        return null;
    }
    public LivingEntity getClickedEntity(){
        return null;
    }
    public Block getClickedBlock(){
        return null;
    }
    public ActionType getAction(){
        return null;
    }

}
