/**
 * 
 */
package test.robot;

import java.util.concurrent.CopyOnWriteArrayList;

import junit.framework.JUnit4TestAdapter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import robot.Pioneer;
import robot.external.IPioneer;
import data.Host;
import device.Device;
import device.DeviceNode;
import device.external.IDevice;

/**
 * @author sebastian
 *
 */
public class PioneerTest
{
    static DeviceNode dn;
    static Pioneer pion;
    
    @BeforeClass public static void setUpBeforeClass() throws Exception
    {
    	int port = 6667;
        String host = "localhost";
 
        /** Device list */
        CopyOnWriteArrayList<Device> devList = new CopyOnWriteArrayList<Device>();
        devList.add( new Device(IDevice.DEVICE_RANGER_CODE,host,port,-1) );
        devList.add( new Device(IDevice.DEVICE_SONAR_CODE,host,port,-1) );
        devList.add( new Device(IDevice.DEVICE_POSITION2D_CODE,host,port,-1) );
        
        /** Host list */
        CopyOnWriteArrayList<Host> hostList = new CopyOnWriteArrayList<Host>();
        hostList.add(new Host(host,port));

        dn = new DeviceNode(hostList.toArray(new Host[hostList.size()]), devList.toArray(new Device[devList.size()]));
        dn.runThreaded();
        
        pion = new Pioneer(dn.getDeviceListArray());
        assertNotNull(pion);
        
        pion.runThreaded();
        assertTrue(pion.isThreaded());
    }
    @AfterClass public static void tearDownAfterClass() throws Exception
    {
        pion.shutdown();
        dn.shutdown();
    }
    /**
     * Test method for {@link robot.Robot#setSpeed(double)}.
     */
    @Test public void testSetSpeed()
    {
        pion.setCommand();
        pion.setSpeed(0.4);
        pion.setTurnrate(0.0);
        
        try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); }
        
        assertTrue(pion.getSpeed() > 0.0);
        testStop();
    }

    @Test public void testSetSpeed2()
    {
        pion.setCommand();
        pion.setSpeed(-0.4);
        pion.setTurnrate(0.0);
        
        try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); }
        
        assertTrue(pion.getSpeed() < 0.0);
    }

    /**
     * Test method for {@link robot.Robot#setTurnrate(double)}.
     */
    @Test public void testSetTurnrate()
    {
        testStop();
        pion.setCommand();
        pion.setTurnrate(0.4);
        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
        
        assertTrue(pion.getTurnrate() > 0.0);
    }
    @Test public void testSetTurnrate2()
    {
        pion.setCommand();
        pion.setTurnrate(-0.4);
        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
        
        assertTrue(pion.getTurnrate() < 0.0);
    }
    @Test public void testStop()
    {
        pion.stop();
        assertTrue(pion.getSpeed() == 0.0);
    }
    @Test public void testWallfollow() 
    {
        pion.setWallfollow();
        assertTrue(pion.getCurrentState() == IPioneer.StateType.LWALL_FOLLOWING);
        try { Thread.sleep(30000); } catch (InterruptedException e) { e.printStackTrace(); }

    }
    @Test public void testSetSpeed3()
    {
        pion.setCommand();
        testSetSpeed();
    }

    /** To use JUnit  test suite */
    public static JUnit4TestAdapter suite()
    { 
       return new JUnit4TestAdapter(PioneerTest.class); 
    }
}
