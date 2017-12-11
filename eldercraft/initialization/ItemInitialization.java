package sambucus.eldercraft.initialization;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;
import sambucus.eldercraft.objects.items.PattyBeefRaw;
import sambucus.eldercraft.objects.items.JuiceClearCan;
import sambucus.eldercraft.objects.items.JuiceDenseCan;
import sambucus.eldercraft.objects.items.JuiceFizzyCan;
import sambucus.eldercraft.objects.items.JuiceBurntCan;
import sambucus.eldercraft.objects.items.PattyChickenRaw;
import sambucus.eldercraft.objects.items.TempBerries;
import sambucus.eldercraft.objects.items.PattyBeefCooked;
import sambucus.eldercraft.objects.items.PattyChickenCooked;
import sambucus.eldercraft.objects.items.PattyFishCooked;
import sambucus.eldercraft.objects.items.PattyLeanCooked;
import sambucus.eldercraft.objects.items.PattyMuttonCooked;
import sambucus.eldercraft.objects.items.PattyPorkCooked;
import sambucus.eldercraft.objects.items.PattyFishRaw;
import sambucus.eldercraft.objects.items.BrewFishScales;
import sambucus.eldercraft.objects.items.MashGrowing;
import sambucus.eldercraft.objects.items.IronCan;
import sambucus.eldercraft.objects.items.PattyLeanRaw;
import sambucus.eldercraft.objects.items.PattyZMeat;
import sambucus.eldercraft.objects.items.PattyMuttonRaw;
import sambucus.eldercraft.objects.items.BrewOilySkin;
import sambucus.eldercraft.objects.items.PattyPorkRaw;
import sambucus.eldercraft.objects.items.MashPowdery;
import sambucus.eldercraft.objects.items.MashPrickley;
import sambucus.eldercraft.objects.items.MashSoft;
import sambucus.eldercraft.objects.items.BrewZombieLumps;
import sambucus.eldercraft.objects.items.BrewPoisonGlands;

public class ItemInitialization {
	//public static final Item NAMEOFJAVACLASS = new javaclassname(case sensitive)("itemname");
	//The array list that is creating our item list. Shouldn't need to to touch this. unless it is completely fucked up again
	public static final List<Item> ITEMS = new ArrayList<Item>();
	//extra brewing items
	public static final Item POISONGLAND 		= new BrewPoisonGlands("item_poisongland");
	public static final Item FISHSCALES 		= new BrewFishScales("item_fishscales");
	public static final Item OILYSKIN 			= new BrewOilySkin("item_oilyskin");
	public static final Item ZOMBIELUMPS		= new BrewZombieLumps("item_zombielumps");
	//"food" items
	public static final Item BEEFPATTY 			= new PattyBeefRaw("item_rawbeefpatty");
	public static final Item CHICKENPATTY 		= new PattyChickenRaw("item_rawchickenpatty");
	public static final Item MUTTONPATTY 		= new PattyMuttonRaw("item_rawmuttonpatty");
	public static final Item LEANPATTY 			= new PattyLeanRaw("item_rawleanpatty");
	public static final Item FISHPATTY 			= new PattyFishRaw("item_rawfishpatty");
	public static final Item PORKPATTY 			= new PattyPorkRaw("item_rawporkpatty");
	public static final Item COOKEDBEEFPATTY 	= new PattyBeefCooked("item_cookedbeefpatty");
	public static final Item COOKEDCHICKENPATTY = new PattyChickenCooked("item_cookedchickenpatty");
	public static final Item COOKEDFISHPATTY 	= new PattyFishCooked("item_cookedfishpatty");
	public static final Item COOKEDLEANPATTY 	= new PattyLeanCooked("item_cookedleanpatty");
	public static final Item COOKEDMUTTONPATTY 	= new PattyMuttonCooked("item_cookedmuttonpatty");
	public static final Item COOKEDPORKPATTY 	= new PattyPorkCooked("item_cookedporkpatty");
	public static final Item ZMEATPATTY 		= new PattyZMeat("item_zmeatpatty");
	//cans of juice
	public static final Item IRONCAN 			= new IronCan("item_ironcan");
	public static final Item CLEARJUICECAN 		= new JuiceClearCan("item_canofclearjuice");
	public static final Item DENSEJUICECAN 		= new JuiceDenseCan("item_canofdensejuice");
	public static final Item FIZZYJUICECAN 		= new JuiceFizzyCan("item_canoffizzyjuice");
	public static final Item BURNTJUICECAN 		= new JuiceBurntCan("item_canofburntjuice");
	//mash
	public static final Item GROWINGMASH 		= new MashGrowing("item_growingmash");
	public static final Item POWDERYMASH 		= new MashPowdery("item_powderymash");
	public static final Item PRICKLEYMASH 		= new MashPrickley("item_prickleymash");
	public static final Item SOFTMASH	 		= new MashSoft("item_softmash");
	//temporary items
	public static final Item CLUSTEROFBERRIES 	= new TempBerries("item_clusterofberries");
}