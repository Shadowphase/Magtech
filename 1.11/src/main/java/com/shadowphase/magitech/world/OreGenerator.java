package com.shadowphase.magitech.world;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.google.common.base.Predicate;
import com.google.gson.Gson;
import com.shadowphase.magitech.Main;
import com.shadowphase.magitech.init.ModBlocks;
import com.shadowphase.magitech.lib.Constants;
import com.shadowphase.magitech.lib.OreGen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OreGenerator implements IWorldGenerator {

    private static final Map<OreGen, WorldGenMinable> overworld_genBlocks = new HashMap<OreGen, WorldGenMinable>();
    private static final Map<OreGen, WorldGenMinable> nether_genBlocks = new HashMap<OreGen, WorldGenMinable>();
    private static final Map<OreGen, WorldGenMinable> end_genBlocks = new HashMap<OreGen, WorldGenMinable>();

    public OreGenerator() {
        InputStream readIn = null;
        Reader reader = null;
        try {
            ClassLoader loader = ModBlocks.class.getClassLoader();
            readIn = loader.getResourceAsStream(Constants.ORE_GENERATION);
            reader = new InputStreamReader(readIn, "UTF-8");
            OreGen[][] ores = new Gson().fromJson(reader, OreGen[][].class);
            addOreBlocks(overworld_genBlocks, ores[0], null);
            addOreBlocks(nether_genBlocks, ores[1], new NetherGenPredicate());
            addOreBlocks(end_genBlocks, ores[2], new EndGenPredicate());
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
            IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
        case 0: // Overworld
            runGenerator(overworld_genBlocks, world, random, chunkX, chunkZ);
            break;
        case -1: // Nether
            runGenerator(nether_genBlocks, world, random, chunkX, chunkZ);
            break;
        case 1: // End
            runGenerator(end_genBlocks, world, random, chunkX, chunkZ);
            break;
        case 44: // Shadow Portal
            break;
        }
    }

    private void addOreBlocks(Map<OreGen, WorldGenMinable> map, OreGen[] oreBlocks, Predicate<IBlockState> predicate) {
        for (int i = 0; i < oreBlocks.length; ++i) {
            final OreGen oreGen = oreBlocks[i];
            final String name = oreGen.getBlockName();
            final int oreNumber = oreGen.getOreNumber();

            WorldGenMinable worldGen;
            if (predicate == null) {
                worldGen = new WorldGenMinable(Main.blocks.get(name).getBlock().getDefaultState(), oreNumber);
            } else {
                worldGen = new WorldGenMinable(Main.blocks.get(name).getBlock().getDefaultState(), oreNumber,
                        predicate);
            }
            map.put(oreGen, worldGen);
        }
    }

    private void runGenerator(Map<OreGen, WorldGenMinable> oreMap, World world, Random rand, int chunk_X, int chunk_Z) {
        for (Entry<OreGen, WorldGenMinable> entry : oreMap.entrySet()) {
            OreGen oreGen = entry.getKey();
            int chancesToSpawn = oreGen.getChanceToSpawn();
            int minHeight = oreGen.getMinHeight();
            int maxHeight = oreGen.getMaxHeight();
            if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
                throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

            int heightDiff = maxHeight - minHeight + 1;
            for (int i = 0; i < chancesToSpawn; ++i) {
                int x = chunk_X * 16 + rand.nextInt(16);
                int y = minHeight + rand.nextInt(heightDiff);
                int z = chunk_Z * 16 + rand.nextInt(16);
                entry.getValue().generate(world, rand, new BlockPos(x, y, z));
            }
        }
    }
}
