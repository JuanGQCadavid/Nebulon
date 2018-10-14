package co.edu.eafit.jquiro12.settingup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "co.edu.eafit.jquiro12.settingup.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void sendMessage(View view) {
        /*
        Intent intent = new Intent(this, HomePage.class);
        EditText editText = (EditText) findViewById(R.id.input_user);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/

        TextView textView = findViewById(R.id.textView5);
        textView.setVisibility(View.VISIBLE);
    }
}
