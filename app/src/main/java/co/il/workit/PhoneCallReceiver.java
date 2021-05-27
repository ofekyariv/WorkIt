package co.il.workit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class PhoneCallReceiver extends BroadcastReceiver {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    interface ICallMessageReceiver {
        void sendCallMessage(String socketMessage);
    }
    //Also declare the interface in your BroadcastReceiver as static
    private static ICallMessageReceiver iCallMessageReceiver;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                iCallMessageReceiver.sendCallMessage(incomingNumber);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerCallback(ICallMessageReceiver iCallMessageReceiver) {
        PhoneCallReceiver.iCallMessageReceiver = iCallMessageReceiver;
    }


}



