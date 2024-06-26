package jmri.jmrix.jmriclient.configurexml;

import org.jdom2.Element;

/**
 * Provides load and store functionality for configuring
 * JMRIClientTurnoutManagers.
 * <p>
 * Uses the store method from the abstract base class, but provides a load
 * method here.
 *
 * @author Bob Jacobsen Copyright: Copyright (c) 2008
 */
public class JMRIClientTurnoutManagerXml extends jmri.managers.configurexml.AbstractTurnoutManagerConfigXML {

    public JMRIClientTurnoutManagerXml() {
        super();
    }

    @Override
    public void setStoreElementClass(Element turnouts) {
        turnouts.setAttribute("class", "jmri.jmrix.jmriclient.configurexml.JMRIClientTurnoutManagerXml");
    }

    @Override
    public boolean load(Element shared, Element perNode) {
        // load individual turnouts
        return loadTurnouts(shared, perNode);
    }

    // initialize logging
//    private final static Logger log = LoggerFactory.getLogger(JMRIClientTurnoutManagerXml.class);
}
