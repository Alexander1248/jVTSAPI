package ru.alexander.api.listeners;

import ru.alexander.api.responses.ErrorResponse;

public abstract class ResponseAdapter<S> implements ResponseListener<S> {
    @Override
    public void onSuccess(S value) {}

    @Override
    public void onFailure(ErrorResponse error) {
        System.err.println(error);
    }
}
