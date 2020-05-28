package interfaces;

public interface NetworkObserver {
    void doOnPost(String response);
    void doOnGet(String response);
    void doOnPut(String response);
    void doOnError(String response);
}
