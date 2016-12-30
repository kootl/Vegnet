package com.sunstar.vegnet.kootl.comm.okhttp;

import android.util.Log;

import com.sunstar.vegnet.kootl.comm.tool.ThreadTool;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by louisgeek on 2016/12/6.
 */

public class OkHttpSingleton {

    /**
     * ====================================================
     */
    private static volatile OkHttpSingleton mInstance;

    /* 私有构造方法，防止被实例化 */
    private OkHttpSingleton() {
        /*
       OkHTTP 3  不能用  后期赋值不了 也添加不了拦截器
       mOkHttpClient.newBuilder().connectTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.newBuilder().readTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.newBuilder().writeTimeout(30, TimeUnit.SECONDS);*/

        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpSingleton getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpSingleton.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpSingleton();
                }
            }
        }
        return mInstance;
    }

    /**
     * ==========================================
     */
    private static final String TAG = "OkHttpSingleton";
    //-99,  -100   自己定义的。。。
    public static final int OKHTTP_CALL_CANCEL = -50;
    public static final int OKHTTP_CALL_ERROR = -100;
    /**
     * "application/x-www-form-urlencoded"，他是默认的MIME内容编码类型，一般可以用于所有的情况。但是他在传输比较大的二进制或者文本数据时效率极低。
     * 这种情况应该使用"multipart/form-data"。如上传文件或者二进制数据和非ASCII数据。
     */
    public static final MediaType MEDIA_TYPE_NORAML_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
    //既可以提交普通键值对，也可以提交(多个)文件键值对。
    public static final MediaType MEDIA_TYPE_MULTIPART_FORM = MediaType.parse("multipart/form-data;charset=utf-8");
    //只能提交二进制，而且只能提交一个二进制，如果提交文件的话，只能提交一个文件,后台接收参数只能有一个，而且只能是流（或者字节数组）
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");
    public static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    private OkHttpClient mOkHttpClient;

    /**
     * Posting form parameters FormBody （okhttp3 新增）  中文有问题  暂时别用
     */
    @Deprecated
    public void doPostOld(String url, Map<String, String> headersMap, Map<String, String> paramsMap, Callback responseCallback) {
        Log.d(TAG, "doPostAsyncOld: url:" + url);
        StringBuilder stringBuilder = new StringBuilder("params:");
        for (String key : paramsMap.keySet()) {
            String value = paramsMap.get(key);
            stringBuilder.append(key);
            stringBuilder.append("=");
            stringBuilder.append(value);
            stringBuilder.append("&");
            //  Log.d(TAG, "doPostAsync: key:"+key+",value:"+paramsMap.get(key));
        }
        Log.d(TAG, "doPostAsyncOld:" + stringBuilder.toString());
        //表单数据
       /* RequestBody formBody = new FormBody.Builder()
                .add("ID", "1")
                .add("ID2", "2")
                .build();*/
        FormBody formBody = null;

        if (paramsMap != null) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                // System.out.println("key= "+ key + " and value= " + paramsMap.get(key));
                String value = paramsMap.get(key);
             /*   try {
                    key= URLEncoder.encode(key,DEFAULT_PARAMS_ENCODING);
                    value=URLEncoder.encode(value,DEFAULT_PARAMS_ENCODING);
                    Log.d(TAG, "doPostAsync: encode key:"+key);
                    Log.d(TAG, "doPostAsync: encode value:"+value);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e(TAG, "doPostAsync: encode UnsupportedEncodingException");
                }*/
                //###   formBodyBuilder.add(key,value);
                formBodyBuilder.addEncoded(key, value);
                formBodyBuilder.add(key, value);

            }
            formBody = formBodyBuilder.build();
        }
      /*  Request request=new Request.Builder()
                .url("http://api.lvseeds.com:8080/lvseeds/Melonplant/GetOther")
                .headers(Headers.of(getHeaders()))
                .post(formBody)
                .build();*/
        //
        Request request;
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);

        //默认需要加的验证
        ////#### requestBuilder.headers(Headers.of(setupDefaultHeaders()));

        if (headersMap != null) {
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (formBody != null) {
            requestBuilder.post(formBody);
        }
        request = requestBuilder.build();

        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }


    public void doPost(String url, Map<String, String> defaultHeadersMap, Map<String, String> customHeadersMap, String paramsStr, Callback responseCallback) {
        //创建一个请求对象
        Request request;
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);

        //
        if (defaultHeadersMap != null&&defaultHeadersMap.size()>0) {
            //默认需要加的验证
            requestBuilder.headers(Headers.of(defaultHeadersMap));
        }
        if (customHeadersMap != null&&customHeadersMap.size()>0) {
            for (Map.Entry<String, String> entry : customHeadersMap.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (paramsStr != null) {
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_NORAML_FORM, paramsStr);
            if (requestBody != null) {
                requestBuilder.post(requestBody);
            }
        }
        //
        request = requestBuilder.build();
        //
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    private void doPostString(String url, Map<String, String> defaultHeadersMap, Map<String, String> customHeadersMap, String strContent, Callback responseCallback) {
        //创建一个请求对象
        Request request;
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);

        //
        if (defaultHeadersMap != null&&defaultHeadersMap.size()>0) {
            //默认需要加的验证
            requestBuilder.headers(Headers.of(defaultHeadersMap));
        }
        if (customHeadersMap != null&&customHeadersMap.size()>0) {
            for (Map.Entry<String, String> entry : customHeadersMap.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (strContent != null) {
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_TEXT, strContent);
            if (requestBody != null) {
                requestBuilder.post(requestBody);
            }
        }
        //
        request = requestBuilder.build();
        //
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    private void doPostJsonStr(String url, Map<String, String> defaultHeadersMap, Map<String, String> customHeadersMap, String jsonStr, Callback responseCallback) {
        //创建一个请求对象
        Request request;
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);

        //
        if (defaultHeadersMap != null&&defaultHeadersMap.size()>0) {
            //默认需要加的验证
            requestBuilder.headers(Headers.of(defaultHeadersMap));
        }
        if (customHeadersMap != null&&customHeadersMap.size()>0) {
            for (Map.Entry<String, String> entry : customHeadersMap.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (jsonStr != null) {
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, jsonStr);
            if (requestBody != null) {
                requestBuilder.post(requestBody);
            }
        }
        //
        request = requestBuilder.build();
        //
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    public void doGet(String url, Map<String, String> defaultHeadersMap, Map<String, String> customHeadersMap, Callback responseCallback) {
      /*  Request request=new Request.Builder()
                .url("http://api.lvseeds.com:8080/lvseeds/Melonplant/GetOther")
                .headers(Headers.of(getHeaders()))
                .post(formBody)
                .build();*/
        //
        Request request;
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        if (defaultHeadersMap != null&&defaultHeadersMap.size()>0) {
            //默认需要加的验证
            requestBuilder.headers(Headers.of(defaultHeadersMap));
        }
        if (customHeadersMap != null&&customHeadersMap.size()>0) {
            for (Map.Entry<String, String> entry : customHeadersMap.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        request = requestBuilder.build();
        //
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 下载文件
     */
    public void doGetUrlDownloadFile(String url, Callback responseCallback) {

        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 下载文件带进度   测试通过  进度OK
     *
     * @param url
     */
    public void doGetAsyncFileWithProgress(String url, final OnDownloadProgressListener onDownloadProgressListener, Callback responseCallback) {

      /*  final OnDownloadProgressListener onDownloadProgressListener=new OnDownloadProgressListener() {
            @Override
            public void onDownloadloadProgress(long bytesRead, long contentLength, boolean done) {
                int progress = (int) (100.0 * bytesRead / contentLength);
                Log.d(TAG, "onDownloadloadProgress: progress:"+progress);
            }
        };*/
        //增加拦截器  okhttp 3 注意  不清楚可以直接参照http://www.it1352.com/138888.html 创建一个OkHttpClient
        OkHttpClient.Builder okHttpClientBuilder = mOkHttpClient.newBuilder();
        okHttpClientBuilder.addInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                                /*Request request = chain.request().newBuilder()
                                        .addHeader("Accept", "Application/JSON").build();
                                return chain.proceed(request);*/
                        //拦截
                        Response originalResponse = chain.proceed(chain.request());
                        //包装响应体并返回
                        return originalResponse.newBuilder()
                                .body(new DownloadProgressResponseBody(originalResponse.body(), onDownloadProgressListener))
                                .build();
                    }
                }).build();

      /* okhttp 3.0不行  okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());
                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(new DownloadProgressResponseBody(originalResponse.body(),onDownloadProgressListener))
                        .build();
            }
        });*/

        mOkHttpClient = okHttpClientBuilder.build();
        Log.d(TAG, "mOkHttpClient: connectTimeoutMillis:" + mOkHttpClient.connectTimeoutMillis());
        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }


    //上传的  自定义的RequestBody，能够显示进度
    private static class UploadProgressRequestBody extends RequestBody {
        //实际的待包装请求体
        private final RequestBody requestBody;
        //进度回调接口
        private final OnUploadProgressListener onUploadProgressListener;
        //包装完成的BufferedSink
        private BufferedSink bufferedSink;

        /**
         * 构造函数，赋值
         *
         * @param requestBody              待包装的请求体
         * @param onUploadProgressListener 回调接口
         */
        public UploadProgressRequestBody(RequestBody requestBody, OnUploadProgressListener onUploadProgressListener) {
            this.requestBody = requestBody;
            this.onUploadProgressListener = onUploadProgressListener;
        }

        /**
         * 重写调用实际的响应体的contentType
         *
         * @return MediaType
         */
        @Override
        public MediaType contentType() {
            return requestBody.contentType();
        }

        /**
         * 重写调用实际的响应体的contentLength
         *
         * @return contentLength
         * @throws IOException 异常
         */
        @Override
        public long contentLength() throws IOException {
            return requestBody.contentLength();
        }

        /**
         * 重写进行写入
         *
         * @param sink BufferedSink
         * @throws IOException 异常
         */
        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            if (bufferedSink == null) {
                //包装
                bufferedSink = Okio.buffer(sink(sink));
            }
            //写入
            requestBody.writeTo(bufferedSink);
            //必须调用flush，否则最后一部分数据可能不会被写入
            bufferedSink.flush();
        }

        /**
         * 写入，回调进度接口
         *
         * @param sink Sink
         * @return Sink
         */
        private Sink sink(Sink sink) {
            return new ForwardingSink(sink) {
                //当前写入字节数
                long bytesWritten = 0L;
                //总字节长度，避免多次调用contentLength()方法
                long contentLength = 0L;

                @Override
                public void write(Buffer source, long byteCount) throws IOException {
                    super.write(source, byteCount);
                    if (contentLength == 0) {
                        //获得contentLength的值，后续不再调用
                        contentLength = contentLength();
                    }
                    //增加当前写入的字节数
                    bytesWritten += byteCount;
                    //回调
                    onUploadProgressListener.onUploadProgress(bytesWritten, contentLength, bytesWritten == contentLength);
                }
            };
        }
    }

    //下载的  自定义的ResponseBody，在其中处理进度
    private static class DownloadProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final OnDownloadProgressListener onDownloadProgressListener;
        private BufferedSource bufferedSource;

        public DownloadProgressResponseBody(ResponseBody responseBody, OnDownloadProgressListener onDownloadProgressListener) {
            this.responseBody = responseBody;
            this.onDownloadProgressListener = onDownloadProgressListener;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;
                int progress;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    long contentLength = responseBody.contentLength();//不要调用多次  好像会出错
                    onDownloadProgressListener.onDownloadloadProgress(totalBytesRead, contentLength, bytesRead == -1);

                    progress = (int) (totalBytesRead * 100.0 / contentLength);
                    //Log.d(TAG, "read: progress:"+progress);
                    ThreadTool.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onDownloadProgressListener.onDownloadloadProgressNotifyUI(progress);
                        }
                    });

                    return bytesRead;
                }
            };
        }
    }


    //上传进度回调接口
    public interface OnUploadProgressListener {
        void onUploadProgress(long bytesRead, long contentLength, boolean done);
        //void onUploadProgressNotifyUI(int progress);
    }

    //下载进度回调接口
    public interface OnDownloadProgressListener {
        void onDownloadloadProgress(long bytesRead, long contentLength, boolean done);

        void onDownloadloadProgressNotifyUI(int progress);
    }
}
