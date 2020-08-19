package com.example.business;

import java.util.Objects;

public class ZookeeperNode
{
    private final String host;
    private final int port;

    public ZookeeperNode(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZookeeperNode that = (ZookeeperNode) o;
        return port == that.port &&
                Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }
}
