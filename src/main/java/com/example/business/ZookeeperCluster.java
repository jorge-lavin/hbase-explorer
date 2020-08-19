package com.example.business;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ZookeeperCluster
{
    private final String name;
    private final Set<ZookeeperNode> nodes;

    public ZookeeperCluster(String name, Set<ZookeeperNode> nodes)
    {
        this.name = name;
        this.nodes = nodes;
    }

    @Override
    public String toString() {
       return "ZookeeperCluster{name=" + name + ",nodes=[" + nodesToString() +"]}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZookeeperCluster that = (ZookeeperCluster) o;
        return Objects.equals(name, that.name) && Objects.equals(nodes, that.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nodes);
    }

    private String nodesToString()
    {
        return nodes.stream().map(ZookeeperNode::toString).collect(Collectors.joining(","));
    }
}