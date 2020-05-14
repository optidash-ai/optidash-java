package ai.optidash.support.io;

import com.google.common.io.ByteSource;

public abstract class FileSource extends ByteSource {


    public abstract String getFileName() ;
}
