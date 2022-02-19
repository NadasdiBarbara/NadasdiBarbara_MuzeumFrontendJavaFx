package hu.petrik.muzeumfrontendjavafx;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class StatueApi {
    private static final String BASE_URL = "http://127.0.0.1:8000";
    public static final String STATUE_API_URL = BASE_URL + "/api/statues";

    public static List<Statue> getStatues() throws IOException {
        Response response = RequestHandler.get(STATUE_API_URL);
        String json = response.getContent();
        Gson jsonConvert = new Gson();
        if (response.getResponseCode() >= 400){
            String message = jsonConvert.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }
        Type type = new TypeToken<List<Statue>>(){}.getType();
        return jsonConvert.fromJson(json,type);
    }

    public static Statue addStatue(Statue newStatue) throws IOException {
        Gson jsonConvert = new Gson();
        String statueJson = jsonConvert.toJson(newStatue);
        Response response = RequestHandler.post(STATUE_API_URL, statueJson);

        String json = response.getContent();
        if (response.getResponseCode() >= 400){
            String message = jsonConvert.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }
        return jsonConvert.fromJson(json, Statue.class);
    }
    public static Statue modStatue(Statue modositando) throws IOException {
        Gson jsonConvert = new Gson();
        String statueJson = jsonConvert.toJson(modositando);
        Response response = RequestHandler.put(STATUE_API_URL + "/" + modositando.getId(), statueJson);

        String json = response.getContent();
        if (response.getResponseCode() >= 400){
            String message = jsonConvert.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }
        return jsonConvert.fromJson(json, Statue.class);
    }

    public static boolean deleteStatue(int id) throws IOException {
        Response response = RequestHandler.delete(STATUE_API_URL+ "/" + id);

        Gson jsonConvert = new Gson();
        String json = response.getContent();
        if (response.getResponseCode() >= 400){
            String message = jsonConvert.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }
        return response.getResponseCode() == 204;
    }
}
