package com.htlconline.sm.classmate.chat;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.codebutler.android_websockets.WebSocketClient;
import com.google.gson.Gson;
import com.htlconline.sm.classmate.R;

import org.json.JSONObject;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChatNewFragment extends Fragment  {
	// LogCat tag
	private static final String TAG = "StudyMate";

	private FloatingActionButton btnSend;
	private EditText inputMsg;


	// Chat messages list adapter
	private MessagesListAdapter adapter;
	private List<Message> listMessages;
	private ListView listViewMessages;


	// Client userId
	private String userId, groupId, senderName;
	public static final String ACTION_POST = "post";
	public static final String ACTION_CONNECT = "connect";
	public static final String ACTION_STATUS_UPDATE = "status_update";
	public static final String ACTION_BIND = "bind";
Handler handler;

	private WebSocketClient client;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chat, null);
		initView(view);
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
		);
		return view;
	}

	private void initView(View view) {
		handler = new Handler();
		btnSend = (FloatingActionButton) view.findViewById(R.id.btnSend);
		inputMsg = (EditText) view.findViewById(R.id.inputMsg);
		listViewMessages = (ListView) view.findViewById(R.id.list_view_messages);
		// Getting the person userId from previous screen
		Bundle bundle = getArguments();
		if(bundle != null){
			userId = bundle.getString("userId");
			groupId = bundle.getString("groupId");
			senderName = bundle.getString("senderName");
		}


		btnSend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Sending message to web socket server
				sendMessageToServer(createItem(ACTION_POST, inputMsg.getText().toString()));
				// Clearing the input filed once message was sent
				inputMsg.setText("");
			}
		});


		listMessages = new ArrayList<Message>();

		adapter = new MessagesListAdapter(getActivity(), listMessages);
		listViewMessages.setAdapter(adapter);
		createWebsocketConnection();

	}



	private void createWebsocketConnection(){
		client = new WebSocketClient(URI.create(getString(R.string.socket_host)), new WebSocketClient.Listener() {
			@Override
			public void onConnect() {
				Log.d(TAG, " ------Web socket onConnect------");
				handler.postDelayed(runnable,500);

			}

			@Override
			public void onMessage(String message) {
				Log.d(TAG,"On Message ---Incoming----->"+message);
				Message message1 = new Gson().fromJson(message, Message.class);
				parseMessage(message1);

			}

			@Override
			public void onMessage(byte[] data) {
				Log.d(TAG, String.format("Got binary message! %s",bytesToHex(data)));
				// Message will be in JSON format
//				parseMessage(bytesToHex(data));
			}

			@Override
			public void onDisconnect(int code, String reason) {

				String message = String.format(Locale.US,"Disconnected! Code: %d Reason: %s", code, reason);
				Log.e(TAG, "onDisconnect! : " + message);
			}

			@Override
			public void onError(Exception error) {
				Log.e(TAG, "onError! : " + error);
			}

		}, null);
		if(!client.isConnected())
			client.connect();

	}


	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			sendMessageToServer(createItem(ACTION_BIND, "Bind"));
		}
	};


	@Override
	public void onDetach() {
		super.onDetach();
		if(client != null & client.isConnected()){
			client.disconnect();
			client = null;
		}
	}

	private void sendMessageToServer(String message) {
		Log.d(TAG, " ------sendMessageToServer------"+message);
		Log.d(TAG, " ------sendMessageToServer------"+client.isConnected());
		if (client != null && client.isConnected()) {
			Log.d(TAG, " ------sendMessageToServer------"+client.isConnected());
			client.send(message);
		}
	}

	/**
	 * Appending message to list view
	 */
	private void appendMessage(final Message m) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				listMessages.add(m);
				adapter.notifyDataSetChanged();

//				playBeep();
			}
		});
	}



	/**
	 * Plays device's default notification sound
	 */
	public void playBeep() {

		try {
			Uri notification = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(getActivity(),
					notification);
			r.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}



	private String createItem(String action,String message){
		JSONObject jsonObject = new JSONObject();
		try{
			jsonObject.put("action",action);
			jsonObject.put("tuserId",groupId);
			jsonObject.put("sender",senderName);
			jsonObject.put("senderId", userId);
			jsonObject.put("tmessage", message);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
			jsonObject.put("time", simpleDateFormat.format(Calendar.getInstance().getTime()));

		}catch (Exception e){
			System.out.println("----Exception--"+e);
		}
		return jsonObject.toString();
	}







	public void parseMessage(Message response) {
		if (ACTION_POST.equals(response.getAction())) {
			if(response.getTuserId().trim().equalsIgnoreCase(groupId)) {
				if (response.getSenderId().equalsIgnoreCase(userId)) {
					response.setIsSelf(true);
				} else {
					response.setIsSelf(false);
				}
				response.setFromName(response.getSender());
				appendMessage(response);
			}else{
				sendNotification(response);
			}
		} else if (ACTION_BIND.equals(response.getAction())) {
			sendMessageToServer(createItem(ACTION_CONNECT,"Connect"));
		} else if (ACTION_CONNECT.equals(response.getAction())) {

		} else if (ACTION_STATUS_UPDATE.equals(response.getAction())) {

		}
	}


		private void sendNotification(Message response){
		NotificationCompat.Builder notBuilder = new NotificationCompat.Builder(getActivity());
		notBuilder.setContentTitle(response.getSender());
		notBuilder.setContentText(response.getTmessage());
		Intent intent =  new Intent(getActivity(), ChatActivity.class);
			intent.putExtra("userId", userId);
			intent.putExtra("groupId", response.getTuserId());
			intent.putExtra("senderName", senderName);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notBuilder.setContentIntent(pi);
		notBuilder.setSmallIcon(android.R.drawable.ic_notification_overlay);
		notBuilder.setSound(RingtoneManager.getActualDefaultRingtoneUri(getActivity(), RingtoneManager.TYPE_NOTIFICATION));
		notBuilder.setOnlyAlertOnce(false);
		notBuilder.setAutoCancel(true);
		NotificationCompat.BigTextStyle bst = new NotificationCompat.BigTextStyle(notBuilder);
		bst.bigText(response.getTmessage());
		NotificationManager nm = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
		nm.notify(0, bst.build());
	}
}
