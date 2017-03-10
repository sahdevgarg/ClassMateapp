package com.htlconline.sm.classmate.chat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.htlconline.sm.classmate.R;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    ContactListPojo contactListPojo;
    List<Tmessage> tmessagesList = new ArrayList<Tmessage>();
    ListView listView;
    String userId, name;
    private String groupOrPersonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Chat");

        if (getIntent().getExtras() != null) {
            userId = getIntent().getExtras().getString("userId");
            name = getIntent().getExtras().getString("name");
        }

        listView = (ListView) findViewById(R.id.listViewContact);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactListActivity.this, ChatActivity.class);
                intent.putExtra("userId", userId);
                Tmessage tmessage = tmessagesList.get(position);
                groupOrPersonName = getDisplayName(tmessage);
                intent.putExtra("groupId", tmessage.getGid());
                intent.putExtra("senderName", name);
                intent.putExtra("receiverName", groupOrPersonName);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }


    @Override
    protected void onResume() {
        super.onResume();
        new MyAsyncTask().execute();
    }

    class MyAsyncTask extends AsyncTask<Void, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ContactListActivity.this, "", "Please Wait!");
        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                String response = GetDataFromServer.getDataFromServer(GetDataFromServer.HTTP_GET, getString(R.string.api_host) + "/contact_list/" + userId + "?callback=studymate", null, null).trim();
                response = response.replace("studymate(", "");
                response.replace(")", "");
                return response;
            } catch (Exception e) {
                System.out.println("-------Expection doInBackground -->" + e);
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.cancel();
            if (null != s && !s.trim().equalsIgnoreCase("")) {
                Gson gson = new Gson();
                JSONObject jsonObject = null;
                try {
                    tmessagesList.clear();
                    jsonObject = new JSONObject(s);
                    contactListPojo = gson.fromJson(jsonObject.toString(), ContactListPojo.class);
                    tmessagesList = contactListPojo.getTmessage();
                    MyAdapter myAdapter = new MyAdapter(ContactListActivity.this);
                    listView.setAdapter(myAdapter);
                } catch (Exception e) {
                    System.out.println("-------Expection onPostExecute -->" + e);
                }
            }

        }
    }

    class MyAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;

        public MyAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return tmessagesList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.list_item_contact, null);
                viewHolder.txtViewSubject = (TextView) convertView.findViewById(R.id.txtViewSubject);
                viewHolder.txtViewBatch = (TextView) convertView.findViewById(R.id.txtViewBatch);
                viewHolder.txtViewCenter = (TextView) convertView.findViewById(R.id.txtViewCenter);
                viewHolder.txtViewGroupName = (TextView) convertView.findViewById(R.id.txtViewGroupName);
                viewHolder.txtViewTime = (TextView) convertView.findViewById(R.id.txtViewTime);
                viewHolder.txtViewLastMessage = (TextView) convertView.findViewById(R.id.txtViewLastMessage);
                viewHolder.txtViewUnreadCount = (TextView) convertView.findViewById(R.id.txtViewUnreadCount);
                viewHolder.layoutUnReadCount = (LinearLayout) convertView.findViewById(R.id.layoutUnReadCount);
                viewHolder.layoutPhoto = (LinearLayout) convertView.findViewById(R.id.layoutPhoto);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Tmessage tmessage = tmessagesList.get(position);

            String colorCodee = intToARGB(tmessage.getGid().hashCode());
            int size = colorCodee.length();
            if (size < 6) {
                int remainsize = 6 - size;
                for (int i = 0; i < remainsize; i++) {
                    colorCodee = "0" + colorCodee;
                }
            } else if (size > 6) {
                colorCodee = colorCodee.substring(0, 6);
            }
            colorCodee = "#" + colorCodee;
            ((GradientDrawable) viewHolder.layoutPhoto.getBackground()).setColor(Color.parseColor(colorCodee));
            viewHolder.txtViewTime.setText(changeTimeFormat(tmessage.getTime()));
            viewHolder.txtViewLastMessage.setText(tmessage.getLastMessage());
            viewHolder.txtViewUnreadCount.setText(tmessage.getUmess());
            if (!tmessage.getUmess().trim().equalsIgnoreCase("0")) {
                viewHolder.layoutUnReadCount.setVisibility(LinearLayout.VISIBLE);
                viewHolder.txtViewTime.setTextColor(Color.GREEN);
            } else {
                viewHolder.layoutUnReadCount.setVisibility(LinearLayout.INVISIBLE);
                viewHolder.txtViewTime.setTextColor(Color.DKGRAY);
            }

            if (!tmessage.getIsGroup().trim().equalsIgnoreCase("True")) {
                String gName = tmessage.getGname();
                if (gName.contains(" -")) {
                    String[] sp = gName.split(" -");

                    String tempGName = sp[0];
                    if (tempGName.contains("("))
                        tempGName = tempGName.substring(0, tempGName.indexOf("("));

                    if (tempGName.trim().equalsIgnoreCase(name.trim())) {
                        tempGName = sp[1].trim();
                    } else {
                        tempGName = sp[0].trim();
                    }

                    viewHolder.txtViewBatch.setText(sp[sp.length - 1].trim().substring(0, 1).toUpperCase());
                    viewHolder.txtViewGroupName.setText(tempGName);//(sp[sp.length - 1].trim());
                }
                viewHolder.txtViewSubject.setText("");
                viewHolder.txtViewCenter.setText("");
            } else {
                String gName = tmessage.getGname();
                String[] sp = gName.split("-");
                viewHolder.txtViewBatch.setText(sp[2].trim());
                viewHolder.txtViewSubject.setText(sp[0].trim());
                viewHolder.txtViewCenter.setText(sp[1].trim());


                viewHolder.txtViewGroupName.setText(tmessage.getGname());
            }
            return convertView;
        }

        public String intToARGB(int i) {
            return Integer.toHexString(((i >> 24) & 0xFF)) +
                    Integer.toHexString(((i >> 16) & 0xFF)) +
                    Integer.toHexString(((i >> 8) & 0xFF));

        }

        private String changeTimeFormat(String oldTime) {
            SimpleDateFormat simpleDateFormatOld = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            SimpleDateFormat simpleDateFormatNew = new SimpleDateFormat("hh:mm a");
            try {
                return simpleDateFormatNew.format(simpleDateFormatOld.parse(oldTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "";
        }


        class ViewHolder {
            private TextView txtViewSubject, txtViewBatch, txtViewCenter, txtViewGroupName, txtViewTime, txtViewLastMessage, txtViewUnreadCount;
            private LinearLayout layoutUnReadCount, layoutPhoto;
        }
    }

    public String getDisplayName(Tmessage tmessage){
        String tempGname = "";
        if (!tmessage.getIsGroup().trim().equalsIgnoreCase("True")) {
            String gName = tmessage.getGname();
            if (gName.contains("-")) {
                String[] sp = gName.split(" -");

                tempGname = sp[0];
                if (tempGname.contains("("))
                    tempGname = tempGname.substring(0, tempGname.indexOf("("));

                if (tempGname.trim().equalsIgnoreCase(name.trim())) {
                    tempGname = sp[1].trim();
                } else {
                    tempGname = sp[0].trim();
                }
            }
        } else {
            tempGname = tmessage.getGname();
        }
        return tempGname;
    }
}
