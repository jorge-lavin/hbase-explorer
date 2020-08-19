package com.example.gui;

import com.example.app.AppVersion;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;

//A menu on top, clusters panel on the left and tables panel on the right.
public class StartWindow extends JFrame
{
    private final MenuBar menuBar = new MenuBar();
    private final ClustersPanel clustersPanel = new ClustersPanel();
    private final TablesPanel tablesPanel = new TablesPanel();

    public StartWindow(AppVersion appVersion)
    {
        super();
        setSize(new Dimension(640, 480)); // FIXME Hardcoded
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(title(appVersion));

        setJMenuBar(menuBar);

        setLayout(new GridLayout(1,2));
        add(clustersPanel);
        add(tablesPanel);

        setVisible(true);
    }

    private static String title(AppVersion appVersion)
    {
        return "HBase Explorer " + appVersion.version();
    }

}
