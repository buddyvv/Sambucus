package sambucus.eldercraft.initialization;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import sambucus.eldercraft.objects.blocks.sugarcube;


public class BlockInitialization {

		public static final List<Block> BLOCKS = new ArrayList<Block>();
		
		public static final Block SUGARCUBE = new sugarcube("block_sugarcube",Material.GRASS);

		
}
