package sambucus.eldercraft.initialization;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import sambucus.eldercraft.objects.blocks.Block_BerryBox;
import sambucus.eldercraft.objects.blocks.Block_Brewer;
import sambucus.eldercraft.objects.blocks.Block_Grinder;
import sambucus.eldercraft.objects.blocks.Block_Root;
import sambucus.eldercraft.objects.blocks.Block_SugarCube;


public class BlockInitialization {

		public static final List<Block> BLOCKS = new ArrayList<Block>(); 							
		
		public static final Block BERRYBOX = new Block_BerryBox("block_berrybox", Material.ROCK);		//Initialization of the Berry Box Block
		public static final Block BREWER = new Block_Brewer("block_brewer", Material.ROCK);				//Initialization of the Brewer Block
		public static final Block GRINDER = new Block_Grinder("block_grinder", Material.ROCK);			//Initialization of the Grinder Block
		public static final Block ROOTS = new Block_Root("block_roots", Material.ROCK);					//Initialization of the Root Block
		public static final Block SUGARCUBE = new Block_SugarCube("block_sugarcube",Material.GRASS);	//Initialization of the Sugar Cube Block
				
}
