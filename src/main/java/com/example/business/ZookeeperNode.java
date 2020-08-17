package com.example.business;

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
}
