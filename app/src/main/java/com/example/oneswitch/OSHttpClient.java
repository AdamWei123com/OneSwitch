package com.example.oneswitch;
import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OSHttpClient
{
	String Context=null;
	private List<String> cookieList=null;
	 String COOKIE=null;
	 String COOKIES=null;
	 
	File file=new File("/storage/emulated/0/文件.text");
	private OkHttpClient clien;
	/*
	 带单表
	 cookie获取
	 完成
	 */
	public String post(String url){
		
		clien=new OkHttpClient();
		
		RequestBody form=new FormBody
			.Builder()
			.add("name","Adam")
			.add("password","Adam972663233")

			.build();
		Request request=new Request.Builder()
			.url(url)
			.post(form)
			.build();
		clien.newCall(request).enqueue(new Callback(){

				@Override
				public void onFailure(Call p1, IOException p2)
				{
					// TODO: Implement this method
				}

				@Override
				public void onResponse(Call p1, Response p2) throws IOException
				{
					// TODO: Implement this method
					if(null!=p2.body().string()){
						Context=p2.body().string();
						cookieList=p2.headers("Set-Cookie");
						if(cookieList != null) {
							COOKIE=cookieList.toString();
							COOKIE = cookieList.get(0).substring(0, cookieList.get(0).indexOf(";"));
							COOKIES = cookieList.get(1).substring(0, cookieList.get(1).indexOf(";"));
			}
			}
				}
			});
			return Context;
}
	/*
	 带单表
	 带cookie
	 实现单个及多个文件上传
	 逐个计算进度
	 完成
	 */
	public String posting(String url){
		
		MultipartBody.Builder form=new MultipartBody.Builder().setType(MultipartBody.FORM);


		clien = new OkHttpClient();
		
		for(int i=1;i<=5;i++){
			form.addFormDataPart("file" + i, file.getName(), new ProgressRequestBody(MultipartBody.FORM,file, new httpContextListener(){

										 @Override
										 public void update(long bytesRead, long contentLength, boolean done)
										 {
											 // TODO: Implement this method
											 System.out.print((bytesRead - contentLength) * 100 / bytesRead + "%");
											 System.out.println("boolean"+done);
										 }

										 @Override
										 public void Download(long bytesRead, long contentLength, boolean done)
										 {
											 // TODO: Implement this method
										 }
									 }));
			form.addFormDataPart("ftext"+i,file.getName());
		}
		
			RequestBody requestBody=form.addFormDataPart("head","tyyhhggyy")
			.addFormDataPart("txt","fghbvvcfffffn")
			.addFormDataPart("bid","3")
			.build();
		Request postre=new Request.Builder()
			.addHeader("cookie",COOKIE)
			.addHeader("cookie",COOKIES)
			.url(url)
			.post(requestBody)
			.build();
		
		clien.newCall(postre).enqueue(new Callback(){

				@Override
				public void onFailure(Call p1, IOException p2)
				{
					// TODO: Implement this method
				}

				@Override
				public void onResponse(Call p1, Response p2) throws IOException
				{
					// TODO: Implement this method
					if(null!=p2){
						//Context=p2.body().string();
					}
				}
			});
			return Context;
	}
	/*
	下载文件带cookie
	计算进度
	完成
	*/
	public void Download(String url) throws IOException{
		Request.Builder builder= new Request.Builder()
		.addHeader("cookie",COOKIE)
		.addHeader("cookie",COOKIES);
		for(int i=1;i<=4;i++){
		builder.url("http://adam.hostcoz.com/zone/down.php?d=dir&n=985714577929228160.text");
		}
		Request request=builder.build();
		final httpContextListener progressListener = new httpContextListener() {

			@Override
			public void Download(long bytesRead, long contentLength, boolean done)
			{
				// TODO: Implement this method
				System.out.println(bytesRead);
				System.out.println(contentLength);
				System.out.println(done);
				System.out.println("%d%% done\n"+(100 * bytesRead) / contentLength);
				
			}

			@Override public void update(long bytesRead, long contentLength, boolean done) {
				
			}
		};

		 clien = new OkHttpClient.Builder()
			.addNetworkInterceptor(new Interceptor() {
				@Override public Response intercept(Chain chain) throws IOException {
					Response originalResponse = chain.proceed(chain.request());
					return originalResponse.newBuilder()
						.body(new ProgressResponseBody(originalResponse.body(), progressListener))
						.build();
				}
			})
			.build();

		Response response = clien.newCall(request).execute();
		if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

		System.out.println(response.body().string());
	}
}
