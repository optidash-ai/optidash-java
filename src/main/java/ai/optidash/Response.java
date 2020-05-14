package ai.optidash;

public interface Response  {
    boolean isSuccessful();
    String getMessage();
    Metadata getMetadata();
}
