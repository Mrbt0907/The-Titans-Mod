package net.minecraft.theTitans.events;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
public class TitanSavedData extends WorldSavedData
{
	private NBTTagCompound data = new NBTTagCompound();
	public static final String DATA_NAME = TheTitans.MODID + "_TitanData";
	//Stages
	private boolean preTitanComplete;
	private boolean titanComplete;
	private boolean postTitanComplete;
	//Events
	private boolean postEvent[] = new boolean[10];
	private boolean postEnderDragon;
	private boolean postWither;
	private boolean postTwilight;
	private boolean postEmperorSkulling;
	private boolean postFirstTitan;
	private boolean postWitherzilla;
	private boolean postWitherzillaOmega;
	private boolean postExecutorDragon;
	private boolean postExecutorDragonOmega;
	public TitanSavedData()
	{
		super(DATA_NAME);
		TheTitans.debug("Initializing TitanSavedData()");
	}

	public TitanSavedData(String s)
	{
		super(s);
		TheTitans.debug("Initializing TitanSavedData(String)");
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		data = compound.getCompoundTag("TitansData");
		for (int i = 0; i < postEvent.length; i++)
		postEvent[i] = data.getBoolean("PostEvent" + i);
		preTitanComplete = data.getBoolean("PreTitanComplete");
		titanComplete = data.getBoolean("TitanComplete");
		postTitanComplete = data.getBoolean("PostTitanComplete");
		postEnderDragon = data.getBoolean("PostDragon");
		postWither = data.getBoolean("PostWither");
		postTwilight = data.getBoolean("PostTwilight");
		postEmperorSkulling = data.getBoolean("PostEmperorSkulling");
		postFirstTitan = data.getBoolean("PostFirstTitan");
		postWitherzilla = data.getBoolean("PostWitherzilla");
		postWitherzillaOmega = data.getBoolean("PostWitherzillaOmega");
		postExecutorDragon = data.getBoolean("PostExecutorDragon");
		postExecutorDragonOmega = data.getBoolean("PostExecutorDragonOmega");
	}

	public void writeToNBT(NBTTagCompound compound)
	{
		compound.setTag("TitansData", data);
		for (int i = 0; i < postEvent.length; i++)
		compound.setBoolean("PostEvent" + i, postEvent[i]);
		compound.setBoolean("PreTitanComplete", preTitanComplete);
		compound.setBoolean("TitanComplete", titanComplete);
		compound.setBoolean("PostTitanComplete", postTitanComplete);
		compound.setBoolean("PostDragon", postEnderDragon);
		compound.setBoolean("PostWither", postWither);
		compound.setBoolean("PostTwilight", postTwilight);
		compound.setBoolean("PostEmperorSkulling", postEmperorSkulling);
		compound.setBoolean("PostFirstTitan", postFirstTitan);
		compound.setBoolean("PostWitherzilla", postWitherzilla);
		compound.setBoolean("PostWitherzillaOmega", postWitherzillaOmega);
		compound.setBoolean("PostExecutorDragon", postExecutorDragon);
		compound.setBoolean("PostExecutorDragonOmega", postExecutorDragonOmega);
	}

	public NBTTagCompound getData()
	{
		return data;
	}

	public static TitanSavedData getStorageData(World world)
	{
		if (world != null)
		{
			MapStorage storage = world.mapStorage;
			TitanSavedData instance = (TitanSavedData)storage.loadData(TitanSavedData.class, DATA_NAME);
			if (instance == null)
			{
				TheTitans.debug("Creating a new instance of TitanSavedData as data returned null");
				instance = new TitanSavedData();
				storage.setData(DATA_NAME, instance);
			}

			return instance;
		}

		TheTitans.fatal("The world given was null", new NullPointerException());
		return null;
	}

	public boolean getBoolean(World world, String key)
	{
		return data.getBoolean(key);
	}

	public void setBoolean(World world, String key, boolean flag)
	{
		TitanSavedData instance = getStorageData(world);
		switch(key)
		{
			case "PostDragon": instance.postEnderDragon = flag; break;
			case "PostWither": instance.postWither = flag; break;
			case "PostTwilight": instance.postTwilight = flag; break;
			case "PostKrabs": instance.postEmperorSkulling = flag; break;
			case "PostFirstTitan": instance.postFirstTitan = flag; break;
			case "PostWitherzilla": instance.postWitherzilla = flag; break;
			case "PostWitherzillaOmega": instance.postWitherzillaOmega = flag; break;
			case "PostExecutorDragon": instance.postExecutorDragon = flag; break;
			case "PostExecutorDragonOmega": instance.postExecutorDragonOmega = flag; break;
			case "PreTitanComplete": instance.preTitanComplete = flag; break;
			case "TitanComplete": instance.titanComplete = flag; break;
			case "PostTitanComplete": instance.postTitanComplete = flag; break;
			case "PostEventZombie": instance.postEvent[0] = flag; break;
			case "PostEventSpeeder": instance.postEvent[1] = flag; break;
			case "PostEventTiag": instance.postEvent[2] = flag; break;
			case "PostEventSiege": instance.postEvent[3] = flag; break;
			case "PostEventNether": instance.postEvent[4] = flag; break;
			case "PostEventEnd": instance.postEvent[5] = flag; break;
			case "PostEventAwake": instance.postEvent[6] = flag; break;
			case "PostEventRumble": instance.postEvent[7] = flag; break;
			case "PostEventTrueEnd": instance.postEvent[8] = flag; break;
			case "PostEventFinalEnd": instance.postEvent[9] = flag; break;
			default:
			{
				TheTitans.warn("The key \"" + key + "\" does not exist. Skipping setBoolean()...");
				return;
			}
		}

		data.setBoolean(key, flag);
		instance.markDirty();
	}
}


