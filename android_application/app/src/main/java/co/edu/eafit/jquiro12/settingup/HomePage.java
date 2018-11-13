package co.edu.eafit.jquiro12.settingup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class HomePage extends AppCompatActivity {
    private int STANDAR_PORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        STANDAR_PORT = 5555;
        try{
            STANDAR_PORT = (Integer) getIntent().getExtras().getSerializable("STANDAR_PORT");

        }catch (Exception e){
            e.printStackTrace();

        }



        /*
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(Welcome.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
*/



    }

    public void installNebulon(View view) {
        Intent intent = new Intent(this, Nebulizer_founded.class);
        intent.putExtra("STANDAR_PORT", (Serializable)STANDAR_PORT);
        startActivity(intent);


    }

    public void log_out(View view) {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }
}
