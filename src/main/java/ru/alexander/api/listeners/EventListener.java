package ru.alexander.api.listeners;

public interface EventListener<S> {
    void onEvent(S value);
}
