/*
 * Decompiled with CFR 0.150.
 */
package baritone.events.events.baritoneOnly;

import digger.cmept.forum.event.events.Event;
import digger.cmept.forum.event.events.callables.EventCancellable;
public class EventBarAChunkPre
extends EventCancellable
implements Event {
    public int chunkX;
    public int chunkZ;
    public boolean loadChunk;

    public EventBarAChunkPre(int chunkX, int chunkZ, boolean load) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.loadChunk = load;
    }
}

