package com.boco.app.core.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NetworkUtils
{
	private final static String TAG = "NetworkUtils";

	public static boolean isNetworkUp(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return info != null && info.isAvailable();
	}

	private static String changeInputStream(InputStream inputStream, String encode)
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len;
		String result = "";
		if (inputStream != null)
		{
			try
			{
				while ((len = inputStream.read(data)) != -1)
				{
					outputStream.write(data, 0, len);
				}
				String str = new String(outputStream.toByteArray());
                result = new String(str.getBytes(),"UTF-8");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}



}