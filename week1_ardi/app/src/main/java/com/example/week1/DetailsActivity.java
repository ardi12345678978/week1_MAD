package com.example.week1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Model.ListData;

public class DetailsActivity extends AppCompatActivity {

    private TextView details_text_nama, details_text_alamat, details_text_umur;
    private ImageView details_image_edit, details_image_delete, details_image_back;
    ArrayList<ListData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Loading loading = new Loading(DetailsActivity.this);

        initView();

        Intent intent = getIntent();
        list = intent.getParcelableArrayListExtra("arraylist");

        int position = intent.getIntExtra("position", -1);

        details_text_nama.setText(list.get(position).getNama());
        details_text_alamat.setText(list.get(position).getAlamat());
        details_text_umur.setText(list.get(position).getUmur());

        details_image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, Edit.class);
                intent.putExtra("condition", "edit");
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("arraylist", list);
                startActivity(intent);
            }
        });

        details_image_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(DetailsActivity.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Delete " + list.get(position).getNama())
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(position);
                                loading.startLoadingAnimation();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loading.dismissDialog();
                                        Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                                        intent.putParcelableArrayListExtra("arraylist", list);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(DetailsActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                                    }
                                }, 2000);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        details_image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initView() {
        details_text_nama = findViewById(R.id.details_text_nama);
        details_text_alamat = findViewById(R.id.details_text_alamat);
        details_text_umur = findViewById(R.id.details_text_umur);
        details_image_edit = findViewById(R.id.details_image_edit);
        details_image_delete = findViewById(R.id.details_image_delete);
        details_image_back = findViewById(R.id.details_image_back);
    }
}