package ai.optidash;

import java.io.IOException;
import java.nio.file.Path;

import ai.optidash.support.ByteSourceResponse;

public interface Request {
    Response toJson();

    ByteSourceResponse toBuffer();

    Response toFile(String output) throws IOException;

    Response toFile(Path output) throws IOException;
}
