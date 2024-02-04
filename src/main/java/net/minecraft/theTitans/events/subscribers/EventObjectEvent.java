package net.minecraft.theTitans.events.subscribers;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.theTitans.events.EventObject;
public class EventObjectEvent extends Event
{
	EventObject eventObj;
	public EventObjectEvent(EventObject eventObj)
	{
		this.eventObj = eventObj;
	}

	@Cancelable
	public static class EventDead extends EventObjectEvent
	{
		public EventDead(EventObject eventObj)
		{
			super(eventObj);
		}
	}
}


