package com.github.towardthestars.localspecialties.util;

public class BlockStateFlag
{
    public static final int BUD = 1;
    public static final int SEND_TO_CLIENT = 2;
    public static final int PREVENT_RERENDERING = 4;
    public static final int FORCE_RERENDERING = 8;
    public static final int PREVENT_NEIGHBOR_REACTION = 16;
    public static final int PREVENT_NEIGHBOR_REACTION_SPAWNING_DROPS = 32;
    public static final int MOVING_BLOCK = 64;
}
