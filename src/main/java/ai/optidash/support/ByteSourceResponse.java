package ai.optidash.support;

import static ai.optidash.Dynamic.dynamic;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

import ai.optidash.Dynamic;
import ai.optidash.Metadata;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.io.ByteSource;

public class ByteSourceResponse extends ByteSource implements ai.optidash.Response, AutoCloseable {
    private final Response response;

    private final Supplier<Dynamic> metadata = Suppliers.memoize(new Supplier<Dynamic>() {
        @Override
        public Dynamic get() {
            final String metadata = response.header("X-Optidash-Meta");

            if (metadata != null && !Strings.isNullOrEmpty(metadata)) {
                return new Json().fromJson(metadata, Dynamic.class);
            } else {
                return dynamic();
            }
        }
    });

    public ByteSourceResponse(final Response response) {
        this.response = response;
    }

    @Override
    public Optional<Long> sizeIfKnown() {
        return Optional.of(response
                               .body()
                               .contentLength());
    }

    @Override
    public InputStream openStream() throws IOException {
        validate(response);
        return response
                   .body()
                   .source()
                   .inputStream();
    }

    @Override
    public String getMessage() {
        return getMetadata().get("message");
    }

    @Override
    public Metadata getMetadata() {
        final Dynamic dynamic = metadata.get();
        return DynamicMetadata.of(dynamic);

    }

    @Override
    public boolean isSuccessful() {
        return response.isSuccessful() && getMetadata().<Boolean>get("success");
    }

    public void close() {
        response.close();
    }

    private void validate(Response response) {
        checkState(isSuccessful(), "request failed " + response.message());
    }

}
