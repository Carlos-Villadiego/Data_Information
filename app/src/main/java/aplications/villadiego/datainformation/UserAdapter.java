package aplications.villadiego.datainformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    private ArrayList<User> list;
    private Context context;
    private int anInt;

    public UserAdapter(Context context, int anInt, ArrayList<User> list) {
        this.list = list;
        this.context = context;
        this.anInt = anInt;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(anInt, null);
        }

        User user = list.get(position);


        TextView doc=view.findViewById(R.id.txtdoc);
        TextView name=view.findViewById(R.id.txtname);
        TextView str=view.findViewById(R.id.txtstratum);
        TextView salary=view.findViewById(R.id.txtsalary);
        TextView edu=view.findViewById(R.id.txtedu);



        doc.setText(user.getDoc());
        name.setText(user.getName());
        str.setText(String.valueOf(user.getStratum()));
        salary.setText(String.valueOf(user.getSalary()));
        edu.setText(user.getEdu());

        return view;
    }

    public void setFilter(ArrayList<User> listapersonas){
        this.list =new ArrayList<>();
        this.list.addAll(listapersonas);
        notifyDataSetChanged();
    }
}
