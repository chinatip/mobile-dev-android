package chinatip.login_firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private  FirebaseAuth mAuth;
    TextView email;
    Button signout, newPost, viewPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        email = (TextView) findViewById(R.id.displayEmail);
        signout = (Button) findViewById(R.id.signout);
        newPost = (Button) findViewById(R.id.newPost);
        viewPost = (Button) findViewById(R.id.viewPost);


        if(mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewPost.class));
            }
        });

        viewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewPosts.class));
            }
        });

        String email = (String) getIntent().getSerializableExtra("email");
        TextView displayEmail = (TextView) findViewById(R.id.displayEmail);
        displayEmail.setText(email);
    }
}
