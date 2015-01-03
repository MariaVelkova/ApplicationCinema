package com.example.maria.cinema.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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
public class FragmentLoginTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login_tab, container, false);
        return rootView;
    }
    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();
        activity.getActionBar().setTitle(getString(R.string.login));
        Button loginBtn = (Button) activity.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.loginBtn) {
                    EditText username = (EditText) activity.findViewById(R.id.username);
                    EditText password = (EditText) activity.findViewById(R.id.password);
                    String usernameValue = username.getText().toString();
                    String passwordValue = password.getText().toString();

                    try {
                        DBManager dbManager = DBManager.getInstance();
                        User user = dbManager.loginUser(activity, usernameValue, passwordValue);
                        Intent intent = new Intent(activity, UserHomeActivity.class);
                        startActivity(intent);
                    } catch (Exception exception) {
                        Log.d("EXCEPTION", exception.getMessage());
                        Toast.makeText(activity, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
