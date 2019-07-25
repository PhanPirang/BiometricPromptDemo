package com.example.biometricpromptdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Executor newExecutor = Executors.newSingleThreadExecutor();

        FragmentActivity activity = this;

        final BiometricPrompt myBiometricPrompt = new BiometricPrompt(activity, newExecutor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                switch (errorCode) {
                    case BiometricPrompt.ERROR_NEGATIVE_BUTTON:
                        Log.d(TAG, "Cancel");
                        break;
                    case BiometricPrompt.ERROR_NO_BIOMETRICS:
                        Log.d(TAG, "No fingerprints enrolled");
                        break;
                    default:
                        Log.d(TAG, errString + ", " + errorCode);
                        break;
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.d(TAG, "Fingerprint recognised successfully");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.d(TAG, "Fingerprint not recognised");
            }

        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Title text goes here")
                .setSubtitle("Subtitle goes here")
                .setDescription("This is the description")
                .setNegativeButtonText("Cancel")
                .build();

        findViewById(R.id.launchAuthentication).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBiometricPrompt.authenticate(promptInfo);
            }
        });
    }
}
