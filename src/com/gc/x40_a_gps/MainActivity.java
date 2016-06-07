package com.gc.x40_a_gps;

import java.util.List;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView tv_location;
	private LocationManager locationManager;
	private MyLocationListener myLocationListener;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tv_location = (TextView)findViewById(R.id.tv_location);
        
        //获取系统定位服务
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        //获取所有位置提供者
//      List<String> allProviders = locationManager.getAllProviders();
//      System.out.println(allProviders);
        
        //最佳位置提供者的"标准"
        Criteria criteria = new Criteria();
        //是否允许付费定位（例如3g，4g定位）
        criteria.setCostAllowed(true);
        //精确度（良好）
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        
        //获取最佳  位置提供者（参数：标准，是否可用）
        String bestProvider = locationManager.getBestProvider(criteria, true);
        
        
        myLocationListener = new MyLocationListener();
        //获取定位更新(位置提供者，最小更新时间，最小更新距离，监听)
        locationManager.requestLocationUpdates(bestProvider, 0, 0, myLocationListener);
//      locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
        
    }

    class MyLocationListener implements LocationListener{

    	//位置变化时回调
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			String longitude = "经度 ： " + location.getLongitude() + "度";
			String latitude = "纬度 ： " + location.getLatitude() + "度";
			String accuracy = "精度 ：" + location.getAccuracy() + "米";
			String altitude = "海拔 ： " + location.getAltitude() + "米";
			
			tv_location.setText(longitude + "\n" + latitude + "\n" + altitude + "\n" + accuracy);
		}

		//位置提供者状态发生变化时回调
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			System.out.println("onStatusChanged");
		}

		//位置提供者可用时回调（开启时）
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			System.out.println("onProviderEnabled");
		}

		//位置提供者不可用时回调（关闭时）
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			System.out.println("onProviderDisabled");
		}
    	
    }

    /**
     * activity销毁时调用，关闭GPS定位服务监听
     */
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	//关闭GPS定位服务
    	locationManager.removeUpdates(myLocationListener);
    }
}
