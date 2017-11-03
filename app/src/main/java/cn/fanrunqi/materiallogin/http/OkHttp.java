package cn.fanrunqi.materiallogin.http;



import org.junit.Test;

import java.io.IOException;
import java.util.List;

import cn.fanrunqi.materiallogin.javabeans.UserEntity;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 72408 on 2017/3/16.
 */

public class OkHttp {

    private static OkHttpClient client;

    static {
        client = new OkHttpClient();
    }

    public String sendGetRequest(String url, List<Object> objects, String apiKey) throws IOException {

        if (objects == null) {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        } else {
            return null;
        }
    }

    public String sendWordPostRequest(String url, String word) throws IOException {

        FormBody body = new FormBody.Builder().add("word", word).build();

        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public String registerRequest(String url , UserEntity userEntity) throws IOException {
        FormBody body = new FormBody.Builder().add("user", userEntity.getUser()).
                add("pwd",userEntity.getPwd()).build();

        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public String loginRequest(String url , UserEntity userEntity) throws IOException {
        FormBody body = new FormBody.Builder().add("user", userEntity.getUser()).
                add("pwd",userEntity.getPwd()).build();

        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }



    @Test
    public void test() throws IOException {
        String url = "http://127.0.0.1:8080/login";
//        try {
//            DictEntity dictEntity = new Gson().fromJson(new OkHttp().sendWordPostRequest(url,"take") ,DictEntity.class) ;
//
//            System.out.println(dictEntity.getMeaning());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println(registerRequest(url , new UserEntity(0 , "sd000" , "232")));

    }


}










