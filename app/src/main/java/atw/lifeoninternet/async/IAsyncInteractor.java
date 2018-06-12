package atw.lifeoninternet.async;

import java.util.Map;

/**
 * Created by Vivek on 22/03/18.
 */
public interface IAsyncInteractor {
    //   void validateCredentialsAsync(OnRequestListener listener, String url);

    void validateCredentialsAsync(String method,OnRequestListener listener, int pid, String url);
    void validateCredentialsAsync(String method,OnRequestListener listener, int pid, String url, Map<String, String> data);

}
