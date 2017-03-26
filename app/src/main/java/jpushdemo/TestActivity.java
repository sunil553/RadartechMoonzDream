package jpushdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.radartech.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

import static android.R.attr.key;

public class TestActivity extends Activity {


    private String alarmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("User-defined open Activity");
        Intent intent = getIntent();
        if (null != intent) {

//Bundle[{cn.jpush.android.NOTIFICATION_TYPE=0, app=com.radartech.sw,
// cn.jpush.android.ALERT=Geo-fence in alarm! target:.Benz_Ver3,2016-12-16 13:46:04(GMT+5:30),
// cn.jpush.android.EXTRA={"type":1,"id":2605,"alarmid":2605},
// cn.jpush.android.NOTIFICATION_ID=221091030,
// cn.jpush.android.NOTIFICATION_CONTENT_TITLE=RadarTech,
// cn.jpush.android.MSG_ID=221091030}]
            Bundle bundle = getIntent().getExtras();
            Utility.showLogMessage("bundle data",bundle.toString());
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
//            String alarmId = bundle.getString(JPushInterface.EXTRA_EXTRA);
            try {
                JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                 alarmId = json.optString("alarmid");

            } catch (JSONException e) {
            }
            tv.setText("Title : " + title + "  " + "AlarmId : " + alarmId);
        }
        addContentView(tv, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    }

}
