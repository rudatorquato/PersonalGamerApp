package com.example.personalgamer;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import adapter.MedicamentosAdapter;
import controller.PresrcricaoController;
import interfaces.OnItemClicked;
import modelo.Medicamento;
import modelo.Paciente;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.document.FirebaseVisionCloudDocumentRecognizerOptions;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.NetworkObserver;
import network.NetworkManager;
import util.Path;
import util.VerifyConnection;

public class MainActivity extends AppCompatActivity implements OnItemClicked, View.OnClickListener {
    private SharedPreferences preferences;
    FirebaseVisionImage image;

    public static final String FILE_NAME = "temp.jpg";
    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;

    private static final int MAX_DIMENSION = 1200;

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mImageDetails, tv_hospital, edt_paciente, edt_leito, edt_prontuario, edt_data;
    private ImageView mMainImage, ic_data;
    private View progressView;

    MedicamentoController medicamentoController = null;
    private MedicamentosAdapter mAdapter;
    ArrayList<Medicamento> medicamentos;
    int index = 0;

    private boolean control = false;
    private int user_id;

    private Context context = this;
    private NetworkManager manager;
    private NetworkObserver networkObserver;

    private CoordinatorLayout coordinatorLayout;

    private Matrix matrix = new Matrix();
    private Float scale = 1f;
    ScaleGestureDetector SGD;
    private StorageReference mStorageRef;
    private StorageTask uploadTask;
    private Uri imgUri, uri_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        loadViews();

//        AmazonS3Client s3Client =   new AmazonS3Client( new BasicAWSCredentials( MY_ACCESS_KEY_ID, MY_SECRET_KEY ) );
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");

