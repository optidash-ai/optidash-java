package ai.optidash.support.io;

import static java.nio.file.StandardOpenOption.READ;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class Streams {

    public static FileSource of(final Path file) {
        return new FileSource() {
            @Override
            public String getFileName() {
                return file.getFileName().toString();
            }

            @Override
            public InputStream openStream() throws IOException {
                return Files.newInputStream(file, READ);
            }
        };
    }

    public static FileSource of(final URL url) {
        return new FileSource() {
            @Override
            public InputStream openStream() {
                try {
                    return url.openStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String getFileName() {
                return url.getFile();
            }
        };
    }
}
