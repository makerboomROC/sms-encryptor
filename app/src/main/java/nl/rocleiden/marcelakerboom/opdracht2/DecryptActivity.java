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
    private SeekBar[] strengthSliders;
    private CeasarEncryptor encryptor;

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

        strengthSliders = new SeekBar[2];
        strengthSliders[0] = (SeekBar) findViewById(R.id.strengthSlider1);
        strengthSliders[1] = (SeekBar) findViewById(R.id.strengthSlider2);

        messageText.addTextChangedListener(this);

        for (int i = 0; i < strengthSliders.length; i++) {
            strengthSliders[i].setOnSeekBarChangeListener(this);
        }

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
        int index = message.length() - 1;
        char letter = message.charAt(index);
        int strength = getStrength()[index % 2];

        char decryptedLetter = encryptor.decryptChar(letter, strength);
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
