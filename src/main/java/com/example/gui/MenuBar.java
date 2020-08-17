package com.example.gui;

import javax.swing.*;

// http://zetcode.com/javaswing/menusandtoolbars/
public class MenuBar extends JMenuBar
{
    JMenu fileMenu = new JMenu("File");
    JMenu viewMenu = new JMenu("View");
    JMenu toolsMenu = new JMenu("Tools");
    JMenu helpMenu = new JMenu("Help");

    public MenuBar()
    {
        super();
        add(fileMenu);
        add(viewMenu);
        add(toolsMenu);
        add(Box.createHorizontalGlue());
        add(helpMenu);

    }
}
