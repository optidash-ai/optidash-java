package ai.optidash.support;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import ai.optidash.Dynamic;
import ai.optidash.Metadata;
import ai.optidash.Operations;

import com.google.common.base.Optional;
import com.google.common.io.Files;
import com.google.gson.Gson;

public abstract class BaseRequest<T extends Operations> extends BaseOperations<T> {
    private final OkHttpClient client;

    public BaseRequest(OkHttpClient client) {
        this.client = client;
    }

    @Override
    protected abstract Dynamic metadata();

    public ai.optidash.Response toJson() {
        try {
            final okhttp3.Request request = new okhttp3.Request.Builder()
                                                .url(url())
                                                .post(body())
                                                .build();
            try (okhttp3.Response response = client
                                                 .newCall(request)
                                                 .execute()) {
                return new JsonResponse(Dynamic.of(new Gson().fromJson(response
                                                                           .body()
                                                                           .charStream(), Map.class)));
            }
        } catch (IOException e) {
            return new ExceptionResponse(e);
        }
    }

    public ByteSourceResponse toBuffer() {
        try {
            validate();
            final okhttp3.Request request = new Request.Builder()
                                                .url(url())
                                                .addHeader("X-Optidash-Binary", "true")
                                                .post(body())
                                                .build();
            return new ByteSourceResponse(client
                                              .newCall(request)
                                              .execute());
        } catch (final IOException | IllegalStateException | UnsupportedOperationException io) {
            final ExceptionResponse delegate = new ExceptionResponse(io);
            return new ByteSourceResponse(null) {
                @Override
                public Optional<Long> sizeIfKnown() {
                    return Optional.absent();
                }

                @Override
                public InputStream openStream() throws IOException {
                    throw io;
                }

                @Override
                public String getMessage() {
                    return delegate.getMessage();
                }

                @Override
                public Metadata getMetadata() {
                    return delegate.getMetadata();
                }

                @Override
                public boolean isSuccessful() {
                    return delegate.isSuccessful();
                }

                @Override
                public void close() {
                }
            };
        }

    }

    private void validate() {
        checkState(metadata().get("webhook") == null,"Can't use Webhooks with binary responses");
        checkState(metadata().get("store") == null,"Can't use External Storage with binary responses");
    }

    protected abstract RequestBody body() throws IOException;

    protected abstract HttpUrl url();

    public ai.optidash.Response toFile(String output) throws IOException {
        return toFile(Paths.get(output));
    }

    public ai.optidash.Response toFile(Path output) throws IOException {
        final ByteSourceResponse byteSource = toBuffer();
        if (byteSource.isSuccessful()) {
            byteSource.copyTo(Files.asByteSink(output.toFile()));
        }
        return byteSource;
    }
}
