package co.edu.eafit.jquiro12.settingup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Welcome extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "co.edu.eafit.jquiro12.settingup.MESSAGE";
    private final int STANDAR_PORT = 5555;


    Button send_button;
    EditText user_text;
    EditText password_text;
    Switch rememberme_switch;
    Data program_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        send_button = (Button) findViewById(R.id.button);
        user_text = (EditText) findViewById(R.id.editText);
        password_text = (EditText) findViewById(R.id.editText2);
        rememberme_switch = (Switch) findViewById(R.id.switch_sesion);

        program_data = new Data();
        program_data.setPort(STANDAR_PORT);


    }

    public void sendMessage(View view) {

        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("global_data", program_data);
        startActivity(intent);
    }


    public void senddatatoserver(View v) {
        String email, password;
        boolean rememberme;
        //function in the activity that corresponds to the layout button
        email = user_text.getText().toString();
        password = user_text.getText().toString();

        rememberme = rememberme_switch.isActivated();

        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("e-mail", email);
            post_dict.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (post_dict.length() > 0) {
            new SendJsonDataToServer().execute(String.valueOf(post_dict));

        }
    }

    public void wrongCredentials(){
        //Toast().show();
        TextView textView = findViewById(R.id.textView5);
        textView.setVisibility(View.VISIBLE);
    }
}
