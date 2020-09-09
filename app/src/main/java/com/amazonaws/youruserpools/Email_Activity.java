package com.amazonaws.youruserpools;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;


public class Email_Activity extends AppCompatActivity {

//    private EditText mEditTextTo;
//    private EditText mEditTextSubject;
//    private EditText mEditTextMessage;

    public EditText mEmail;
    public EditText mSubject;
    public EditText mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);


//        mEditTextTo = findViewById(R.id.edit_text_to);
//        mEditTextSubject = findViewById(R.id.edit_text_subject);
//        mEditTextMessage = findViewById(R.id.edit_text_message);
//
//        Button buttonSend = findViewById(R.id.button_send);
//        buttonSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendMail();
//                onBackPressed();
//            }
//      });
//    }
//
//    private void sendMail() {
//        String recipientList = mEditTextTo.getText().toString();
//        String[] recipients = recipientList.split(",");
//
//        String subject = mEditTextSubject.getText().toString();
//        String message = mEditTextMessage.getText().toString();
////
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
//        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
//        intent.putExtra(Intent.EXTRA_TEXT, message);
//
//        intent.setType("message/rfc822");
//        startActivity(Intent.createChooser(intent, "Choose an email client"));
//    }
//}


        mEmail = findViewById(R.id.edit_text_to);
        mMessage = findViewById(R.id.edit_text_subject);
        mSubject = (EditText)findViewById(R.id.edit_text_message);

        FloatingActionButton fab = findViewById(R.id.button_send);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
                onBackPressed();
            }
        });
    }

    private void sendMail() {

        String mail = mEmail.getText().toString().trim();
        String message = mMessage.getText().toString();
        String subject = mSubject.getText().toString().trim();

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);

        javaMailAPI.execute();

    }

}