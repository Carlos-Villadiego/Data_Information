package aplications.villadiego.datainformation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ControllerDB {
    private Database data;
    public ControllerDB(Context context) {
        this.data = new Database(context, DBCreator.TABLE_USER, null, 1);
    }
    public long add(User user) {
        try {
            SQLiteDatabase database = data.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBCreator.DOCUMENT, user.getDoc());
            values.put(DBCreator.NAME, user.getName());
            values.put(DBCreator.STRATUM, user.getStratum());
            values.put(DBCreator.SALARY, user.getSalary());
            values.put(DBCreator.EDUCATION, user.getEdu());
            return database.insert(DBCreator.TABLE_USER, null, values);
        } catch (Exception e) {
            return -1L;
        }
    }

    public int update(User user) {
        try {
            SQLiteDatabase database = data.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBCreator.NAME, user.getName());
            values.put(DBCreator.DOCUMENT, user.getDoc());
            values.put(DBCreator.STRATUM, user.getStratum());
            values.put(DBCreator.SALARY, user.getSalary());
            values.put(DBCreator.EDUCATION, user.getEdu());

            String campoParaActualizar = DBCreator.DOCUMENT + " = ?";
            String[] argumentosParaActualizar = {String.valueOf(user.getDoc())};

            return database.update(DBCreator.TABLE_USER, values, campoParaActualizar, argumentosParaActualizar);
        } catch (Exception e) {
            return 0;
        }
    }

    public int delete(User user) {
        try {
            SQLiteDatabase database = data.getWritableDatabase();
            String[] args = {String.valueOf(user.getDoc())};
            return database.delete(DBCreator.TABLE_USER, DBCreator.DOCUMENT + " = ?", args);
        } catch (Exception e) {
            return 0;
        }
    }

    public ArrayList<User> getUser() {
        ArrayList<User> users = new ArrayList<>();

        SQLiteDatabase database = data.getReadableDatabase();

        String[] columnasConsultar = {DBCreator.DOCUMENT, DBCreator.NAME, DBCreator.STRATUM
                , DBCreator.SALARY, DBCreator.EDUCATION};

        Cursor cursor = database.query(DBCreator.TABLE_USER, columnasConsultar
                , null, null, null, null, null);

        if (cursor == null) {
            return users;
        }

        if (!cursor.moveToFirst()) {
            return users;
        }

        do {

            User user = new User(cursor.getString(0), cursor.getString(1)
                    , cursor.getInt(2), cursor.getFloat(3), cursor.getString(4));
            users.add(user);
        } while (cursor.moveToNext());

        cursor.close();
        return users;
    }
    public boolean searchUser(String ced){
        String[] args = new String[] {ced};
        SQLiteDatabase database = data.getReadableDatabase();
        Cursor c = database.query(DBCreator.TABLE_USER, null, "document=?", args, null, null, null);
        if (c.getCount()>0) {
            database.close();
            return true;
        }
        else{
            database.close();
            return false;}

    }


}
