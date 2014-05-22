package com.appointment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class GetData {
	
	String strResult;
	
	public GetData()
	{
		
	}
	
	public String verifyLogin(String url,String uname,String Password)
	{
		DefaultHttpClient defaultClient = new DefaultHttpClient();
		HttpPost  httpPost = new HttpPost(url);
		try {
			HttpResponse httpRespone = defaultClient.execute(httpPost);
			HttpEntity httpEntity = httpRespone.getEntity();
			if(httpEntity != null)
			{
				InputStream ipStream = httpEntity.getContent();
				strResult = convertToString(ipStream);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return strResult;
		
	}

	public String convertToString(InputStream iStream) throws IOException
	{
		if(iStream!=null)
		{
			StringBuilder sb= new StringBuilder();
			String line;
			try
			{
				BufferedReader reader =new BufferedReader(new InputStreamReader(iStream,"UTF-8"));
				while((line=reader.readLine())!=null)
				{
					sb.append(line).append("\n");
				}
			}
			finally{
				iStream.close();
			}
			return sb.toString();
			
		}
		else {
			return "";
		}
		
	}
}
