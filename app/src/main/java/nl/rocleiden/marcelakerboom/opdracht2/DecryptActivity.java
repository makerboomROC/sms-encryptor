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

        Bundle extras = getIntent().getExtras();
        encryptor = new CeasarEncryptor();
        messageText = (EditText) findViewById(R.id.messageText);
        decryptedText = (EditText) findViewById(R.id.decryptedText);

        messageText.setText(extras.getString("encryptedMessage"));
        messageText.addTextChangedListener(this);

        initializeSliders(extras.getIntArray("strengths"));
        decryptMessage();
    }

    private void initializeSliders(int[] strengths) {
        strengthSliders = new SeekBar[]{
                (SeekBar) findViewById(R.id.strengthSlider1),
                (SeekBar) findViewById(R.id.strengthSlider2)
        };

        for (int i = 0, length = strengthSliders.length; i < length; i++) {
            SeekBar strengthSlider = strengthSliders[i];
            strengthSlider.setProgress(strengths[i]);
            strengthSlider.setOnSeekBarChangeListener(this);
        }
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
