package cool.android.english.base;

public interface ICallback<RESULT> {

    void onResult(RESULT result);

    void onError(Throwable error);

}
