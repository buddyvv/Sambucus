package sambucus.eldercraft.objects.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sambucus.eldercraft.ElderCraft;
import sambucus.eldercraft.initialization.BlockInitialization;
import sambucus.eldercraft.initialization.ItemInitialization;
import sambucus.eldercraft.objects.tiles.TileGrinder;
import sambucus.eldercraft.utility.IHaveModel;
import sambucus.eldercraft.utility.handlers.GUIHandler;

public class BlockGrinder extends BlockContainer implements IHaveModel{
	public BlockGrinder(String name, Material material){
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ElderCraft.eldercrafttab);
		BlockInitialization.BLOCKS.add(this);
		ItemInitialization.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));	
	}
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }
	//TODO get the god damn model in so it will look right
	@Override //here for when we have the actual model that is not a full block nor light blocking
	public boolean isOpaqueCube(IBlockState state){
	    return false;
	}
	@Override
	public boolean isFullCube(IBlockState state){
	    return false;
	}
	@Override
	public void registerModels(){
		ElderCraft.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	@Override
	public boolean hasTileEntity(IBlockState state){
	    return true;
	}
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileGrinder) { // prevent a crash if not the right type, or is null
			TileGrinder tileEntityData = (TileGrinder)tileentity;
		}
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new  TileGrinder();
	}
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
			EntityPlayer playerIn, EnumHand hand,EnumFacing side,
			float hitX, float hitY, float hitZ) {
		
		// Uses the gui handler registered to your mod to open the gui for the given gui id
		// open on the server side only  (not sure why you shouldn't open client side too... vanilla doesn't, so we better not either)
		if (worldIn.isRemote) {
			return true;
		}
		//TODO make this work
		playerIn.openGui(ElderCraft.instance, GUIHandler.getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof IInventory) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileEntity);
		}
		super.breakBlock(worldIn, pos, state);
	}
}