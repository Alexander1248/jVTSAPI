package ru.alexander;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.alexander.api.VTubeStudioAPI;
import ru.alexander.api.listeners.ResponseAdapter;
import ru.alexander.calls.events.TestEventCall;

import java.io.IOException;
import java.util.LinkedHashMap;

public class TestPlugin {

    @Test
    @Disabled
    public void testVTSApi() throws IOException {
        VTubeStudioAPI api = new VTubeStudioAPI();

        TestEventCall call = new TestEventCall("Hello World!", null,
                value -> System.out.println(value.getMessage() + " - " + value.getCounter()));
        ResponseAdapter<LinkedHashMap<String, Object>> auth = new ResponseAdapter<>() {
            @Override
            public void onSuccess(LinkedHashMap<String, Object> value) {
                System.out.println(value);
                call.execute(api);
            }
        };

        ResponseAdapter<LinkedHashMap<String, Object>> token = new ResponseAdapter<>() {
            @Override
            public void onSuccess(LinkedHashMap<String, Object> value) {
                System.out.println(value);
                api.auth(
                        "Test Plugin",
                        "Alexander",
                        (String) value.get("authenticationToken"),
                        auth
                );
            }
        };
        api.requestToken(
                "Test Plugin",
                "Alexander",
                null,
                token);

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }
    }

}

