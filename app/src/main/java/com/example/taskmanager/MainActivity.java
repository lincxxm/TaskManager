package com.example.taskmanager;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    Context mContext;
    List<UsageStats> usageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        usageList = getUsageStatsList(mContext);

        ListView listView = (ListView) findViewById(R.id.listView);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for (UsageStats usage : usageList) {
            Map<String, String> datum = new HashMap<String, String>();
            datum.put("PackageName", usage.getPackageName());
            datum.put("FirstTimeStamp", String.valueOf(usage.getFirstTimeStamp()));
            datum.put("LastTimeStamp", String.valueOf(usage.getLastTimeStamp()));
            datum.put("LastTimeUsed", String.valueOf(usage.getLastTimeUsed()));
            datum.put("TotalTimeInForeground", String.valueOf(usage.getTotalTimeInForeground()));

            System.out.println(usage.getFirstTimeStamp());
            System.out.println(usage.getLastTimeStamp());
            System.out.println(usage.getLastTimeUsed());
            System.out.println(usage.getPackageName());
            System.out.println(usage.getTotalTimeInForeground());

            data.add(datum);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.list_view_item,
                new String[]{"PackageName", "FirstTimeStamp", "LastTimeStamp", "LastTimeUsed", "TotalTimeInForeground"},
                new int[]{R.id.PackageName, R.id.FirstTimeStamp, R.id.LastTimeStamp, R.id.LastTimeUsed, R.id.TotalTimeInForeground});

        listView.setAdapter(adapter);
    }

    public List<UsageStats> getUsageStatsList(Context context) {
        DateFormat dateFormat = new SimpleDateFormat();
        UsageStatsManager usm = getUsageStatsManager(context);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();
        Log.d(TAG, "Range start:" + dateFormat.format(startTime));
        Log.d(TAG, "Range end:" + dateFormat.format(endTime));
        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
        return usageStatsList;
    }


    @SuppressWarnings("ResourceType")
    private UsageStatsManager getUsageStatsManager(Context context) {
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        return usm;
    }


}
