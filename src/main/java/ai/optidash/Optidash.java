package ai.optidash;

import static com.google.common.base.Preconditions.checkNotNull;
import static okhttp3.Credentials.basic;
import static okhttp3.RequestBody.create;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

import okhttp3.*;

import ai.optidash.support.BaseRequest;
import ai.optidash.support.Json;
import ai.optidash.support.io.FileSource;
import ai.optidash.support.io.Streams;

public class Optidash {
    private static URI baseUrl = URI.create("https://api.optidash.ai/");

    static void baseUrl(URI baseUrl) {
        Optidash.baseUrl = baseUrl;
    }

    private final String apiKey;

    private final OkHttpClient client;

    public Optidash(final String apiKey) {
        this.apiKey = checkNotNull(apiKey, "API key can not be null");
        client = new OkHttpClient.Builder()
                     .addInterceptor(new Interceptor() {
                         @Override
                         public okhttp3.Response intercept(Chain chain) throws IOException {
                             return chain.proceed(chain
                                                      .request()
                                                      .newBuilder()
                                                      .addHeader("Authorization", basic(apiKey, ""))
                                                      .build());
                         }
                     })
                     .build();
    }

    public Fetch fetch(URI source) {
        return new FetchImpl(checkNotNull(source, "source can not be null").toString());
    }

    public Fetch fetch(String source) {
        return new FetchImpl(checkNotNull(source, "source can not be null"));
    }

    public Upload upload(String input) {
        return upload(Streams.of(Paths.get(checkNotNull(input, "input can not be null"))));
    }

    public Upload upload(FileSource input) {
        return new UploadImpl(checkNotNull(input, "input can not be null"));
    }

    private final class FetchImpl extends BaseRequest<Fetch> implements Fetch {
        private final Dynamic body = Dynamic.dynamic();

        public FetchImpl(String source) {
            super(client);
            body.set("url", source);
        }

        @Override
        protected Dynamic metadata() {
            return body;
        }

        @Override
        protected RequestBody body() throws IOException {
            return RequestBody.create(MediaType.parse("application/json"), new Json().toJson(this.metadata()));
        }

        @Override
        protected HttpUrl url() {
            return new HttpUrl.Builder()
                       .scheme(baseUrl.getScheme())
                       .host(baseUrl.getHost())
                       .port(baseUrl.getPort())
                       .addPathSegments("1.0/fetch")
                       .build();
        }
    }

    private final class UploadImpl extends BaseRequest<Upload> implements Upload {

        private final Dynamic body = Dynamic.dynamic();

        private final FileSource input;

        public UploadImpl(FileSource input) {
            super(client);
            this.input = input;
        }

        @Override
        protected HttpUrl url() {
            return new HttpUrl.Builder()
                       .scheme(baseUrl.getScheme())
                       .host(baseUrl.getHost())
                       .port(baseUrl.getPort())
                       .addPathSegments("1.0/upload")
                       .build();
        }


        @Override
        protected Dynamic metadata() {
            return body;
        }

        @Override
        protected RequestBody body() throws IOException {
            return new MultipartBody.Builder()
                       .addFormDataPart("file", input.getFileName(), create(MediaType.parse("application/octet-stream"), input.read()))
                       .addFormDataPart("data", new Json().toJson(metadata()))
                       .build();
        }

    }

}
