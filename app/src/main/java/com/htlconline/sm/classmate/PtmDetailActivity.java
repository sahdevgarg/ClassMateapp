package com.htlconline.sm.classmate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by M82061 on 3/8/2017.
 */

public class PtmDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText commentEditText;
    private Button submitButton;
    private LinearLayout commentListLayout;
    private LayoutInflater inflater;
    private View myView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ptm_detail_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("PTM Detail");

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        commentEditText = (EditText)findViewById(R.id.commentEditText);
        submitButton = (Button)findViewById(R.id.commentSubmit);
        submitButton.setOnClickListener(this);
        commentListLayout = (LinearLayout)findViewById(R.id.commentListLayout);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.commentSubmit:

                if(commentEditText.getText().toString().trim().length() > 0){
                    myView = inflater.inflate(R.layout.comment_list_item, null);

                    TextView commentTextView = (TextView)myView.findViewById(R.id.commentDetail);
                    commentTextView.setText(commentEditText.getText().toString());

                    commentListLayout.addView(myView);
                    commentEditText.setText("");

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(commentEditText.getWindowToken(), 0);
                }else {
                    Toast.makeText(this, "Please enter comment first..!!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
