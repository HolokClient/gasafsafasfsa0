/*
 * Decompiled with CFR 0.150.
 */
package baritone.events.events.baritoneOnly;

import digger.cmept.forum.event.events.Event;
import digger.cmept.forum.event.events.callables.EventCancellable;
import net.minecraft.util.math.BlockPos;

public class EventBarBlockBreak
extends EventCancellable
implements Event {
    public BlockPos position;

    public EventBarBlockBreak(BlockPos pos) {
        this.position = pos;
    }
}

