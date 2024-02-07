package gens.global.gensmasterapps.pemilu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droidbond.loadingbutton.LoadingButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import gens.global.gensmasterapps.R;
import gens.global.gensmasterapps.activity.HomeActivity;
import gens.global.gensmasterapps.api.ApiService;
import gens.global.gensmasterapps.api.RetrofitClient;
import gens.global.gensmasterapps.function.MyMessage;
import gens.global.gensmasterapps.function.MySession;
import gens.global.gensmasterapps.model.DefaultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresidenActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView plano,hologram;
    EditText satu,dua,tiga;
    MyMessage myMessage;
    private static final int CAMERA_REQUEST_CODE_PLANO = 100;
    private static final int CAMERA_REQUEST_CODE_HOLO = 101;
    Uri fileUri;
    LinearLayout ln_images;
    ImageView img_plano,img_holo;
    String nama_plano,nama_holo,code;
    MySession mySession;
    ApiService apiService;
    Call<DefaultModel> defaultModelCall;
    LoadingButton save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presiden);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        myMessage = new MyMessage(this);
        mySession = new MySession(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Quick Count Presiden");
        TextView tv=(TextView) toolbar.getChildAt(0);
        tv.setTypeface(getResources().getFont(R.font.poppins));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        code = "presiden"+mySession.getPhone();
        mySession = new MySession(this);
        ln_images = findViewById(R.id.ln_images);
        img_holo = findViewById(R.id.img_holo);
        img_plano = findViewById(R.id.img_plano);
        plano = findViewById(R.id.plano);
        hologram = findViewById(R.id.hologram);
        satu = findViewById(R.id.satu);
        dua = findViewById(R.id.dua);
        tiga = findViewById(R.id.tiga);
        plano.setOnClickListener(V->{
                capturePlano();
        });
        hologram.setOnClickListener(V->{
            captureHolo();
        });
        save = findViewById(R.id.save);
        save.setOnClickListener(V->{
            saveData();
        });
    }
    private void captureHolo() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE_HOLO);
    }
    private void capturePlano() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE_PLANO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE_PLANO && resultCode == RESULT_OK) {
            // Mengambil gambar dari hasil kamera
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ln_images.setVisibility(View.VISIBLE);
            img_plano.setImageBitmap(photo);
            plano.setVisibility(View.GONE);
            assert photo != null;
            nama_plano = getFileName(getImageUriFromBitmap(photo));
            upload(photo,nama_plano,"1");
        }
        if (requestCode == CAMERA_REQUEST_CODE_HOLO && resultCode == RESULT_OK) {
            // Mengambil gambar dari hasil kamera
            Bitmap photo_holo = (Bitmap) data.getExtras().get("data");
            ln_images.setVisibility(View.VISIBLE);
            img_holo.setImageBitmap(photo_holo);
            hologram.setVisibility(View.GONE);
            assert photo_holo != null;
            nama_holo = getFileName(getImageUriFromBitmap(photo_holo));
            upload(photo_holo,nama_holo,"2");
        }
    }
    private Uri getImageUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime now = LocalDateTime.now();
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Jakarta");
            LocalDateTime jakartaTime = LocalDateTime.now(timeZone.toZoneId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            String names = jakartaTime.format(formatter);
            path = MediaStore.Images.Media.insertImage(
                    getContentResolver(),
                    bitmap,
                    names,
                    null
            );
            
        }
        return Uri.parse(path);
    }
    private String getFileName(Uri fileUri) {
        String fileName = "";
        try (Cursor cursor = getContentResolver().query(fileUri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                fileName = cursor.getString(displayNameIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
    private void upload(Bitmap bitmap,String fileName,String type){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        sendFileToServer(fileName,encodedImage,type);
    }
    void sendFileToServer(String fileName,String base64Data,String type){
        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("images", fileName);
            paramObject.put("file",base64Data);
            paramObject.put("petugas",mySession.getPhone().trim());
            paramObject.put("code",code);
            paramObject.put("type",type);
            defaultModelCall = apiService.UploadImagesPresiden(paramObject.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(@NonNull Call<DefaultModel> call, @NonNull Response<DefaultModel> response) {
                    assert response.body() != null;
                    Log.d("UploadImagesPresiden", new Gson().toJson(response.body()));
                }
                @Override
                public void onFailure(@NonNull Call<DefaultModel> call, @NonNull Throwable t) {
                    Log.d("UploadImagesPresiden", Objects.requireNonNull(t.getMessage()));
                }
            });
        } catch (JSONException e) {
            Log.d("UploadImagesPresiden", Objects.requireNonNull(e.getMessage()));
            throw new RuntimeException(e);
        }

    }
    void saveData(){
        save.showLoading();
        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("satu", satu.getText().toString().trim());
            paramObject.put("dua",dua.getText().toString().trim());
            paramObject.put("tiga",tiga.getText().toString().trim());
            paramObject.put("code",code);
            defaultModelCall = apiService.savePresiden(paramObject.toString());
            defaultModelCall.enqueue(new Callback<DefaultModel>() {
                @Override
                public void onResponse(@NonNull Call<DefaultModel> call, @NonNull Response<DefaultModel> response) {
                    assert response.body() != null;
                    Log.d("UploadImagesPresiden", new Gson().toJson(response.body()));
                    if (response.body().getCode().matches("200")){
                        myMessage.Success(response.body().getMessage());
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }else{
                        myMessage.Error(response.body().getMessage());
                    }
                    save.hideLoading();
                }
                @Override
                public void onFailure(@NonNull Call<DefaultModel> call, @NonNull Throwable t) {
                    Log.d("UploadImagesPresiden", Objects.requireNonNull(t.getMessage()));
                }
            });
        } catch (JSONException e) {
            Log.d("UploadImagesPresiden", Objects.requireNonNull(e.getMessage()));
            throw new RuntimeException(e);
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}