package workingFrontend;

import java.util.HashMap;
import java.util.Map;

import it.unibo.qactors.akka.QActor;

public class TestActor {
	
	private QActor actor;
	private Map<String, String> dict;
	
	public TestActor(QActor actor) {
		this.actor = actor;
		this.dict = new HashMap<String, String>();
	}

	public QActor getActor() {
		return actor;
	}

	public Map<String, String> getDict() {
		return dict;
	}
	
	
}