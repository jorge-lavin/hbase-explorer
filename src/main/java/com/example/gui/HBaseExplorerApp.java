package com.example.gui;

import com.example.app.AppVersion;
import com.example.app.ResourcesFileAppVersionLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;

public class HBaseExplorerApp
{
    private final static Logger logger = LoggerFactory.getLogger(HBaseExplorerApp.class);

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(HBaseExplorerApp::createAndShowGUI);
    }

    public static void createAndShowGUI()
    {
        AppVersion appVersion = new ResourcesFileAppVersionLoader().load();
        appVersion.log(logger);

        new StartWindow(appVersion);
    }

}
