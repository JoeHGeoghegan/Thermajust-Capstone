package capstone.thermajust;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WiFi_Default extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_default);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Fields to access
        final EditText name = (EditText)findViewById(R.id.editText_wifi_default_field);
        final EditText password = (EditText)findViewById(R.id.editText_wifi_default_password_field);
        final Button save = (Button)findViewById(R.id.button_wifi_default_save);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                name.getText().toString();
//                password.getText().toString();
                Main_Tabbed_View.model.setWifiDefaults(name.getText().toString(),
                                                        password.getText().toString());
                Main_Tabbed_View.model.saveOptions(getApplicationContext());
            }
        });

        //set wifi defaults if they have been set previously
        String wifiDefaultName = Main_Tabbed_View.model.getWiFiDefaultName();
        String wifiDefaultPassword = Main_Tabbed_View.model.getWiFiDefaultPassword();
        if (wifiDefaultName != null) {
            name.setText(wifiDefaultName);
            if (wifiDefaultPassword != null) { //shouldn't just fill in a password
                password.setText(wifiDefaultPassword);
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void save() {

    }
}
