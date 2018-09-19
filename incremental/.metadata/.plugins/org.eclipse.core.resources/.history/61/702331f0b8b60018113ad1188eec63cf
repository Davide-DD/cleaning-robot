package secondTest;

import java.util.HashSet;
import java.util.Set;

import it.unibo.qactors.QActorContext;
import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;
import it.unibo.system.SituatedSysKb;

public class CtxRun implements Runnable {

	private String name, systemTheoryName, systemRulesFile;
	private String[] actorIds;
	private Set<QActor> qactors;
	
	public CtxRun(String name, String systemTheoryName, String systemRulesFile, String[] actorIds) {
		this.name = name;
		this.systemTheoryName = systemTheoryName;
		this.systemRulesFile = systemRulesFile;
		this.actorIds = actorIds;
		this.qactors = new HashSet<QActor>();
	}
	
	public Set<QActor> getQActors() {
		return qactors;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			QActorContext.initQActorSystem(name, systemTheoryName, systemRulesFile, 
					SituatedSysKb.standardOutEnvView, null, false);
			
			while(qactors.size() != actorIds.length) {
				Thread.sleep(250);
					
				for (String id : actorIds) {
					qactors.add(QActorUtils.getQActor(id));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
