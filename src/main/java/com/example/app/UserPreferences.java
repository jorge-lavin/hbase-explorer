package com.example.app;

import com.example.business.ZookeeperCluster;

import java.util.List;
import java.util.Objects;

/** Represents the user defined preferences for the application **/
public class UserPreferences
{
    public final List<ZookeeperCluster> clusters;

    public UserPreferences(List<ZookeeperCluster> clusters)
    {
        this.clusters = clusters;
    }

    @Override
    public String toString() {
        return "UserPreferences{" + "clusters=" + clusters + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPreferences that = (UserPreferences) o;
        return Objects.equals(clusters, that.clusters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clusters);
    }
}
