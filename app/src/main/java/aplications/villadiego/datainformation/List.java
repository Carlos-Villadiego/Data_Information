package aplications.villadiego.datainformation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressLint("Registered")
public class List extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ControllerDB controllerDB;
    ListView list;
    ArrayList<User> listaUsers;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        controllerDB = new ControllerDB(getApplicationContext());
        list = findViewById(R.id.listusers);
        listaUsers = controllerDB.getUser();
        adapter = new UserAdapter(getApplicationContext(), R.layout.activity_details, listaUsers);
        list.setAdapter(adapter);
        registerForContextMenu(list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<User> listaPersons = controllerDB.getUser();
                UserAdapter adaptador = new UserAdapter(getApplicationContext(), R.layout.activity_details, listaPersons);
                list.setAdapter(adaptador);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Actualizacion cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.mnupdate:
                Update(menuInfo.position);
                return true;
            case R.id.mndelete:
                Deleta(menuInfo.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void Update(int pos) {
        Intent intent = new Intent(this, Update.class);
        intent.putExtra("indice", pos);
        startActivityForResult(intent, 2);
    }

    private void Deleta(int pos) {
        int retorno = controllerDB.delete(listaUsers.get(pos));
        if (retorno == 1) {
            listaUsers = controllerDB.getUser();
            adapter = new UserAdapter(getApplicationContext(), R.layout.activity_details, listaUsers);
            list.setAdapter(adapter);
            Toast.makeText(getApplicationContext(), "Registro eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error al borrar", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item= menu.findItem(R.id.mnsearch);
        @SuppressWarnings("deprecation") SearchView searchView=(SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        //noinspection deprecation
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adapter.setFilter(listaUsers);
                return true;
            }
        });
        return true;
    }


    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    public boolean onQueryTextChange(String newText) {
        try {
            ArrayList<User>listafiltrada=filter(listaUsers,newText);
            adapter.setFilter(listafiltrada);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private ArrayList<User> filter(ArrayList<User> users, String text){
        ArrayList<User>filter=new ArrayList<>();
        try{
            text = text.toLowerCase().trim();
            for(User user : users){
                String cod=user.getDoc();
                if (cod.contains(text)){
                    filter.add(user);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return filter;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
