package iut.lp.dba.contactprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static iut.lp.dba.contactprovider.ContactProvider.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void show_contact(View view){
        Cursor c = getContentResolver().query(CONTENT_URI,null,null,null, "name");
        String result = "Contact resolver app";
        if (!c.moveToFirst()){
            Toast.makeText(this,  result + "not inserted  yet", Toast.LENGTH_LONG).show();
        }else {
            do {
                result = result + "\n"+ "id : " + c.getString(c.getColumnIndex("id")) +  "\n"+
                        "name : " + c.getString(c.getColumnIndex("name")) +  "\n"+
                        "phone : " + c.getString(c.getColumnIndex("phone_number")) +  "\n"+
                        "email : " + c.getString(c.getColumnIndex("email")) +  "\n";
            }while (c.moveToNext());

            Toast.makeText(this,  result , Toast.LENGTH_LONG).show();
        }
    }

}