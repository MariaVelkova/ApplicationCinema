package com.example.maria.cinema.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maria.cinema.R;
import com.example.maria.cinema.activities.UserHomeActivity;
import com.example.maria.cinema.common.DBManager;
import com.example.maria.cinema.models.User;

/**
 * Created by Maria on 12/28/2014.
 */
public class FragmentUserInfo extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user_info, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();
        activity.getActionBar().setTitle(getString(R.string.register));
        Button saveUserBtn = (Button) activity.findViewById(R.id.saveUserBtn);
        saveUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.saveUserBtn) {
                    EditText username = (EditText) activity.findViewById(R.id.username);
                    String usernameValue = username.getText().toString();
                    EditText email = (EditText) activity.findViewById(R.id.email);
                    String emailValue = email.getText().toString();
                    EditText password = (EditText) activity.findViewById(R.id.password);
                    String passwordValue = password.getText().toString();
                    EditText password2 = (EditText) activity.findViewById(R.id.password2);
                    String password2Value = password2.getText().toString();
                    DBManager dbManager = DBManager.getInstance();
                    try {
                        User user = dbManager.registerUser(activity, usernameValue, emailValue, passwordValue, password2Value);
                        Intent intent = new Intent(activity, UserHomeActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.d("EXCEPTION", e.getMessage());
                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}
