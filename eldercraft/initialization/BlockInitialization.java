package sambucus.eldercraft.initialization;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import sambucus.eldercraft.objects.blocks.BlockGrinder;
import sambucus.eldercraft.objects.blocks.sugarcube;


public class BlockInitialization {

		public static final List<Block> BLOCKS = new ArrayList<Block>(); 							//The array list that is creating our item list. Shouldn't need to to touch this.
		
		public static final Block SUGARCUBE 	= new sugarcube("block_sugarcube",Material.GRASS);		//Initialization of the sugarcube block. Refers to sambucus.eldercraft.objects.blocks.sugarcube
		public static final Block GRINDER_BLOCK = new BlockGrinder("block_grinder", Material.ROCK);
		
}
