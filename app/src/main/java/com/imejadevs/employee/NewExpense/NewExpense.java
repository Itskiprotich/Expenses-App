package com.imejadevs.employee.NewExpense;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.imejadevs.employee.Database.Database;
import com.imejadevs.employee.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewExpense extends AppCompatActivity {
    private Button button;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private Bitmap bitmap;
    String flag_name, Total_cash, date_claimed, date_incurred, vat_component, date_paid, date_added, claim_summary;
    boolean flag_claimed;
    ByteArrayOutputStream stream;
    private EditText Name, Cash, Summary;
    private TextView textView, VAT;
    Date date;
    SimpleDateFormat simpleDateFormat;
    String today;
    private CheckBox checkBox, getCheckBox;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        database = new Database(this);
        button = (Button) findViewById(R.id.photo);
        imageView = (ImageView) findViewById(R.id.image);
        Name = (EditText) findViewById(R.id.aa);
        Cash = (EditText) findViewById(R.id.bb);
        Summary = (EditText) findViewById(R.id.cc);
        textView = (TextView) findViewById(R.id.date);
        VAT = (TextView) findViewById(R.id.vat_view);
        date = Calendar.getInstance().getTime();
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        today = simpleDateFormat.format(date);
        textView.setText(today);
        checkBox = (CheckBox) findViewById(R.id.claim);
        getCheckBox = (CheckBox) findViewById(R.id.vat);
        onclickListners();
    }

    private void onclickListners() {
        getCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (getCheckBox.isChecked()) {
                    VAT.setVisibility(View.VISIBLE);
                    VAT.setText("Vat Charged 20%");
                } else {
                    VAT.setVisibility(View.GONE);
                    VAT.setText("No VAT Charged");
                }
            }
        });
    }

    public void capturePhoto(View view) {
        PopupMenu popupMenu = new PopupMenu(this, button);
        popupMenu.getMenuInflater().inflate(R.menu.photo, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.camera) {
                    capture();

                } else if (id == R.id.gallery) {
                    openGalley();
                }

                return false;
            }
        });

        popupMenu.show();
    }

    private void openGalley() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
    }

    private void capture() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);

        } else {
            if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                    imageView.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public void saveExpense(View view) {
        flag_name = Name.getText().toString();
        Total_cash = Cash.getText().toString();
        claim_summary = Summary.getText().toString();
        flag_claimed = check_if_claimed();
        date_incurred = "N/A";
        date_paid = "N/A";
        date_added = textView.getText().toString();
        vat_component = VAT.getText().toString();

        if (flag_name.isEmpty()) {
            Name.setError("Enter the flag name");
            Name.requestFocus();

        } else if (Total_cash.isEmpty()) {
            Cash.setError("Enter the amount");
            Cash.requestFocus();
        } else if (claim_summary.isEmpty()) {
            Summary.setError("Enter Summary");
            Summary.requestFocus();
        } else {
            if (imageView.getVisibility() == View.VISIBLE) {
                if (flag_claimed == true) {
                    date_claimed = textView.getText().toString();


                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    boolean b = database.addExpese(flag_name, Total_cash, claim_summary, flag_claimed, date_incurred, date_paid, date_added, vat_component, byteArray, "Not Paid", date_claimed);
                    if (b == true) {
                        Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show();
                        clearData();
                    } else {
                        Toast.makeText(this, "Failed..try again!", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    date_claimed = "N/A";//textView.getText().toString();


                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    boolean b = database.addExpese(flag_name, Total_cash, claim_summary, flag_claimed, date_incurred, date_paid, date_added, vat_component, byteArray, "Not Paid", date_claimed);
                    if (b == true) {
                        Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show();
                        clearData();
                    } else {
                        Toast.makeText(this, "Failed..try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (imageView.getVisibility() == View.INVISIBLE) {
                if (flag_claimed == true) {
                    date_claimed = textView.getText().toString();

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.no);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    boolean b = database.addExpese(flag_name, Total_cash, claim_summary, flag_claimed, date_incurred, date_paid, date_added, vat_component, byteArray, "Not Paid", date_claimed);
                    if (b == true) {
                        Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show();
                        clearData();
                    } else {
                        Toast.makeText(this, "Failed..try again!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    date_claimed = "N/A";//textView.getText().toString();

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.no);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    boolean b = database.addExpese(flag_name, Total_cash, claim_summary, flag_claimed, date_incurred, date_paid, date_added, vat_component, byteArray, "Not Paid", date_claimed);
                    if (b == true) {
                        Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show();
                        clearData();
                    } else {
                        Toast.makeText(this, "Failed..try again!", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }

    }

    private void clearData() {
        Name.setText("");
        Cash.setText("");
        Summary.setText("");
    }

    private boolean check_if_claimed() {
        if (checkBox.isChecked()) {

            return true;
        } else {

            return false;
        }
    }

}
