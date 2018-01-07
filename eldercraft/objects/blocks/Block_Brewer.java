package sambucus.eldercraft.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import sambucus.eldercraft.ElderCraft;
import sambucus.eldercraft.initialization.BlockInitialization;
import sambucus.eldercraft.initialization.ItemInitialization;
import sambucus.eldercraft.utility.IHaveModel;

public class Block_Brewer extends Block implements IHaveModel{
	public Block_Brewer(String name, Material material){
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ElderCraft.eldercrafttab);
		BlockInitialization.BLOCKS.add(this);
		ItemInitialization.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));	
	}
	
	@Override
	public void registerModels(){
		ElderCraft.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}