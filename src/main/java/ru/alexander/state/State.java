package ru.alexander.state;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ru.alexander.api.responses.ErrorResponse;

public abstract class State<T> {
    @Setter(AccessLevel.PACKAGE)
    @Getter(AccessLevel.PROTECTED)
    private T value;

    @Setter(AccessLevel.PACKAGE)
    private StateMachine machine;


    public abstract void onCall();
    public abstract void onError(ErrorResponse error);

    protected  <D> void transit(String stateName, String messageType, D data) {
        machine.transit(this, stateName, messageType, data);
    }
}
