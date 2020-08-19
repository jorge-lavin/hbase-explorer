package com.example.app;

public interface UserPreferencesRepository
{
    UserPreferences load();
    void store(UserPreferences preferences);
    void delete();
}
