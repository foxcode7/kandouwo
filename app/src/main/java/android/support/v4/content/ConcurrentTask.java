package android.support.v4.content;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: panmingwei
 * Date: 13-6-19
 * Time: 上午10:33
 */
public abstract class ConcurrentTask<Params, Progress, Result> extends ModernAsyncTask<Params, Progress, Result> {
    public static final Executor THREAD_POOL_EXECUTOR = ModernAsyncTask.THREAD_POOL_EXECUTOR;
    public static final Executor SERIAL_EXECUTOR = Executors.newSingleThreadExecutor();
    private Executor executor;

    protected ConcurrentTask() {
        this(THREAD_POOL_EXECUTOR);
    }

    protected ConcurrentTask(Executor executor) {
        this.executor = executor;
    }

    public void exe(Params... params) {
        executeOnExecutor(executor, params);
    }
}
