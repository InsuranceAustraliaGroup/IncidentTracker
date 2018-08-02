package au.com.iag.incidenttracker.service.transport;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <T> T createService(Gson gson, Class<T> clientClass, String endpoint) {

        AuthenticationInterceptor interceptor = new AuthenticationInterceptor("apikey Gr4wrUKNkINPHe1WhlaNBnLfnWc04u5RGCta");

        httpClient.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return retrofit.create(clientClass);
    }
}

