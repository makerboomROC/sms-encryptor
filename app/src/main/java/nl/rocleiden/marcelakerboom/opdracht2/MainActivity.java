package nl.rocleiden.marcelakerboom.opdracht2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener {
    private EditText recipientText;
    private EditText messageText;
    private EditText encryptedText;
    private SeekBar strengthSlider;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void encrypt(View view) {
        encryptMessage();
    }

    public void send(View view) {
        encryptMessage();
        sendEncryptedMessage();
    }

    public void cancel(View view) {

    }

    protected void encryptMessage() {
        CharSequence message = messageText.getText();
        String encryptedMessage = encryptString(message);

        encryptedText.setText(encryptedMessage);
    }

    protected void sendEncryptedMessage() {
        SmsManager smsManager = SmsManager.getDefault();
        String encryptedMessage = encryptedText.getText().toString();
        String recipient = recipientText.getText().toString();
        try {
            smsManager.sendTextMessage(recipient, null, encryptedMessage, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            String errorMessage = "SMS failed: " + e.getMessage();
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable message) {
        char letter = message.charAt(message.length() - 1);
        String encryptedMessage = encryptedText.getText().toString() + encryptLetter(letter);
        encryptedText.setText(encryptedMessage);
//        encryptMessage();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        encryptMessage();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.decrypt:
                Intent decryptIntent = new Intent(this, DecryptActivity.class);
                startActivity(decryptIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipientText = (EditText) findViewById(R.id.recipientText);
        messageText = (EditText) findViewById(R.id.messageText);
        encryptedText = (EditText) findViewById(R.id.encryptedText);
        strengthSlider = (SeekBar) findViewById(R.id.strengthSlider);

        messageText.addTextChangedListener(this);
        strengthSlider.setOnSeekBarChangeListener(this);

        encryptMessage();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private String encryptString(CharSequence messageChars) {
        int length = messageChars.length();
        char[] encryptedChars = new char[length];

        for (int i = 0; i < length; i++) {
            char messageChar = messageChars.charAt(i);
            char encryptedChar = encryptLetter(messageChar);
            encryptedChars[i] = encryptedChar;
        }

        return new String(encryptedChars);
    }

    private char encryptLetter(char letter) {
        int strength = strengthSlider.getProgress();

        for (int i = 0; i < strength; i++) {
            if (letter >= 'A' && letter < 'Z') letter++;
            else if (letter == 'Z') letter = 'A';
            else if (letter >= 'a' && letter < 'z') letter++;
            else if (letter == 'z') letter = 'a';
        }
        return letter;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://nl.rocleiden.marcelakerboom.opdracht2/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://nl.rocleiden.marcelakerboom.opdracht2/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
