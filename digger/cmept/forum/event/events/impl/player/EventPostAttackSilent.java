package digger.cmept.forum.event.events.impl.player;

import digger.cmept.forum.event.events.callables.EventCancellable;
import net.minecraft.entity.Entity;

public class EventPostAttackSilent
        extends EventCancellable {
    private final Entity targetEntity;

    public EventPostAttackSilent(Entity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public Entity getTargetEntity() {
        return this.targetEntity;
    }
}
