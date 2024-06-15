package ru.alexander.api.listeners;


import ru.alexander.api.responses.ErrorResponse;

public interface ResponseListener<S> {
    void onSuccess(S value);
    void onFailure(ErrorResponse error);
}
