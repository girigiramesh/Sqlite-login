package com.example.apple.login_sqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText Et_user_name, Et_password;
    Button Btn_login, Btn_show_data;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Et_user_name = findViewById(R.id.Et_user_name);
        Et_password = findViewById(R.id.Et_password);

        Btn_login = findViewById(R.id.Btn_login);
        Btn_login.setOnClickListener(this);

        Btn_show_data = findViewById(R.id.Btn_show_data);
        Btn_show_data.setOnClickListener(this);

        mydb = new DatabaseHelper(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_login:
                if (Et_user_name.getText().toString().length() == 0) {
                    ShowMessage("Email","Please enter email..");
                    return;
                }
                if (Et_user_name.getText().toString().length() > 0 && !android.util.Patterns.EMAIL_ADDRESS.matcher(Et_user_name.getText().toString()).matches()) {
                    ShowMessage("Email","Please enter valid email..");
                    return;
                }
                if (Et_password.getText().toString().length() == 0) {
                    ShowMessage("Password","Please enter password..");
                    return;
                }
                boolean isInserted = mydb.insertData(Et_user_name.getText().toString(), Et_password.getText().toString());
                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Data is Add Sucessfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "No Data is Found", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.Btn_show_data:
                Cursor res = mydb.getAllData();
                if (res.getCount() == 0) {
                    ShowMessage("Error", "Nothings");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("ID :" + res.getString(0) + "\n");
                    buffer.append("EMAIL :" + res.getString(1) + "\n");
                    buffer.append("PASSWORD :" + res.getString(2) + "\n");
                }

                ShowMessage("Stored Data", buffer.toString());
                break;
            default:
                break;
        }
    }

    public void ShowMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
