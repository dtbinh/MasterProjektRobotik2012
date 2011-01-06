package jadex;

import robot.*;
import jadex.bridge.*;
import jadex.micro.*;

public class ExploreAgent extends MicroAgent {
	
	MessageAgent msgAgent = new MessageAgent();
	PioneerRsB pionRsB = null;

	public void agentCreated()
	{
		System.out.println(getArgument("Starting up explore agent.."));
		msgAgent.getMessageService().tell("ExploreAgent", "Starting up..");
		try {
			pionRsB = new PioneerRsB("localhost", 6665, 1);
//			pionRG.setPlanner("localhost", 6685);
			// Planner
//			pionRG.setPosition(new Position(-28, 3, 90));
		} catch (Exception e) {
			// TODO why just JCC crashes here?
			e.printStackTrace();
			killAgent();
		}
	}
	public void executeBody()
	{
	}
	public void agentKilled()
	{
		msgAgent.getMessageService().tell("ExploreAgent", "Shutting down..");
		pionRsB.shutdown();
	}
	public static MicroAgentMetaInfo getMetaInfo()
	{
		return new MicroAgentMetaInfo("This agent starts up the Explorer agent.", 
				null, new IArgument[]{
				new Argument("welcome text", "This parameter is the argument given to the agent.", "String", "Hello world, this is a Jadex micro agent."),	
		}, null);
	}
}