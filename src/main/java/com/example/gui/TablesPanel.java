package com.example.gui;

import javax.swing.*;
import java.awt.*;

public class TablesPanel extends JTabbedPane
{
    public TablesPanel()
    {
        ImageIcon icon = null;
        Component panel1 = makeTextPanel("Blah");
        addTab("One", icon, panel1, "Does nothing");
        setSelectedIndex(0);

        Component panel2 = makeTextPanel("Blah blah");
        addTab("Two", icon, panel2, "Does twice as much nothing");

        Component panel3 = makeTextPanel("Blah blah blah");
        addTab("Three", icon, panel3, "Still does nothing");

        Component panel4 = makeTextPanel("Blah blah blah blah");
        addTab("Four", icon, panel4, "Does nothing at all");
    }

    protected Component makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
}
