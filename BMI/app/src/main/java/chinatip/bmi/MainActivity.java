package chinatip.bmi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import static chinatip.bmi.R.id.text;
public class MainActivity extends AppCompatActivity {
    EditText weight;
    EditText height;
    Button submit;
    TextView result;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weight = (EditText) findViewById(R.id.wtext);
        height = (EditText) findViewById(R.id.htext);
        result = (TextView) findViewById(R.id.textView);
        img = (ImageView) findViewById(R.id.imageView);
        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                double w = Double.parseDouble(weight.getText().toString());
                double h = Double.parseDouble(height.getText().toString());
                double sol;
                if(h < 100) sol = w / (h*h);
                else sol = w / ((h/100)*(h/100));
                result.setText("Your BMI is " + String.format("%.2f", sol));
                changeImage(sol);
            }
        });

    }

    protected void changeImage(double sol) {
        if(sol < 22) {
            img.setImageResource(R.drawable.skinny);
        }
        else if(sol < 30) {
            img.setImageResource(R.drawable.healthy);
        }
        else {
            img.setImageResource(R.drawable.fat);
        }
    }


}
