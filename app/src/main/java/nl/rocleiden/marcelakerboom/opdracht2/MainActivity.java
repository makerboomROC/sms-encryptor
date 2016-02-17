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
    private EditText reversedText;
    private SeekBar[] strengthSliders;
    private CeasarEncryptor encryptor;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public String getRecipient() {
        return recipientText.getText().toString();
    }

    public int[] getStrength() {
        int length = strengthSliders.length;
        int[] strength = new int[length];

        for (int i = 0; i < length; i++) {
            strength[i] = strengthSliders[i].getProgress();
        }

        return strength;
    }

    public String getMessage() {
        return messageText.getText().toString();
    }

    public String getEncryptedMessage() {
        return encryptedText.getText().toString();
    }

    public void send(View view) {
        encryptMessage();
        sendEncryptedMessage();
    }

    public void cancel(View view) {
        messageText.setText("");
        encryptedText.setText("");
        recipientText.setText("");

        for (SeekBar strengthSlider : strengthSliders) {
            strengthSlider.setProgress(0);
        }
    }

    protected void encryptMessage() {
        String encryptedMessage = encryptor.encrypt(getMessage(), getStrength());
        encryptedText.setText(encryptedMessage);
    }

    protected void sendEncryptedMessage() {
        SmsManager smsManager = SmsManager.getDefault();
        try {
            smsManager.sendTextMessage(getRecipient(), null, getEncryptedMessage(), null, null);
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
    public void onTextChanged(CharSequence message, int start, int before, int count) {
        if (count < 1) {
            int length = getEncryptedMessage().length() - 1;
            String encryptedMessage = getEncryptedMessage().substring(0, length);
            encryptedText.setText(encryptedMessage);
        } else {
            char letter = message.charAt(start);
            int strength = getStrength()[start % 2];

            char encryptedLetter = encryptor.encryptChar(letter, strength);
            String encryptedMessage = getEncryptedMessage() + encryptedLetter;

            encryptedText.setText(encryptedMessage);
        }
        reverseText();
    }

    @Override
    public void afterTextChanged(Editable message) {
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
                decryptIntent.putExtra("encryptedMessage", encryptedText.getText().toString());
                decryptIntent.putExtra("strengths", getStrength());
                startActivity(decryptIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        encryptor = new CeasarEncryptor();
        recipientText = (EditText) findViewById(R.id.recipientText);
        messageText = (EditText) findViewById(R.id.messageText);
        encryptedText = (EditText) findViewById(R.id.encryptedText);
        reversedText = (EditText) findViewById(R.id.reversedText);

        strengthSliders = new SeekBar[2];
        strengthSliders[0] = (SeekBar) findViewById(R.id.strengthSlider1);
        strengthSliders[1] = (SeekBar) findViewById(R.id.strengthSlider2);

        messageText.addTextChangedListener(this);

        for (SeekBar strengthSlider : strengthSliders) {
            strengthSlider.setOnSeekBarChangeListener(this);
        }

        encryptMessage();
        reverseText();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    private void reverseText() {
        CharSequence message = messageText.getText();
        String reversedMessage = new String();

        for (int i = message.length() - 1; i >= 0; i--) {
            reversedMessage = reversedMessage + message.charAt(i);
        }

        reversedText.setText(reversedMessage);
    }
}