        SGD = new ScaleGestureDetector(this, new ScaleListener());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder
                    .setMessage(R.string.dialog_select_prompt)
                    .setPositiveButton(R.string.dialog_select_gallery, (dialog, which) -> startGalleryChooser())
                    .setNegativeButton(R.string.dialog_select_camera, (dialog, which) -> startCamera());
            builder.create().show();
        });

        setDataAdapter();
        setupRecyclerView();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            edt_paciente.setText(bundle.getString("paciente"));
            edt_prontuario.setText(bundle.getString("prontuario"));
            edt_data.setText(bundle.getString("data"));
        }

        ic_data.setOnClickListener(v -> {
            Calendar calendario = Calendar.getInstance();

            int ano = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
                String day, month;
                day = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                month = (monthOfYear + 1 < 10) ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);

                String data_picker = day + "/" + month + "/" + year;
                edt_data.setText(data_picker);
            }, ano, mes, dia);

            dpd.show();
        });
    }

    public void loadViews() {
        mImageDetails = findViewById(R.id.image_details);
        tv_hospital = findViewById(R.id.tv_hospital);
        edt_paciente = findViewById(R.id.edt_paciente);
        edt_leito = findViewById(R.id.edt_leito);
        edt_prontuario = findViewById(R.id.edt_prontuario);
        edt_data = findViewById(R.id.edt_data);
        mMainImage = findViewById(R.id.main_image);
        mMainImage.setOnClickListener(this);
        ic_data = findViewById(R.id.ic_data);

        progressView = findViewById(R.id.login_progress);
        coordinatorLayout = findViewById(R.id.activity_main);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            System.out.println("SCALE 0");
            scale = scale * detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 5f));
            matrix.setScale(scale, scale);
            mMainImage.setImageMatrix(matrix);
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("SCALE");
        SGD.onTouchEvent(event);
        return true;
    }

    private void attemptPrescricao() {


        boolean cancel = false;
        View focusView = null;

        String paciente = edt_paciente.getText().toString();
        String prontuario = edt_prontuario.getText().toString();
        String data = edt_data.getText().toString();

        if (TextUtils.isEmpty(data)) {
            edt_data.setError(getString(R.string.error_field_required));
            focusView = edt_data;
            cancel = true;
        }

        if (TextUtils.isEmpty(prontuario)) {
            edt_prontuario.setError(getString(R.string.error_field_required));
            focusView = edt_prontuario;
            cancel = true;
        }

        if (TextUtils.isEmpty(paciente)) {
            edt_paciente.setError(getString(R.string.error_field_required));
            focusView = edt_paciente;
            cancel = true;
        }

        if (mAdapter.medicamentos.get(0).getMedicamento().isEmpty()) {
            cancel = true;
            Snackbar.make(coordinatorLayout,"Adicione um medicamento", Snackbar.LENGTH_LONG).show();
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            showProgress(true);
            ImageFileUploader();
        }
    }

    private void setDataAdapter() {
        medicamentos =  new ArrayList<>();
        medicamentos.add(new Medicamento());
        mAdapter = new MedicamentosAdapter(medicamentos);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnClick(MainActivity.this);

        medicamentoController = new MedicamentoController(new ControllerActions() {
            @Override
            public void onRightSwiped(int position) {
                super.onRightSwiped(position);
                if (mAdapter.getItemCount() > 1) {
                    mAdapter.medicamentos.remove(position);
                    mAdapter.notifyItemRemoved(position);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onLeftSwiped(int position) {
                super.onLeftSwiped(position);
                if (mAdapter.getItemCount() > 1) {
                    mAdapter.medicamentos.remove(position);
                    mAdapter.notifyItemRemoved(position);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onHourSet(int position, String data) {
                super.onHourSet(position, data);
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(medicamentoController);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    public void startGalleryChooser() {
        if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                    GALLERY_IMAGE_REQUEST);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void startCamera() {
        if (PermissionUtils.requestPermission(
                this,
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                intent.setClipData(ClipData.newRawUri("", photoUri));
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
    }

    public File getCameraFile() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, FILE_NAME);
    }

    public void uploadImage(Uri uri) {
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                Bitmap bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
                                MAX_DIMENSION);
                mMainImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            uploadImage(data.getData());

            Uri imageUri = data.getData();
            imgUri = data.getData();
            if(uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(context, "Carregando imagem..", Toast.LENGTH_SHORT).show();
            } else {
//                ImageFileUploader();
            }
//            textDetect(context, imageUri);
//            showProgress(true);
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            uploadImage(photoUri);
            imgUri = photoUri;
            if(uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(context, "Carregando imagem..", Toast.LENGTH_SHORT).show();
            } else {
//                ImageFileUploader();
            }
//            textDetect(context, photoUri);
//            showProgress(true);
        }
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void ImageFileUploader(){
        manager = new NetworkManager();
        manager.setNetworkObserver(getPrescricaoObserver());

        if(imgUri != null) {
            StorageReference ref = mStorageRef.child(System.currentTimeMillis() + "." + getExtension(imgUri));
            uploadTask = ref.putFile(imgUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Get a URL to the uploaded content
//                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                uri_up = uri;

                                showProgress(false);

                                manager.get(Path.urlGetPaciente2 + edt_prontuario.getText().toString());
                                System.out.println("imageurl: " + uri);
                            }
                        });

                        Toast.makeText(context, "Imagem carregada com sucesso", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        } else {
            showProgress(false);

            manager.get(Path.urlGetPaciente2 + edt_prontuario.getText().toString());
        }
    }

    @SuppressLint("SetTextI18n")
    public void textDetect(Context context, Uri imageUri) {
        try {
            image = FirebaseVisionImage.fromFilePath(context, imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Or, to provide language hints to assist with language detection:
        // See https://cloud.google.com/vision/docs/languages for supported languages
        FirebaseVisionCloudDocumentRecognizerOptions options =
                new FirebaseVisionCloudDocumentRecognizerOptions.Builder()
                        .setLanguageHints(Arrays.asList("pt", "en"))
                        .build();
        FirebaseVisionDocumentTextRecognizer detector = FirebaseVision.getInstance()
                .getCloudDocumentTextRecognizer(options);

        detector.processImage(image)
                .addOnSuccessListener(result -> {
                    // Task completed successfully
                    // ...

                    showProgress(false);

                    if (result != null) {
                        String str[];
                        int contador = 0;

                        String resultText = result.getText();
                        //mImageDetails.setText(resultText);
//                    System.out.println("RESULT: " + resultText);

                        for (FirebaseVisionDocumentText.Block block : result.getBlocks()) {
                            String blockText = block.getText();

                            System.out.println("BlockText: " + blockText);

                            mImageDetails.setText("Edite as informações caso necessário.");

                            if (contador == 0 && Character.isDigit(blockText.charAt(0))) {
                                str = blockText.split(" ");
                                if (str[0].matches("^\\d+\\)")) contador = 13;
                            }

                            if (contador <= 9) {
                                if (blockText.trim().length() > 1) {
                                    if (contador == 0) tv_hospital.setText(blockText);
                                    if (contador == 6) edt_paciente.setText(blockText);
                                    if (contador == 7) edt_leito.setText(blockText.trim());
                                    if (contador == 8) edt_prontuario.setText(blockText.trim());
                                    if (contador == 9) {
                                        String data = blockText.trim();
                                        if (data.contains("-")) {
                                            data = data.replace(" ", "").replace("-", "/");
                                            edt_data.setText(data);
                                        } else {
                                            edt_data.setText(blockText.trim());
                                        }
                                    }
                                    contador++;
                                }
                            } else {
                                str = blockText.split(" ");

                                if (str[0].matches("^\\d+\\)") || contador == 13) {
                                    mAdapter.medicamentos.get(mAdapter.getItemCount() - 1).setMedicamento(blockText.trim());

                                    contador++;
                                    index++;
                                } else if (blockText.startsWith("00")) {
                                    str = blockText.split("-");
                                    for (int i = 0; i < str.length; i++) {
                                        if (str[i].contains("X") || str[i].contains("x")) {
                                            mAdapter.medicamentos.get(mAdapter.getItemCount() - 1)
                                                    .setAprazamento(edt_data.getText().toString(), str[i].trim().substring(0, 2) + ":00", "");
                                        }
                                    }

                                    contador = 13;

                                    medicamentos.add(new Medicamento());
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    contador++;
//                                System.out.println("Anotações: " + blockText);
                                }
                            }

//                        Float blockConfidence = block.getConfidence();
//                        List<RecognizedLanguage> blockRecognizedLanguages = block.getRecognizedLanguages();
//                        Rect blockFrame = block.getBoundingBox();
//                        for (FirebaseVisionDocumentText.Paragraph paragraph: block.getParagraphs()) {
//                            String paragraphText = paragraph.getText();
////                            System.out.println("ParagraphText: " + paragraphText);
//
//                            Float paragraphConfidence = paragraph.getConfidence();
//                            List<RecognizedLanguage> paragraphRecognizedLanguages = paragraph.getRecognizedLanguages();
//                            Rect paragraphFrame = paragraph.getBoundingBox();
//                            for (FirebaseVisionDocumentText.Word word: paragraph.getWords()) {
//                                String wordText = word.getText();
////                                mImageDetails.setText(wordText);
////                                System.out.println("wordText: " + wordText);
//
//                                Float wordConfidence = word.getConfidence();
//                                List<RecognizedLanguage> wordRecognizedLanguages = word.getRecognizedLanguages();
//                                Rect wordFrame = word.getBoundingBox();
//                                for (FirebaseVisionDocumentText.Symbol symbol: word.getSymbols()) {
//                                    String symbolText = symbol.getText();
////                                    mImageDetails.setText(symbolText);
////                                    System.out.println("symbollText: " + symbolText);
//
//                                    Float symbolConfidence = symbol.getConfidence();
//                                    List<RecognizedLanguage> symbolRecognizedLanguages = symbol.getRecognizedLanguages();
//                                    Rect symbolFrame = symbol.getBoundingBox();
//                                }
//                            }
//                        }
                        }
                    } else {
                        Toast.makeText(context, "Prescrição Inválida.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Task failed with an exception
                    // ...
                });
    }

    public void onAddField(View v) {
        medicamentos.add(new Medicamento());
        mAdapter.notifyDataSetChanged();
    }

    public void salvar(View v) {

        attemptPrescricao();

    }

    private NetworkObserver getPrescricaoObserver() {
        if (networkObserver == null) {
            networkObserver = new NetworkObserver() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void doOnPost(String response) {

                    System.out.println("PRESCRIÇÃO CRIADA!: " + response);

//                    {
//                        "prescricao": 7,
//                        "medicamento": "Droga",
//                        "aprazamentos": [
//                            {
//                                "data_programada": "10-10-1010",
//                                "hora_programada": "10:10",
//                                "aplicacao": null,
//                                "status": false
//                            },
//                            {
//                                "data_programada": "10-10-1010",
//                                "hora_programada": "10:10",
//                                "aplicacao": null,
//                                "status": false
//                            }
//                        ],
//                        "anotacoes": "qweqwe"
//                    }

                    if (!control) {
                        createMedicamentos(response);
                        control = true;
                    }
                }

                @Override
                public void doOnGet(String response) {
                    System.out.println("USUARIO: "  + response);
                    Map<String, String> params = new HashMap<>();

                    JSONObject jsonObject;

                    try {
                        jsonObject = new JSONObject(new JSONArray(response).get(0).toString());
                        user_id = jsonObject.getInt("id");
                        params.put("paciente", String.valueOf(user_id));
                        if (imgUri != null)
                            params.put("foto", String.valueOf(uri_up));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("CREATE : " + params.toString());
                    manager.post(params, Path.urlPrescricao);
                }

                @Override
                public void doOnError(String response) {

                    System.out.println("ERRO MAIN: " + response);
                }
            };
        }
        return networkObserver;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createMedicamentos(String response) {
        JSONObject params;

        System.out.println("MEDTAM: " + medicamentos.size());
        for (int i = 0; i < medicamentos.size(); i++) {
            System.out.println(" MED: " + medicamentos.get(i).getMedicamento());
            try {
                params = PresrcricaoController.getMedicamentoJson(medicamentos.get(i), response);
                System.out.println("medicamentos: " + params.toString());
                manager.postArray(params, Path.urlMedicamento);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        startActivity(new Intent(context, PrescricaoActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra("id", user_id));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.removeItem(R.id.search);
        menu.removeItem(R.id.ic_data);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            preferences.edit()
                    .remove("tipo")
                    .remove("token")
                    .remove("is_logged")
                    .remove("id")
                    .apply();

            startActivity(new Intent(context, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.ic_horario:
                Calendar horarioAtual = Calendar.getInstance();
                int hour = horarioAtual.get(Calendar.HOUR_OF_DAY);
                int minute = horarioAtual.get(Calendar.MINUTE);
                TimePickerDialog dialog;
                dialog = new TimePickerDialog(context, (timePicker, selectedHour, selectedMinute) -> {
                    String hora = (selectedHour < 10) ? "0" + selectedHour : String.valueOf(selectedHour);
                    String minutos = (selectedMinute < 10) ? "0" + selectedMinute : String.valueOf(selectedMinute);
//                    medicamentos.get(position).setHorarios(hora + ":" + minutos);
                    medicamentos.get(position).setAprazamento(edt_data.getText().toString(), hora + ":" + minutos, "");

//                    for (int i = 0; i < medicamentos.get(position).getAprazamentos().size(); i++) {
//
//                    }
//                        medicamentos.get(position).setHorarios(medicamentos.get(position).getAprazamentos()
//                                .get(medicamentos.get(position).getAprazamentos().size() - 1).getHora());

                    mAdapter.notifyDataSetChanged();
                }, hour, minute, true);//Yes 24 hour time
                dialog.show();
                break;
            case R.id.ic_delete:
                if (!medicamentos.get(position).getAprazamentos().isEmpty())
                    medicamentos.get(position).getAprazamentos()
                            .remove(medicamentos.get(position).getAprazamentos().size() - 1);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_image) {
            Snackbar.make(coordinatorLayout, "Mostra Imagem", Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}

