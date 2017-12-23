package sambucus.eldercraft.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sambucus.eldercraft.ElderCraft;
import sambucus.eldercraft.initialization.BlockInitialization;
import sambucus.eldercraft.initialization.ItemInitialization;
import sambucus.eldercraft.objects.tiles.TileGrinder;
import sambucus.eldercraft.utility.IHaveModel;

public class BlockGrinder extends Block implements IHaveModel{
	public BlockGrinder(String name, Material material){
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ElderCraft.eldercrafttab);
		BlockInitialization.BLOCKS.add(this);
		ItemInitialization.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));	
	}
	//TODO get the god damn model in so it will look right... the GUI.png also
/*	@Override //here for when we have the actual model that is not a full block nor light blocking
	public boolean isOpaqueCube(IBlockState state){
	    return false;
	}
	@Override
	public boolean isFullCube(IBlockState state){
	    return false;
	}*/
	@Override
	public void registerModels(){
		ElderCraft.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	@Override
	public boolean hasTileEntity(IBlockState state){
	    return true;
	}
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new  TileGrinder();
	}
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileGrinder) { // prevent a crash if not the right type, or is null
			TileGrinder tileEntityData = (TileGrinder)tileentity;
		}
	}
}
