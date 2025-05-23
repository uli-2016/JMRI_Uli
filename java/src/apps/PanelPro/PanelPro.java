package apps.PanelPro;

import apps.Apps;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import jmri.util.JmriJFrame;

/**
 * The JMRI program for creating control panels.
 * <p>
 * If an argument is provided at startup, it will be used as the name of the
 * configuration file. Note that this is just the name, not the path; the file
 * is searched for in the usual way, first in the preferences tree and then in
 * xml/
 *
 * <hr>
 * This file is part of JMRI.
 * <p>
 * JMRI is free software; you can redistribute it and/or modify it under the
 * terms of version 2 of the GNU General Public License as published by the Free
 * Software Foundation. See the "COPYING" file for a copy of this license.
 * <p>
 * JMRI is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * @author Bob Jacobsen Copyright 2003
 */
public class PanelPro extends Apps {

    PanelPro() {
        super();
    }

    @Override
    protected String logo() {
        return "resources/PanelPro.gif";
    }

    @Override
    protected String mainWindowHelpID() {
        return "package.apps.PanelPro.PanelPro";
    }

    @Override
    protected String line1() {
        return Bundle.getMessage("PanelProVersionCredit", jmri.Version.name());
    }

    @Override
    protected String line2() {
        return "https://jmri.org/PanelPro";
    }

    /**
     * JPanel displayed as PanelPro main screen.
     */
    @Override
    protected JPanel statusPanel() {
        JPanel j = new JPanel();
        j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
        j.add(super.statusPanel());

        // Buttons
        Action quit = new AbstractAction(Bundle.getMessage("MenuItemQuit")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Apps.handleQuit();
            }
        };

        JPanel p3 = new JPanel();
        p3.setLayout(new java.awt.FlowLayout());
        h1 = new JButton(Bundle.getMessage("ButtonHelp"));
        // as globalHelpBroker is still null, wait to attach help target after help menu is created
        h1.setAlignmentX(Component.CENTER_ALIGNMENT);
        p3.add(h1);
        JButton q1 = new JButton(Bundle.getMessage("ButtonQuit"));
        q1.addActionListener(quit);
        q1.setAlignmentX(Component.CENTER_ALIGNMENT);
        p3.add(q1);
        j.add(p3);

        return j;
    }

    /**
     * Help button on Main Screen.
     */
    private JButton h1;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void attachHelp() {
        if (h1 != null) {
            jmri.util.HelpUtil.addHelpToComponent(h1, "html.apps.PanelPro.index");
        }
    }

    // Main entry point
    public static void main(String args[]) {

        // Set up system properties that needs to be loaded early
        jmri.util.EarlyInitializationPreferences.getInstance().loadAndSetPreferences();

        // show splash screen early
        splash(true);

        Apps.setStartupInfo("PanelPro");

        setConfigFilename("PanelProConfig2.xml", args);
        PanelPro p = new PanelPro();
        JmriJFrame f = new JmriJFrame(jmri.Application.getApplicationName());
        createFrame(p, f);

        log.info("Main initialization done");
        splash(false);
    }

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PanelPro.class);

}
