package com.example.android.encrytpo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.security.SecureRandom;

public class GeneratePasswordActivity extends AppCompatActivity {

    ConstraintLayout mainLayout;
    TextView password_genrated;
    Button btn;
    SeekBar length;
    TextView length_show;
    Button copy_Password;

  private int pos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_password2);
        password_genrated = findViewById(R.id.genrated_password);
        length = findViewById(R.id.length_Seekbar);
        length_show = findViewById(R.id.length_heading);
        btn = findViewById(R.id.generate_password_button);
        copy_Password = findViewById(R.id.copy);
        mainLayout = findViewById(R.id.password_layout);

           // for seekBar ie defining length of the password
           length.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
               @Override
               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    pos = progress;
                    length_show.setText("Length: "+pos);
               }

               @Override
               public void onStartTrackingTouch(SeekBar seekBar) {

               }

               @Override
               public void onStopTrackingTouch(SeekBar seekBar) {

               }
           });
             // generate password button
           btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   if(pos > 8){
                       password_genrated.setText( generatePassword(pos));
                       showSnackBar("Password Generated !!");
                   }else{
                      showSnackBar("Length should be above 8");
                   }

               }
           });

           copy_Password.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   if(password_genrated.getText().toString().equals("eg:cX163Rgadk32424")){
                       showSnackBar("Input the length");
                   }else{

                       ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                       ClipData clipData = ClipData.newPlainText("EditText",password_genrated.getText().toString());
                       clipboard.setPrimaryClip(clipData);
                       showSnackBar("Password Copied!!");
                   }
               }
           });

    }

     private void  showSnackBar(String message){
         Snackbar snackbar = Snackbar.make(mainLayout,message,Snackbar.LENGTH_SHORT);
         snackbar.setAction("okay", new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });
         snackbar.setActionTextColor(Color.WHITE);
          snackbar.show();
     }


    private String generatePassword(int pos) {
        String allChar = "ABCDEFGHIJKLMONPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890#@$!%";
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for(int i =0;i<pos;i++){
            int index = random.nextInt(allChar.length()); // randomly choose the index from allchar
            sb.append(allChar.charAt(index));
        }
         return  sb.toString();
    }
}