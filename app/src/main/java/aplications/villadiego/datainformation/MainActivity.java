package aplications.villadiego.datainformation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name, doc, salary;
    int stratum;
    String education;
    Button reg, list;
    ControllerDB controller;
    Spinner spinnerstr, spnnineredu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doc = findViewById(R.id.txtdoc);
        name = findViewById(R.id.txtname);
        salary = findViewById(R.id.txtsalary);
        spinnerstr = findViewById(R.id.spinnerstr);
        spnnineredu = findViewById(R.id.spinneredu);
        reg = findViewById(R.id.btnreg);
        list = findViewById(R.id.btnlist);

        controller = new ControllerDB(getApplicationContext());


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.stratum,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerstr.setAdapter(adapter);

        spinnerstr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stratum = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.education,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnnineredu.setAdapter(adapter1);

        spnnineredu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                education = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doc.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Error. Verifique los datos ingresados", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(doc.getText().toString().trim(), name.getText().toString(), stratum, Float.parseFloat(salary.getText().toString().trim()), education);
                    if (!controller.searchUser(doc.getText().toString().trim())) {
                        Log.d("Buscar", "No encontrado");
                        long retorno = controller.add(user);
                        if (retorno != -1) {
                            Toast.makeText(v.getContext(), "Usuario agregado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "Usuario no agregado", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("Buscar", "Encontrado");
                        Toast.makeText(getApplicationContext(), "Persona ya esta registrado", Toast.LENGTH_SHORT).show();
                    }
                    limpiar();
                }
            }


        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), List.class);
                startActivity(i);
            }
        });

    }

    private void limpiar() {
        doc.setText("");
        name.setText("");
        salary.setText("");
    }
}
