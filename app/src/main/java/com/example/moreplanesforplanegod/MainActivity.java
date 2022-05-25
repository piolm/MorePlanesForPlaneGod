package com.example.moreplanesforplanegod;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    String uri = "";
    FirebaseDatabase database =  FirebaseDatabase.getInstance();
    String image_src;
    String name = null;
    String description = null;
    int enginesAmount = 0;
    int planeLength = 0;
    int crew = 0;
    float wingSize = 0;
    float wingSize1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editTextName = findViewById(R.id.name);
        EditText editTextDescription = findViewById(R.id.description);
        EditText editTextEngines = findViewById(R.id.enginesAmount);
        EditText editTextPlaneLength = findViewById(R.id.planeLength);
        EditText editTextCrew = findViewById(R.id.crew);
        EditText editTextWingSize = findViewById(R.id.wingSize);
        EditText editTextWingSize1 = findViewById(R.id.wingSize1);
        ImageView planeImg = findViewById(R.id.planeImg);
        Button send = findViewById(R.id.sendTheBaby);
        ActivityResultLauncher<String> nyeso =
                registerForActivityResult(new ActivityResultContracts.GetContent(),
                        new ActivityResultCallback<Uri>() {
                            @Override
                            public void onActivityResult(Uri result) {
                                Glide.with(getApplicationContext()).load(result).into(planeImg);
                                uri = result.toString();
                                Log.d("URI_check", uri);
                                try {
                                    InputStream iS = getApplicationContext().getContentResolver().openInputStream(result);
                                    Bitmap bmp;
                                    bmp = BitmapFactory.decodeStream(iS);
                                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
        planeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nyeso.launch("image/*");
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    name = editTextName.getText().toString();
                    description = editTextDescription.getText().toString();
                    enginesAmount = Integer.parseInt(editTextEngines.getText().toString());
                    planeLength = Integer.parseInt(editTextPlaneLength.getText().toString());
                    crew = Integer.parseInt(editTextCrew.getText().toString());
                    wingSize = Float.parseFloat(editTextWingSize.getText().toString());
                    wingSize1 = Float.parseFloat(editTextWingSize1.getText().toString());
                } catch (NumberFormatException  e) {
                    Toast.makeText(getApplicationContext(), "wrong number", Toast.LENGTH_LONG).show();
                    return;
                }
                if (name.equals("")||description.equals("")) {
                    Toast.makeText(getApplicationContext(), "ayo no not filled strings allowed", Toast.LENGTH_LONG).show(); return;}
                StorageReference planeRef = storageRef.child((name+".jpg").replace(" ", "_"));
                UploadTask uploadTask = planeRef.putBytes(byteArrayOutputStream.toByteArray());
                uploadTask.addOnFailureListener(Throwable::printStackTrace);
                planeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        image_src = uri.toString();
                        Plane plane = new Plane(image_src, name, description, enginesAmount, planeLength, crew, wingSize, wingSize1);
                        Log.d("plane_check", plane.toString());
                        Toast.makeText(getApplicationContext(), "sent!", Toast.LENGTH_LONG).show();
                        DatabaseReference mRef =  database.getReference().child(name);
                        mRef.setValue(plane);
                    }
                });
            }
        });
    }
}