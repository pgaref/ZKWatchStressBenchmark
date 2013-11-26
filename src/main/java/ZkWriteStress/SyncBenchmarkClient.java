package ZkWriteStress;
import java.io.IOException;
import org.apache.log4j.Logger;



public class SyncBenchmarkClient extends BenchmarkClient {

	long StartTime;
	private boolean done;

	private static final Logger LOG = Logger.getLogger(SyncBenchmarkClient.class);

	
	public SyncBenchmarkClient(String host, String namespace,
			int attempts, int id) throws IOException {
		super(host, namespace, attempts, id);
	}
	
	@Override
	protected void submit(int n) {
		StartTime = System.currentTimeMillis();
		try {
			submitWrapped(n);
		} catch (Exception e) {
			// What can you do? for some reason
			// com.netflix.curator.framework.api.Pathable.forPath() throws Exception
			LOG.error("Error while submitting requests", e);
		}
	}
		
	protected void submitWrapped(int n) throws Exception {
		done = false;
		byte data[];
		long i = 0; 
		while((System.currentTimeMillis() - StartTime)/1000 <n ) {
			data = new String("pgaref AcaZoo DATAAAAA"+i++).getBytes();
			_client.create().forPath(_path + "/" + _count, data);
			_count++;

			if (done)
				break;
		}
		
	}
	
	@Override
	protected void finish() {
		done = true;
	}
}
