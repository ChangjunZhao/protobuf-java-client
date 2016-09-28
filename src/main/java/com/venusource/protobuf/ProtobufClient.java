package com.venusource.protobuf;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.venusource.protobuf.ProtobufUserOuterClass.ProtobufUser;
import com.venusource.protobuf.ProtobufUserOuterClass.ProtobufUser.Phone;

public class ProtobufClient {
	public static void main(String args[]){
		CloseableHttpClient httpclient = HttpClients.createDefault(); 
		HttpGet httpget = new HttpGet("http://localhost:8080/protoc");
		CloseableHttpResponse response = null;
        // 执行get请求.    
        try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			ProtobufUser user = ProtobufUser.parseFrom(EntityUtils.toByteArray(entity));
			System.out.printf("user: id=%d, name=%s \n",user.getId(), user.getName());
			for(Phone phone:user.getPhonesList()){
				System.out.printf("%s:%s\n", phone.getPhoneType(), phone.getPhoneNumber());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
