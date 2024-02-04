package com.example.firstapplication;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class fragment2 extends Fragment {

    private String SECRET_KEY = "aesEncryptionKey";
    private String INIT_VECTOR = "encryptionIntVec";

    View view;
    EditText userName, pwd;
    Button loginBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_two, container, false);

        userName = view.findViewById(R.id.username);
        pwd = view.findViewById(R.id.password);
        loginBtn = view.findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUserName = userName.getText().toString();
                String enteredPassword = encrypt(pwd.getText().toString());

                // Check if the username and password exist in the database
                if (checkLoginCredentials(enteredUserName, enteredPassword)) {






                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Replace the current fragment with the new one
                    CafePage cafeFragment = new CafePage();
                    fragmentTransaction.replace(R.id.cafeframeLayout, cafeFragment);

                    // Add the transaction to the back stack (optional, for back navigation)
                    fragmentTransaction.addToBackStack(null);

                    // Commit the transaction
                    fragmentTransaction.commit();
                    ((MainActivity) requireActivity()).hideViews();




                    // Login successful, perform necessary actions
                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show();




                } else {
                    // Login failed, show an error message
                    Toast.makeText(requireContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private boolean checkLoginCredentials(String enteredUserName, String enteredPassword) {
        // Query the database to check if the username and password exist
        Cursor cursor = requireActivity().getContentResolver().query(
                MyContentProvider.CONTENT_URI,
                null,
                MyContentProvider.username + "=? AND " + MyContentProvider.pwdColumn + "=?",
                new String[]{enteredUserName, enteredPassword},
                null
        );

        // If the cursor has any rows, it means the username and password exist
        boolean loginSuccessful = cursor != null && cursor.getCount() > 0;

        // Close the cursor to release resources
        if (cursor != null) {
            cursor.close();
        }

        return loginSuccessful;
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