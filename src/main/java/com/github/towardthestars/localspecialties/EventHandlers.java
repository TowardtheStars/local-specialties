package com.github.towardthestars.localspecialties;



class EventHandlers
{
//    static void registerAll(){
//        UseBlockCallback.EVENT.register(EventHandlers::useHoe);
//        UseBlockCallback.EVENT.register(EventHandlers::useSeed);
//        ServerStartCallback.EVENT.register(EventHandlers::loadAffinityConfig);
//    }
//
//    private static void loadAffinityConfig(MinecraftServer server)
//    {
//        Configs.PLANT_AFFINITY.save();
//    }
//
//    private static ActionResult useHoe(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult)
//    {
//        if (playerEntity != null && playerEntity.getStackInHand(hand).getItem() instanceof HoeItem)
//        {
//            BlockPos blockPos = blockHitResult.getBlockPos();
//            Direction direction = blockHitResult.getSide();
//            if (direction != Direction.DOWN && world.getBlockState(blockPos.up()).isAir())
//            {
//                BlockState blockState = FarmLandHelper.getFarmlandForDirt(world.getBlockState(blockPos));
//                if (blockState != null)
//                {
//                    world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
//                    if (!world.isClient)
//                    {
//                        world.setBlockState(blockPos, blockState, 11);
//                        playerEntity.getStackInHand(hand).damage(1, playerEntity, (p) ->
//                                p.sendToolBreakStatus(hand)
//                        );
//                    }
//
//                    return ActionResult.SUCCESS;
//                }
//            }
//        }
//        return ActionResult.PASS;
//    }
//
//    private static ActionResult useSeed(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult)
//    {
//        ItemStack stack = playerEntity.getStackInHand(hand);
//        if (Plants.PLANTING_MAP.containsKey(stack.getItem()))
//        {
//            BlockPos pos = blockHitResult.getBlockPos();
//            BlockState state = world.getBlockState(pos);
//            // 将原版的耕地变为 mod 里的耕地
//            if (state.getBlock() == Blocks.FARMLAND)
//            {
//                world.setBlockState(
//                        pos,
//                        BlockLoader.FARMLAND.getDefaultState()
//                                .with(BlockFarmland.FERTILITY, 4)
//                                .with(BlockFarmland.MOISTURE, state.get(FarmlandBlock.MOISTURE))
//                );
//                state = world.getBlockState(pos);
//            }
//            if (state.getBlock() == BlockLoader.FARMLAND && blockHitResult.getSide() == Direction.UP)
//            {
//                world.setBlockState(pos.up(), Plants.PLANTING_MAP.get(stack.getItem()).getDefaultState(), 11);
//                stack.decrement(1);
//                return ActionResult.SUCCESS;
//            }
//        }
//        return ActionResult.PASS;
//    }


}
