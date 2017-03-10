package com.htlconline.sm.classmate.chat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htlconline.sm.classmate.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MessagesListAdapter extends BaseAdapter {

	private Context context;
	private List<Message> messagesItems;
private int previousIndex = -1;
	private String prevoiusUserId="";
	private String previousDate = "";
	public MessagesListAdapter(Context context, List<Message> navDrawerItems) {
		this.context = context;
		this.messagesItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return messagesItems.size();
	}

	@Override
	public Object getItem(int position) {
		return messagesItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}





	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Message m = messagesItems.get(position);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (!messagesItems.get(position).isSelf()) {
			convertView = mInflater.inflate(R.layout.chat_user_from_item,null);
		} else {
			convertView = mInflater.inflate(R.layout.chat_user_you_item,null);

		}
		TextView txtMsg = (TextView) convertView.findViewById(R.id.textview_message);
		TextView txtTime = (TextView) convertView.findViewById(R.id.textview_time);
		TextView lblFrom = (TextView) convertView.findViewById(R.id.textview_sender);
		TextView txtViewDate = (TextView) convertView.findViewById(R.id.textview_date);
		LinearLayout layoutDate = (LinearLayout)convertView.findViewById(R.id.layoutDate);



		int previousindex = position-1;
		if(previousindex>=0){
			previousDate = changeDateFormat(messagesItems.get(previousindex).getTime());
			if(previousDate.trim().equalsIgnoreCase(changeDateFormat(m.getTime()).trim())){
				layoutDate.setVisibility(LinearLayout.GONE);
			}else{
				layoutDate.setVisibility(LinearLayout.VISIBLE);
			}
		}







		txtMsg.setText(Html.fromHtml(m.getTmessage()
				+ " &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;" +
				"&#160;&#160;&#160;&#160;&#160;&#160;&#160;"));

		txtViewDate.setText(changeDateFormat(m.getTime()));
		lblFrom.setText(m.getFromName());
		txtTime.setText(changeTimeFormat(m.getTime()));
		if (!m.isSelf()){
			int previousIndex = position-1;
			if(previousIndex>=0)
				prevoiusUserId = messagesItems.get(previousIndex).getSenderId();
			else
				prevoiusUserId = "";
			if(prevoiusUserId.equalsIgnoreCase(m.getSenderId()) && previousDate.trim().equalsIgnoreCase(changeDateFormat(m.getTime()).trim())){
				lblFrom.setVisibility(TextView.GONE);
				LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.outgoing_layout_Tittle);
				linearLayout.setBackgroundResource(R.drawable.balloon_incoming_normal_ext);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				layoutParams.setMargins((int)convertDpToPixel(context,8.0f),0,(int)convertDpToPixel(context,60.0f),0);
				linearLayout.setLayoutParams(layoutParams);
			}else {
				lblFrom.setVisibility(TextView.VISIBLE);
				String colorCodee = intToARGB(m.getFromName().hashCode());
				int size = colorCodee.length();
				if(size<6){
					int remainsize = 6-size;
					for(int i=0;i<remainsize;i++){
						colorCodee = "0"+colorCodee;
					}
				}else if(size>6){
					colorCodee = colorCodee.substring(0,6);
				}
				colorCodee = "#"+colorCodee;
				lblFrom.setTextColor(Color.parseColor(colorCodee));
			}

		}else {
			int previousIndex = position - 1;
			if (previousIndex >= 0) {
				prevoiusUserId = messagesItems.get(previousIndex).getSenderId();
			}else{
				prevoiusUserId = "";
			}
			if (prevoiusUserId.equalsIgnoreCase(m.getSenderId()) && previousDate.trim().equalsIgnoreCase(changeDateFormat(m.getTime()).trim())) {
				lblFrom.setVisibility(TextView.GONE);
				LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.outgoing_layout_Tittle);
				linearLayout.setBackgroundResource(R.drawable.balloon_outgoing_normal_ext);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				layoutParams.setMargins((int)convertDpToPixel(context,60.0f),0,(int)convertDpToPixel(context,8.0f),0);
				layoutParams.gravity = Gravity.RIGHT;
				linearLayout.setLayoutParams(layoutParams);
			}
		}

		return convertView;
	}
	private  float convertDpToPixel(Context context, float dp){
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
		return px;
	}

	private   String intToARGB(int i){
		return Integer.toHexString(((i>>24)&0xFF))+
				Integer.toHexString(((i>>16)&0xFF))+
				Integer.toHexString(((i>>8)&0xFF));

	}
	private String changeTimeFormat(String oldTime){
		SimpleDateFormat simpleDateFormatOld = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		SimpleDateFormat simpleDateFormatNew = new SimpleDateFormat("hh:mm a");
		try {
			return    simpleDateFormatNew.format(simpleDateFormatOld.parse(oldTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String changeDateFormat(String oldTime){
		SimpleDateFormat simpleDateFormatOld = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		SimpleDateFormat simpleDateFormatNew = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			return    simpleDateFormatNew.format(simpleDateFormatOld.parse(oldTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

}
