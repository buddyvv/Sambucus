package sambucus.eldercraft.initialization;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import sambucus.eldercraft.objects.items.Bread_Potion;
import sambucus.eldercraft.objects.items.Bread_Sliced;
import sambucus.eldercraft.objects.items.Brew_FishScales;
import sambucus.eldercraft.objects.items.Brew_OilySkin;
import sambucus.eldercraft.objects.items.Brew_PoisonGlands;
import sambucus.eldercraft.objects.items.Brew_ZombieLumps;
import sambucus.eldercraft.objects.items.Can_Iron;
import sambucus.eldercraft.objects.items.Can_JuiceBurnt;
import sambucus.eldercraft.objects.items.Can_JuiceClear;
import sambucus.eldercraft.objects.items.Can_JuiceDense;
import sambucus.eldercraft.objects.items.Can_JuiceFizzy;
import sambucus.eldercraft.objects.items.Condiment_Ketchup;
import sambucus.eldercraft.objects.items.Condiment_Mayo;
import sambucus.eldercraft.objects.items.Condiment_Mustard;
import sambucus.eldercraft.objects.items.Mash_Growing;
import sambucus.eldercraft.objects.items.Mash_Powdery;
import sambucus.eldercraft.objects.items.Mash_Prickley;
import sambucus.eldercraft.objects.items.Mash_Soft;
import sambucus.eldercraft.objects.items.Patty_CookedBeef;
import sambucus.eldercraft.objects.items.Patty_CookedChicken;
import sambucus.eldercraft.objects.items.Patty_CookedFish;
import sambucus.eldercraft.objects.items.Patty_CookedLean;
import sambucus.eldercraft.objects.items.Patty_CookedMutton;
import sambucus.eldercraft.objects.items.Patty_CookedPork;
import sambucus.eldercraft.objects.items.Patty_RawBeef;
import sambucus.eldercraft.objects.items.Patty_RawChicken;
import sambucus.eldercraft.objects.items.Patty_RawFish;
import sambucus.eldercraft.objects.items.Patty_RawLean;
import sambucus.eldercraft.objects.items.Patty_RawMutton;
import sambucus.eldercraft.objects.items.Patty_RawPork;
import sambucus.eldercraft.objects.items.Patty_ZMeat;
import sambucus.eldercraft.objects.items.TempBerries;
import sambucus.eldercraft.objects.items.Treat_DogHeal;

public class ItemInitialization {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Initialization of Brewing Items
	public static final Item FISHSCALES 		= new Brew_FishScales("item_fishscales");
	public static final Item OILYSKIN 			= new Brew_OilySkin("item_oilyskin");
	public static final Item POISONGLAND 		= new Brew_PoisonGlands("item_poisongland");
	public static final Item ZOMBIELUMPS		= new Brew_ZombieLumps("item_zombielumps");
	
	//brew results
	public static final Item BREADPOTION 		= new Bread_Potion("item_breadpotion", 4, false);
	
	//Initialization of Items using cans
	public static final Item IRONCAN 			= new Can_Iron("item_ironcan");
	public static final Item JUICECLEARCAN 		= new Can_JuiceClear("item_canofclearjuice");
	public static final Item JUICEDENSECAN 		= new Can_JuiceDense("item_canofdensejuice");
	public static final Item JUICEFIZZYCAN 		= new Can_JuiceFizzy("item_canoffizzyjuice");
	public static final Item JUICEBURNTCAN 		= new Can_JuiceBurnt("item_canofburntjuice");
	
	//Initialization of Mashes
	public static final Item MASHGROWING 		= new Mash_Growing("item_growingmash");
	public static final Item MASHPOWDERY 		= new Mash_Powdery("item_powderymash");
	public static final Item MASHPRICKLEY		= new Mash_Prickley("item_prickleymash");
	public static final Item MASHSOFT	 		= new Mash_Soft("item_softmash");
	
	//Initialization of Miscellaneous Foods
	public static final Item BREADSLICED	 	= new Bread_Sliced("item_breadsliced", 4, false);
	public static final Item KETCHUP			= new Condiment_Ketchup("item_ketchup",0 ,false);//red
	public static final Item MUSTARD			= new Condiment_Mustard("item_mustard",0 ,false);//yellow
	public static final Item MAYO				= new Condiment_Mayo("item_mayo",0,false);//white
	
	//Initialization of Raw Patties
	public static final Item PATTYRAWBEEF 		= new Patty_RawBeef("item_rawbeefpatty", 2, false);	
	public static final Item PATTYRAWCHICKEN 	= new Patty_RawChicken("item_rawchickenpatty", 2, false);	
	public static final Item PATTYRAWFISH 		= new Patty_RawFish("item_rawfishpatty", 2, false);	
	public static final Item PATTYRAWLEAN 		= new Patty_RawLean("item_rawleanpatty", 2, false);
	public static final Item PATTYRAWMUTTON		= new Patty_RawMutton("item_rawmuttonpatty", 2, false);	
	public static final Item PATTYRAWPORK 		= new Patty_RawPork("item_rawporkpatty",	2, false);
	
	//Initialization of Cooked Patties
	public static final Item PATTYCOOKEDBEEF 	= new Patty_CookedBeef("item_cookedbeefpatty", 4, false);
	public static final Item PATTYCOOKEDCHICKEN = new Patty_CookedChicken("item_cookedchickenpatty", 4, false);
	public static final Item PATTYCOOKEDFISH 	= new Patty_CookedFish("item_cookedfishpatty", 4, false);
	public static final Item PATTYCOOKEDLEAN 	= new Patty_CookedLean("item_cookedleanpatty", 4, false);
	public static final Item PATTYCOOKEDMUTTON 	= new Patty_CookedMutton("item_cookedmuttonpatty", 4, false);
	public static final Item PATTYCOOKEDPORK 	= new Patty_CookedPork("item_cookedporkpatty", 4, false);
	public static final Item PATTYZMEAT 		= new Patty_ZMeat("item_zmeatpatty", 0, false, new PotionEffect(Potion.getPotionById(17), 200, 100));
	
	//Initialization of magic foods 
	public static final Item TREATDOGHEALTH		= new Treat_DogHeal("item_healingdogtreat",0, true);
	//Initialization of temporary items
	public static final Item CLUSTEROFBERRIES 	= new TempBerries("item_clusterofberries");
}