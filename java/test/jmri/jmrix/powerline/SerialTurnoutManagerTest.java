package jmri.jmrix.powerline;

import jmri.NamedBean;
import jmri.Turnout;
import jmri.jmrix.powerline.simulator.SpecificSystemConnectionMemo;
import jmri.util.JUnitAppender;
import jmri.util.JUnitUtil;

import java.beans.PropertyVetoException;

import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 * SerialTurnoutManagerTest.java
 *
 * Test for the SerialTurnoutManager class
 *
 * @author Bob Jacobsen Copyright 2004, 2008 Converted to multiple connection
 * @author kcameron Copyright (C) 2011
 */
public class SerialTurnoutManagerTest extends jmri.managers.AbstractTurnoutMgrTestBase {

    private SerialTrafficControlScaffold nis = null;

    @Override
    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
        SpecificSystemConnectionMemo memo = new SpecificSystemConnectionMemo();
        // prepare an interface, register
        nis = new SerialTrafficControlScaffold();
        nis.setAdapterMemo(memo);
        memo.setTrafficController(nis);
        memo.setSerialAddress(new SerialAddress(memo));
        // create and register the manager object
        l = new SerialTurnoutManager(nis);
        jmri.InstanceManager.setTurnoutManager(l);
    }

    @Override
    public String getSystemName(int n) {
        return "PTB" + n;
    }
    
    @Override
    protected String getASystemNameWithNoPrefix() {
        return "B2";
    }

    @Test
    public void testAsAbstractFactory() {
        // ask for a Turnout, and check type
        Turnout o = l.newTurnout("PTB1", "my name");
        Assert.assertNotNull( o );
        Assert.assertTrue( o instanceof SerialTurnout );

        // make sure loaded into tables
        Assert.assertNotNull( l.getBySystemName("PTB1"));
        Assert.assertNotNull( l.getByUserName("my name"));

    }

    @Override
    @Test
    public void testProvideName() {
        // create
        Turnout t = l.provide(getSystemName(getNumToTest1()));
        // check
        Assert.assertNotNull("real object returned ", t );
        Assert.assertTrue("system name correct ", t == l.getBySystemName(getSystemName(getNumToTest1())));
    }

    @Override
    @Test
    public void testDefaultSystemName() {
        // create
        Turnout t = l.provideTurnout(getSystemName(getNumToTest1()));
        // check
        Assert.assertNotNull("real object returned ", t );
        Assert.assertTrue("system name correct ", t == l.getBySystemName(getSystemName(getNumToTest1())));
    }

    @Override
    @Test
    public void testUpperLower() {
        Turnout t = l.provideTurnout(getSystemName(getNumToTest2()));

        Assert.assertNull(l.getTurnout(t.getSystemName().toLowerCase()));
    }

    @Override
    @Test
    public void testRegisterDuplicateSystemName() throws PropertyVetoException, NoSuchFieldException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        String s1 = l.makeSystemName("B1");
        String s2 = l.makeSystemName("B2");
        testRegisterDuplicateSystemName(l, s1, s2);
    }

    @Override
    @Test
    public void testMakeSystemName() {
        try {
            l.makeSystemName("1");
            Assert.fail("Expected exception not thrown");
        } catch (NamedBean.BadSystemNameException ex) {
            Assert.assertEquals("\"PT1\" is not a recognized format.", ex.getMessage());
        }
        JUnitAppender.assertErrorMessage("Invalid system name for Turnout: \"PT1\" is not a recognized format.");
        String s = l.makeSystemName("B1");
        Assert.assertNotNull(s);
        Assert.assertFalse(s.isEmpty());
    }

    @AfterEach
    public void tearDown() {
        JUnitUtil.clearShutDownManager(); // put in place because AbstractMRTrafficController implementing subclass was not terminated properly
        JUnitUtil.tearDown();

    }

//    private final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SerialTurnoutManagerTest.class);

}
