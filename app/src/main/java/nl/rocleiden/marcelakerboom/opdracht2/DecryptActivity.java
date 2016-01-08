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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);


        messageText = (EditText) findViewById(R.id.messageText);
        decryptedText = (EditText) findViewById(R.id.decryptedText);
        strengthSlider = (SeekBar) findViewById(R.id.strengthSlider);

        messageText.addTextChangedListener(this);
        strengthSlider.setOnSeekBarChangeListener(this);

        decryptMessage();
    }

    protected void decryptMessage() {
        CharSequence message = messageText.getText();
        String decryptedMessage = decryptString(message);

        decryptedText.setText(decryptedMessage);
    }

    private String decryptString(CharSequence messageChars) {
        int length = messageChars.length();
        char[] encryptedChars = new char[length];

        for (int i = 0; i < length; i++) {
            char messageChar = messageChars.charAt(i);
            char encryptedChar = decryptLetter(messageChar);
            encryptedChars[i] = encryptedChar;
        }

        return new String(encryptedChars);
    }

    private char decryptLetter(char letter) {
        int strength = strengthSlider.getProgress();

        for (int i = 0; i < strength; i++) {
            if (letter > 'A' && letter <= 'Z') letter--;
            else if (letter == 'A') letter = 'Z';
            else if (letter > 'a' && letter <= 'z') letter--;
            else if (letter == 'a') letter = 'z';
        }
        return letter;
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
        String decryptedMessage = decryptedText.getText().toString() + decryptLetter(letter);
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
