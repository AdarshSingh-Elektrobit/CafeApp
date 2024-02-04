package com.example.firstapplication;

import static androidx.core.content.ContextCompat.getSystemService;

import static javax.crypto.Cipher.SECRET_KEY;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.content.Context;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
 import android.view.ViewGroup;
import android.widget.Button;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class fragment1 extends Fragment {

    private String SECRET_KEY = "aesEncryptionKey";
    private String INIT_VECTOR = "encryptionIntVec";

    View view;
    EditText userName , pwd;
    Button loginBtn;

    String userNameVal, pwdVal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_one, container, false);

        userName = view.findViewById(R.id.username);
        pwd = view.findViewById(R.id.password);
        loginBtn = view.findViewById(R.id.loginButton);

        userNameVal = userName.getText().toString();
        pwdVal = pwd.getText().toString();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText userNameText = view.findViewById(R.id.username);
                EditText pwd = view.findViewById(R.id.password);

                String encryptedPwd =  encrypt(pwd.getText().toString());

                // class to add values in the database
                ContentValues values = new ContentValues();
                // fetching text from user
                values.put(MyContentProvider.username,userNameText.getText().toString());
                values.put(MyContentProvider.pwdColumn,encryptedPwd);

                // Assuming values is a ContentValues object with data
//                Uri uri = getContext().getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
                // Assuming values is a ContentValues object with data
                Uri uri = getContext().getContentResolver().insert(Uri.parse("content://com.example.firstapplication.provider/Users"), values);

                // inserting into database through content URI
//                requireActivity().getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
                // displaying a toast message
//        Toast.makeText(this, "New Record Inserted", Toast.LENGTH_LONG).show();
                userNameText.setText("");
                pwd.setText("");
                onClickShowDetails(view);

            }
        });

        return view;
    }
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), 0);
        return true;
    }







    @SuppressLint("Range")
    public void onClickShowDetails(View view) {
// inserting complete table details in this text field
 // creating a cursor object of the
// content URI
        Cursor cursor = requireActivity().getContentResolver().query(MyContentProvider.CONTENT_URI,
                null, null, null, null);
// iteration of the cursor
// to print whole table
        if(cursor.moveToFirst()) {
            StringBuilder strBuild=new StringBuilder();
            while (!cursor.isAfterLast()) {
                strBuild.append("\n").
                        append(cursor.getString(cursor.getColumnIndex(MyContentProvider.username))).
                        append("-").append(cursor.getString(cursor.getColumnIndex(MyContentProvider.pwdColumn)));

                cursor.moveToNext();
            }
            Log.i("DataBase", String.valueOf(strBuild));
        }
        else {
            Log.i("DataBase","No Records Found");
        }
    }




    public String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }








}