package sambucus.eldercraft.blocks;

import sambucus.eldercraft.ElderCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SugarCube extends Block {
	public SugarCube() {
		super(Material.ROCK);
		setUnlocalizedName("ec0.sugarcube");
		setRegistryName("sugarcube");
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}
}