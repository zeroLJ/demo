package com.example.zero.sendposttest;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button m_butPost, m_butGet, m_butdownload, m_butcookie, m_butjson;
    private TextView tv;
    ImageView imageview;
    String s;
    Handler handler = new Handler();
    String action = "http://192.168.0.105/Mytest/post.php";
    String url = "http://192.168.0.105/Mytest/post.php";//发送请求的地址
    OkHttpClient client;

    //    HttpPost httpRequest=null;
//    List<NameValuePair> params=null;
//    HttpResponse httpResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_butPost = (Button) findViewById(R.id.butPost);
        tv = (TextView) findViewById(R.id.lblPostResult);
        m_butGet = (Button) findViewById(R.id.butGet);
        m_butdownload = (Button) findViewById(R.id.butdownload);
        imageview = (ImageView) findViewById(R.id.iv);
        m_butcookie = (Button) findViewById(R.id.butcookie);
        m_butjson = (Button) findViewById(R.id.butjson);

        client = new OkHttpClient.Builder().cookieJar(new CookiesManager()).build();
        m_butPost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = "http://192.168.0.105/Mytest/post.php";//发送请求的地址
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBodyBuild = new FormBody.Builder();
                formBodyBuild.add("name", "zero");//此处添加所需要的键值对
                /*RequestBody requestBody = RequestBody.create(MediaType.parse("text/plan;charset=utf-8)"), "{name:你好}");
                Request request = new Request.Builder().url(url)
                        .post(requestBody).build();//发送json数据，中文等时使用*/


//                JSONObject object=new JSONObject();
//                    try {
//                        object.put("name","zero");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                }
//                RequestBody requestBody=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),object.toString());
//                Request request = new Request.Builder().url(url)
//                        .post(requestBody).build();

//                MultipartBody.Builder body=new MultipartBody.Builder();
//                RequestBody requestBody= body.setType(MediaType.parse("multipart/form-data"))
//                        .addFormDataPart("name", "zero")
//
//                        .build();
//                Request request = new Request.Builder().url(url)
//                        .post(requestBody).build();

                Request request = new Request.Builder().url(url)
                        .post(formBodyBuild.build()).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText("error");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //tv.setText("success");
                                try {
                                    tv.setText(response.body().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        });
        m_butGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Request request = new Request.Builder()
                        .url("http://192.168.0.105/Mytest/post.php?name=zero").build();//get请求时键值对放在地址后
                OkHttpClient client = new OkHttpClient();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    /**
                     * 此时还在非UI线程中
                     * @param call
                     * @param response
                     * @throws IOException
                     */
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String res = response.body().string();
                        //InputStream inputStream = response.body().byteStream();获取输入流，传输大文件时使用
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText(res);
                            }
                        });
                    }
                });
            }
        });
        m_butdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Request request = new Request.Builder()
                        .url("http://pic17.nipic.com/20111122/6759425_152002413138_2.jpg").build();
                OkHttpClient client = new OkHttpClient();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    /**
                     * 此时还在非UI线程中
                     * @param call
                     * @param response
                     * @throws IOException
                     */
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        long length = response.body().contentLength();//获取文件总长度
                        InputStream inputStream = response.body().byteStream();
                        final BitmapDrawable d = (BitmapDrawable) BitmapDrawable.createFromStream(inputStream, "hehe.jpg");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageview.setBackground(d);
                            }
                        });
                    }
                });
            }
        });
        m_butcookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FormBody.Builder formBodyBuild = new FormBody.Builder();
                formBodyBuild.add("name", "zero");//此处添加所需要的键值对
                Request request = new Request.Builder().url(url)
                        .post(formBodyBuild.build()).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText("error");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //tv.setText("success");
                                try {
                                    tv.setText(response.body().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        });
        m_butjson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Request request = new Request.Builder()
                        .url("http://192.168.0.105/Mytest/test.php").build();//get请求时键值对放在地址后
                //OkHttpClient client = new OkHttpClient();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    /**
                     * 此时还在非UI线程中
                     * @param call
                     * @param response
                     * @throws IOException
                     */
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String res = response.body().string();
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(res);
                            //获取状态码，即图上的 200
                            final int a = jsonObject.getInt("code");
                            //获取消息体，即图上的 转换json成功
                            final String message = jsonObject.getString("message");
                            //获取数据data，即图上$arr数组的json格式数据
                            final JSONObject data = jsonObject.getJSONObject("data");
                            //获取$arr名为id的值
                            final int a2 = data.getInt("id");
                            //获取$arr名为type的数组
                            JSONArray type = data.getJSONArray("type");
                            //获取$arr名为type的数组的第一位数据
                            final int a3 = type.getInt(0);
                            //获取$arr名为test的数组的json格式数据
                            JSONObject test = data.getJSONObject("test");
                            //获取$arr名为test的数组的json格式数据中名为“0”的值
                            final int a4 = test.getInt("0");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "" + a + " " + message + " " + a2 + " " + a3 + " " + a4, Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //InputStream inputStream = response.body().byteStream();获取输入流，传输大文件时使用
                    }
                });
            }
        });

    }

    public void ifstorecookie(View view) {

    }

    private class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(getApplicationContext());
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }

        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            final List<Cookie> cookies = cookieStore.get(url);
            if(cookies.size()>0){
                final int a=cookies.size();
                final Cookie cookie=cookies.get(0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,""+a,Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this,""+cookie,Toast.LENGTH_LONG).show();
                    }
                });
                //System.out.println("没加载到cookie");
            }
            return cookies;
        }

    }

}
