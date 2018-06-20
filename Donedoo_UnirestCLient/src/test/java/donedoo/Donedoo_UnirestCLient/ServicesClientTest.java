package donedoo.Donedoo_UnirestCLient;

import java.util.List;

import org.junit.Test;

import com.donedoo.client.ServicesClient;
import com.donedoo.data.Change;
import com.donedoo.data.Tasks;

import static org.junit.Assert.*;

public class ServicesClientTest {

	
	@Test
	public void testPutUpdate() {
		ServicesClient client = new ServicesClient();
		
		Tasks task = new Tasks();
		task.setTaskName("dodany testerem2");
		task.setCompletedBy("Pio");
		task.setGroupName("1");			
		
		client.updateTask(task);
		
		assertNotNull(task);
	}
	
	@Test
	public void testCreate() {
		ServicesClient client = new ServicesClient();
		
		Tasks task = new Tasks();
		task.setTaskName("dodany testerem3");
		task.setPostedBy("Pawel");
		task.setGroupName("1");		
		
		client.createTask(task);
		
		assertNotNull(task);
	}
	
	@Test
	public void testGetChange() {
		ServicesClient client = new ServicesClient();
		
		Change change = client.getChange("1");
		
		System.out.println(change.getChangeType());
		
		assertNotNull(change);
	}
	
	@Test
	public void testGetList() {
		ServicesClient client = new ServicesClient();
		
		List<Tasks> tasks = client.getTasks("1");
		
		System.out.println(tasks);
		
		assertNotNull(tasks);
	}
	
	//tests to do:
	
	
	@Test
	public void testGetGroupUsername() {
		
		ServicesClient client = new ServicesClient();
		
		boolean bool = client.getGroupUserPassword("0","0","0");
			
		
	}
	
	@Test
	public void testsetChange() {
		ServicesClient client = new ServicesClient();
		
		Change change = new Change();
		change.setGroupName("2");
		change.setChangeType("test");
		
		client.createChange(change);
		
		
	}
	
	@Test
	public void testPut() {
		ServicesClient client = new ServicesClient();
		
		Tasks task = new Tasks();
		//task.setTaskName("dodany testerem2");
		task.setCompletedBy("2");
		task.setClaimedBy("2");
		task.setGroupName("2");			
		
		client.updateTask(task);
		
		assertNotNull(task);
	}
	
}
