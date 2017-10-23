package cf.nathanpb.Spelling3.spell;

import cf.nathanpb.Spelling3.interfaces.TriggerAction;
import org.bukkit.event.Event;

/**
 * Created by nathanpb on 10/21/17.
 */
public interface SelfSpell {
    void execute(Event e);
    boolean shouldExecute(TriggerAction action);
    default void setTriggerAction(TriggerAction action){
        SelfManager.SelfMap.put(this, action);
    }
}
