package com.imejadevs.employee;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.imejadevs.employee.Database.Database;
import com.imejadevs.employee.Model.Expenses;

import com.imejadevs.employee.NewExpense.NewExpense;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String[] claim = {"Select", "Paid", "Not Paid"};
    private Spinner spinner;
    ArrayAdapter<String> claims;
    FloatingActionButton floatingActionButton;
    private ListView listView;
    Database database;
    Cursor cursor;
    ArrayList<Expenses> arrayList;
    MyAdapter myAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        loadExpenses();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadExpenses();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database(this);
        arrayList = new ArrayList<>();
        spinner = (Spinner) findViewById(R.id.spinner);
        claims = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, claim);
        spinner.setAdapter(claims);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.list);
        myAdapter = new MyAdapter(this, arrayList);
        listView.setAdapter(myAdapter);
        loadExpenses();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterData(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void filterData(String s) {
        arrayList.clear();
        if (s.equalsIgnoreCase("Select")) {
            loadExpenses();
        } else {
            cursor = database.searchExpenses();
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Expenses expenses = new Expenses();
                    String check = cursor.getString(9);
                    if (s.equalsIgnoreCase(check)) {

                        expenses.setFlag_name(cursor.getString(1));
                        expenses.setTotal_cash(cursor.getString(2));
                        expenses.setVat_component(cursor.getString(3));
                        expenses.setDate_incurred(cursor.getString(4));
                        expenses.setDate_added(cursor.getString(5));
                        expenses.setDate_paid(cursor.getString(6));
                        expenses.setClaim_summary(cursor.getString(7));
                        expenses.setFlag_claimed(cursor.getString(8));
                        expenses.setPayment_status(cursor.getString(9));
                        expenses.setDate_claimed(cursor.getString(10));
                        expenses.setByteArray(cursor.getBlob(11));
                        arrayList.add(expenses);
                        myAdapter.notifyDataSetChanged();
                    }

                }
            }
        }

    }
    private void loadExpenses() {
        arrayList.clear();
        cursor = database.searchExpenses();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                Expenses expenses = new Expenses();
                expenses.setFlag_name(cursor.getString(1));
                expenses.setTotal_cash(cursor.getString(2));
                expenses.setVat_component(cursor.getString(3));
                expenses.setDate_incurred(cursor.getString(4));
                expenses.setDate_added(cursor.getString(5));
                expenses.setDate_paid(cursor.getString(6));
                expenses.setClaim_summary(cursor.getString(7));
                expenses.setFlag_claimed(cursor.getString(8));
                expenses.setPayment_status(cursor.getString(9));
                expenses.setDate_claimed(cursor.getString(10));
                expenses.setByteArray(cursor.getBlob(11));
                arrayList.add(expenses);
                myAdapter.notifyDataSetChanged();

            }
        }
    }
    public void AddExpense(View view) {
        startActivity(new Intent(MainActivity.this, NewExpense.class));

    }
    private class MyAdapter extends ArrayAdapter {
        ArrayList<Expenses> status;
        Database database;

        public MyAdapter(Context context, ArrayList<Expenses> status) {
            super(context, R.layout.expenses, R.id.aa, status);
            this.status = status;
            database = new Database(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.expenses, parent, false);
            final TextView aa = (TextView) view.findViewById(R.id.aa);
            TextView bb = (TextView) view.findViewById(R.id.bb);
            TextView cc = (TextView) view.findViewById(R.id.cc);
            TextView dd = (TextView) view.findViewById(R.id.dd);
            TextView ee = (TextView) view.findViewById(R.id.ee);
            TextView ff = (TextView) view.findViewById(R.id.ff);
            TextView gg = (TextView) view.findViewById(R.id.gg);
            TextView hh = (TextView) view.findViewById(R.id.hh);
            TextView date = (TextView) view.findViewById(R.id.ii);
            ImageView ii = (ImageView) view.findViewById(R.id.image);
            Button del = (Button) view.findViewById(R.id.delete);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.vat);
            Button pay = (Button) view.findViewById(R.id.pay);
            Expenses expenses = status.get(position);
            aa.setText(expenses.getFlag_name());
            bb.setText(expenses.getTotal_cash());
            cc.setText(expenses.getVat_component());
            dd.setText(expenses.getClaim_summary());
            ee.setText(expenses.getPayment_status());
            ff.setText("Date Added:" + expenses.getDate_added());
            String claimed = expenses.getFlag_claimed();
            if (claimed.equalsIgnoreCase("1")) {
                gg.setText("Flag Claimed");
                checkBox.setVisibility(View.INVISIBLE);
            } else if (claimed.equalsIgnoreCase("0")) {
                gg.setText("Not yet Claimed");
                checkBox.setVisibility(View.VISIBLE);
            }
            hh.setText("Date Paid:" + expenses.getDate_paid());
            date.setText("Date Claimed:" + expenses.getDate_claimed());
            byte[] img = expenses.getByteArray();
            ii.setImageBitmap(BitmapFactory.decodeByteArray(img, 0, img.length));

            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteExpense(aa.getText().toString());
                }
            });
            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payExpenses(aa.getText().toString());
                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkBox.isChecked()) {
                        updateClaim(aa.getText().toString());
                    }
                }
            });
            return view;
        }

        private void payExpenses(String s) {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = new Date();
            String update = dateFormat.format(date);
            boolean b = database.payExpense(update, "Paid", s);
            if (b == true) {
                Toast.makeText(getContext(), "Payment Approved", Toast.LENGTH_SHORT).show();
                loadExpenses();
            } else {
                Toast.makeText(getContext(), "Failed, try again!!", Toast.LENGTH_SHORT).show();
            }
        }

        private void deleteExpense(final String s) {
            AlertDialog.Builder al = new AlertDialog.Builder(getContext());
            al.setTitle("Delete");
            al.setMessage("Are you sure you want to delete?");
            al.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    boolean b = database.delete(s);
                    if (b == true) {
                        Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                        loadExpenses();
                    } else {
                        Toast.makeText(getContext(), "Failed, try again!!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            al.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = al.create();
            alertDialog.show();

        }
    }
    private void updateClaim(String s) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String today = simpleDateFormat.format(date);
        boolean b=database.claimNow(today,"1",s);
        if (b==true){
            Toast.makeText(this, "Claim Updated", Toast.LENGTH_SHORT).show();
            loadExpenses();
        }else {
            Toast.makeText(this, "Failed..Try again", Toast.LENGTH_SHORT).show();
        }
    }

}
