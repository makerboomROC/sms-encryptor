package nl.rocleiden.marcelakerboom.opdracht2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;

public class DecryptActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener {
    private EditText messageText;
    private EditText decryptedText;
    private SeekBar strengthSlider;
    private CeasarEncryptor encryptor;

    public int getStrength() {
        return strengthSlider.getProgress();
    }

    public String getMessage() {
        return messageText.getText().toString();
    }

    public String getDecryptedMessage() {
        return decryptedText.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);

        encryptor = new CeasarEncryptor();
        messageText = (EditText) findViewById(R.id.messageText);
        decryptedText = (EditText) findViewById(R.id.decryptedText);
        strengthSlider = (SeekBar) findViewById(R.id.strengthSlider);

        messageText.addTextChangedListener(this);
        strengthSlider.setOnSeekBarChangeListener(this);

        decryptMessage();
    }

    protected void decryptMessage() {
        String decryptedMessage = encryptor.decrypt(getMessage(), getStrength());
        decryptedText.setText(decryptedMessage);
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
        char decryptedLetter = encryptor.decryptChar(letter, getStrength());
        CharSequence decryptedMessage = getDecryptedMessage() + decryptedLetter;

        decryptedText.setText(decryptedMessage);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        decryptMessage();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
