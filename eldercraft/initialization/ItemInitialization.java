package sambucus.eldercraft.initialization;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import sambucus.eldercraft.objects.items.BeefPatty;
import sambucus.eldercraft.objects.items.CanOfClearJuice;
import sambucus.eldercraft.objects.items.CanOfDenseJuice;
import sambucus.eldercraft.objects.items.CanOfFizzyJuice;
import sambucus.eldercraft.objects.items.CanOfHotJuice;
import sambucus.eldercraft.objects.items.ChickenPatty;
import sambucus.eldercraft.objects.items.ClusterOfBerries;
import sambucus.eldercraft.objects.items.CookedBeefPatty;
import sambucus.eldercraft.objects.items.CookedChickenPatty;
import sambucus.eldercraft.objects.items.CookedFishPatty;
import sambucus.eldercraft.objects.items.CookedLeanPatty;
import sambucus.eldercraft.objects.items.CookedMuttonPatty;
import sambucus.eldercraft.objects.items.CookedPorkPatty;
import sambucus.eldercraft.objects.items.FishPatty;
import sambucus.eldercraft.objects.items.FishScales;
import sambucus.eldercraft.objects.items.GrowingMash;
import sambucus.eldercraft.objects.items.IronCan;
import sambucus.eldercraft.objects.items.LeanPatty;
import sambucus.eldercraft.objects.items.MincedMeatPatty;
import sambucus.eldercraft.objects.items.MuttonPatty;
import sambucus.eldercraft.objects.items.OilySkin;
import sambucus.eldercraft.objects.items.PorkPatty;
import sambucus.eldercraft.objects.items.PowderyMash;
import sambucus.eldercraft.objects.items.PrickleyMash;
import sambucus.eldercraft.objects.items.SoftMash;
import sambucus.eldercraft.objects.items.ZombieLumps;
import sambucus.eldercraft.objects.items.poisongland;


public class ItemInitialization {

	//public static final Item NAMEOFJAVACLASS = new javaclassname(case sensitive)("itemname");			 TEMPLATE
	
		public static final List<Item> ITEMS = new ArrayList<Item>();					//The array list that is creating our item list. Shouldn't need to to touch this.
		
		public static final Item POISONGLAND = new poisongland("item_poisongland");		//Initialization of the sugarcube block. Refers to sambucus.eldercraft.objects.blocks.sugarcube
		public static final Item BEEFPATTY = new BeefPatty("item_beefpatty");
		public static final Item CANOFCLEARJUICE = new CanOfClearJuice("item_canofclearjuice");
		public static final Item CANOFDENSEJUICE = new CanOfDenseJuice("item_canofdensejuice");
		public static final Item CANOFFIZZYJUICE = new CanOfFizzyJuice("item_canofFizzyjuice");
		public static final Item CANOFHOTJUICE = new CanOfHotJuice("item_canofhotjuice");
		public static final Item CHICKENPATTY = new ChickenPatty("item_chickenpatty");
		public static final Item CLUSTEROFBERRIES = new ClusterOfBerries("item_clusterofberries");
		public static final Item COOKEDBEEFPATTY = new CookedBeefPatty("item_cookedbeefpatty");
		public static final Item COOKEDCHICKENPATTY = new CookedChickenPatty("item_cookedchickenpatty");
		public static final Item COOKEDFISHPATTY = new CookedFishPatty("item_cookedfishpatty");
		public static final Item COOKEDLEANPATTY = new CookedLeanPatty("item_cookedleanpatty");
		public static final Item COOKEDMUTTONPATTY = new CookedMuttonPatty("item_cookedmuttonpatty");
		public static final Item COOKEDPORKPATTY = new CookedPorkPatty("item_cookedprokpatty");
		public static final Item FISHPATTY = new FishPatty("item_fishpatty");
		public static final Item FISHSCALES = new FishScales("item_fishscales");
		public static final Item GROWINGMASH = new GrowingMash("item_growingmash");
		public static final Item IRONCAN = new IronCan("item_ironcan");
		public static final Item LEANPATTY = new LeanPatty("item_leanpatty");
		public static final Item MINCEDMEATPATTY = new MincedMeatPatty("item_mincemeatpatty");
		public static final Item MUTTONPATTY = new MuttonPatty("item_muttonpatty");
		public static final Item OILYSKIN = new OilySkin("item_oilyskin");
		public static final Item PORKPATTY = new PorkPatty("item_porkpatty");
		public static final Item POWDERYMASH = new PowderyMash("item_powdermash");
		public static final Item PRICKLEYMASH = new PrickleyMash("item_prickleymash");
		public static final Item SOFTMASH = new SoftMash("item_softmash");
		public static final Item ZOMBIELUMPS = new ZombieLumps("item_zombielumps");

		
}
