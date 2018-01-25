package sambucus.eldercraft.initialization;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import sambucus.eldercraft.objects.blocks.Block_BerryBox;
import sambucus.eldercraft.objects.blocks.Block_Brewer;
import sambucus.eldercraft.objects.blocks.Block_CrystalBall;
import sambucus.eldercraft.objects.blocks.Block_Grinder;
import sambucus.eldercraft.objects.blocks.Block_Lathe;
import sambucus.eldercraft.objects.blocks.Block_Root;
import sambucus.eldercraft.objects.blocks.Block_SugarCube;

public class BlockInitialization {
		public static final List<Block> BLOCKS 	= new ArrayList<Block>(); 							
		//Initialization of the Blocks
		public static final Block SUGARCUBE 	= new Block_SugarCube("block_sugarcube",Material.ROCK);//normal for now
		//tile blocks
		public static final Block BERRYBOX 		= new Block_BerryBox("block_berrybox", Material.WOOD);//like a chest
		public static final Block BREWER 		= new Block_Brewer("block_brewer", Material.ROCK);
		public static final Block GRINDER 		= new Block_Grinder("block_grinder", Material.ROCK);
		public static final Block LATHE			= new Block_Lathe("block_lathe", Material.ROCK);
		public static final Block CRYASTALBALL	= new Block_CrystalBall("block_cryastalball", Material.ROCK);
		//plant blocks
		public static final Block ROOTS 		= new Block_Root("block_roots", Material.GRASS);//the root block for all the plants
}