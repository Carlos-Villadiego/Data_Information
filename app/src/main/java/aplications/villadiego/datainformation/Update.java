package aplications.villadiego.datainformation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressLint("Registered")
public class Update extends AppCompatActivity {
    ControllerDB controllerDB;
    EditText name, salary, document;

    Spinner spinnerstr, spinneredu;
    int stratum, i;
    String education;
    Button update, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        document =findViewById(R.id.txtdoc);
        name =findViewById(R.id.txtname);
        salary =findViewById(R.id.tstsalary);
        spinnerstr =findViewById(R.id.spnstr);
        spinneredu =findViewById(R.id.spnedu);
        update =findViewById(R.id.btnupdate);
        cancel =findViewById(R.id.btncancel);

        controllerDB = new ControllerDB(getApplicationContext());

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.stratum,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerstr.setAdapter(adapter);
        spinnerstr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stratum = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        ArrayAdapter<CharSequence> adaptador=ArrayAdapter.createFromResource(this,R.array.education,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneredu.setAdapter(adaptador);
        spinneredu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                education = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        Intent i = getIntent();
        this.i = i.getIntExtra("indice", 0);
        ArrayList<User> lista = controllerDB.getUser();
        User user = lista.get(this.i);
        document.setText(user.getDoc());
        name.setText(user.getName());
        salary.setText(String.valueOf(user.getSalary()));


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User estudiant = new User(document.getText().toString().trim(), name.getText().toString(), stratum, Float.parseFloat(salary.getText().toString().trim()), education);
                int retorno = controllerDB.update(estudiant);
                if (retorno == 1) {
                    Toast.makeText(getApplicationContext(), "Usuario correctamente actualizado", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error. Verifique los datos ingresados.", Toast.LENGTH_SHORT).show();
                }
                limpiar();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

    }


    private void limpiar() {
        name.setText("");
        salary.setText("");
    }


}
