package com.example.gui;

import javax.swing.SwingUtilities;

// TODO log
// TODO title
// TODO icon
// TODO LoadVersion from file
public class HBaseExplorerApp
{

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(HBaseExplorerApp::createAndShowGUI);
    }

    public static void createAndShowGUI()
    {
        final StartWindow window = new StartWindow();
    }
}
