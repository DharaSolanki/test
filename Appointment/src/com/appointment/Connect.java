package com.appointment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connect {

ConnectivityManager connManager;
NetworkInfo mWifi;
boolean isWifi;
Context context;
public Connect(Context context)
{
	this.context=context;
	}

public boolean ConnectWithWifi()
{
	connManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	mWifi=connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	if(mWifi.isConnected())
	{
		return true;
	}
	else
	{
		return false;
	}
	
}
public boolean isWifiConnected()
{
	if(mWifi.isConnected())
	{
		return true;
	}
	else
	{
		return false;
	}
	
}
public boolean isWifiAvailable()
{
	if(mWifi.isAvailable())
	{
		return true;
	}
	else
	{
		return false;
	}
	
}
}
