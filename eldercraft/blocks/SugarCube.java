package sambucus.eldercraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SugarCube extends Block {
	public SugarCube() {
		super(Material.ROCK);
		setUnlocalizedName("ec0.sugarcube");
		setRegistryName("sugarcube");
		//setCreativeTab(CreativeTabs.BUILDING_BLOCKS);	
		//setSoundType();
		
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}