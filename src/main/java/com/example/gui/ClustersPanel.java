package com.example.gui;

import com.example.app.UserPreferencesFileRepository;
import com.example.business.ZookeeperCluster;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.GridLayout;
import java.util.List;

public class ClustersPanel extends JPanel
{
    private final static String addClusterLabel = "Add Cluster";
    private final static String removeClusterLabel = "Remove Cluster";

    private ClustersTree clustersTree;
    private JButton addCluster;
    private JButton removeCluster;

    public ClustersPanel()
    {
        setLayout(new GridLayout(2,1));
        clustersTree = new ClustersTree(ClustersTree.rootNode());

        for (ZookeeperCluster cluster: clusters())
        {
            clustersTree.add(cluster);
        }

        add(clustersTree);

        // TODO Show popup to add cluster.
        JPanel buttonPanel = new JPanel();
        addCluster = new JButton(addClusterLabel);
        buttonPanel.add(addCluster);
        removeCluster = new JButton(removeClusterLabel);
        buttonPanel.add(removeCluster);

        add(buttonPanel);
    }


    private static class ClustersTree extends JTree
    {
        private DefaultMutableTreeNode rootNode;

        public ClustersTree(DefaultMutableTreeNode rootNode)
        {
            super(rootNode);
            this.rootNode = rootNode;
        }

        // TODO Update UI when cluster is added.
        // TODO Update user preferences when cluster is added
        void add(ZookeeperCluster cluster)
        {
            rootNode.add(new DefaultMutableTreeNode(cluster));
        }

        // TODO Update UI when cluster is removed.
        // TODO Update user preferences wwhen cluster is removed
        void remove(ZookeeperCluster cluster)
        {
            rootNode.remove(new DefaultMutableTreeNode(cluster));
        }

        private static DefaultMutableTreeNode rootNode()
        {
            return new DefaultMutableTreeNode("Clusters");
        }
    }



    private List<ZookeeperCluster> clusters()
    {
        // TODO This interface should be injected somehow.
        return new UserPreferencesFileRepository().load().clusters;
    }
}
