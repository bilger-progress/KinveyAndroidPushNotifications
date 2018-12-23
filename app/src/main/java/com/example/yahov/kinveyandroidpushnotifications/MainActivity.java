package com.example.yahov.kinveyandroidpushnotifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.model.User;
import com.kinvey.android.store.UserStore;
import com.kinvey.java.core.KinveyClientCallback;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Client kinveyClient;
    private String kinveyUserName = "xxx";
    private String kinveyUserPassword = "xxx";
    private Button kinveyLoginButton;
    private Button kinveyPushRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get UI elements.
        kinveyLoginButton = findViewById(R.id.buttonKinveyLogin);
        kinveyPushRegisterButton = findViewById(R.id.buttonKinveyPushRegister);

        // Set-up Kinvey Backend.
        kinveyClient = new Client.Builder(this).build();
        // Ping Kinvey backend to check connection.
        kinveyClient.ping(new KinveyPingCallback() {
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Exception was thrown. Check logs.", Toast.LENGTH_LONG).show();
                System.out.println("Exception was thrown: " + t.getMessage());
            }

            public void onSuccess(Boolean b) {
                Toast.makeText(getApplicationContext(), "Kinvey Ping Response: " + b.toString(), Toast.LENGTH_LONG).show();
                kinveyLoginButton.setVisibility(View.VISIBLE);
            }
        });

        kinveyLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    KinveyLogin();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        kinveyPushRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KinveyPushRegister();
            }
        });
    }

    private void KinveyLogin () throws IOException {
        // Check if there is a User already logged in.
        if (kinveyClient.isUserLoggedIn()) {
            Toast.makeText(getApplicationContext(), "User already logged in!", Toast.LENGTH_LONG).show();
            System.out.println("User: " + kinveyClient.getActiveUser().toString());
            kinveyPushRegisterButton.setVisibility(View.VISIBLE);
            return;
        }

        UserStore.login(kinveyUserName, kinveyUserPassword, kinveyClient, new KinveyClientCallback<User>() {
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Exception was thrown. Check logs.", Toast.LENGTH_LONG).show();
                System.out.println("Exception was thrown: " + t.getMessage());
            }
            @Override
            public void onSuccess(User u) {
                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                System.out.println("User: " + u.toString());
                kinveyPushRegisterButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void KinveyPushRegister () {
        kinveyClient.push(GCMService.class).initialize(getApplication());
    }
}
