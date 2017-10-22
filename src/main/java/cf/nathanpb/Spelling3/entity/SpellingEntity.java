package cf.nathanpb.Spelling3.entity;

import cf.nathanpb.Spelling3.events.InteractSpellEvent;
import cf.nathanpb.Spelling3.spell.Spell;
import org.bukkit.entity.LivingEntity;

/**
 * Created by nathanpb on 9/10/17.
 */
public class SpellingEntity {
    private LivingEntity entity;
    public SpellingEntity(LivingEntity entity){
        this.entity = entity;
    }
    public LivingEntity getEntity() {
        return entity;
    }

    public void useSpell(Spell s, InteractSpellEvent e){
        s.trigger(e);
    }
}
