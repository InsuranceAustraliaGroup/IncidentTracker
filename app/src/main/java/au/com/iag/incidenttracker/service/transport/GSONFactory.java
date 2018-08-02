package au.com.iag.incidenttracker.service.transport;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

public class GSONFactory {

    public static Gson getGsonInstance() {
        return new GsonBuilder()
                .create();
    }

    private static <T> TypeAdapter<T> emptyTypeAdapter() {
        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {

            }

            @Override
            public T read(JsonReader in) throws IOException {
                return null;
            }
        };
    }
}
