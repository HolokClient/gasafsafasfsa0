/*
 * Decompiled with CFR 0.150.
 */
package baritone.events.events.baritoneOnly;

import digger.cmept.forum.event.events.Event;
import digger.cmept.forum.event.events.callables.EventCancellable;
public class EventBarSprintState
extends EventCancellable
implements Event {
    public boolean state;

    public EventBarSprintState(boolean stateIn) {
        this.state = stateIn;
    }
}

