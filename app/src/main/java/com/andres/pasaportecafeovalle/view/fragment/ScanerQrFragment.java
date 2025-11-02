package com.andres.pasaportecafeovalle.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.andres.pasaportecafeovalle.R;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScanerQrFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private PreviewView previewView; //para mostrar el contenido de la camara
    private ExecutorService camaraExecutor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scaner_qr, container, false);

        previewView = view.findViewById(R.id.previewView);
        camaraExecutor = Executors.newSingleThreadExecutor();

        view.post(() -> checkPermisosCamara());
        return view;
    }

    private void checkPermisosCamara() {//verificamos si tenemos permiso para usar la camara
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);

        } else {
            iniciarCamara();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, //si tiene permiso iniciamos la camara, si no mostramos un mensaje indicando que no se puede usar la camara
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarCamara();
            } else {
                Toast.makeText(requireContext(), "Se necesita el permiso de cámara para escanear el QR", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void iniciarCamara() {

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = //administra el ciclo de vida de la camara
                ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build(); //cargamos la camara en la vista
                preview.setSurfaceProvider(previewView.getSurfaceProvider()); //conectamos la vista con el layout

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder() //permitimos recibir los frames de la camara
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(camaraExecutor, image -> {// corremos el analizador en un hilo aparte
                    @SuppressLint("UnsafeOptInUsageError")
                    Image mediaImage = image.getImage(); //frame actual detectado
                    if (mediaImage != null) {

                        // Convertir el frame a BinaryBitmap para ZXing
                        ByteBuffer buffer = mediaImage.getPlanes()[0].getBuffer();
                        byte[] data = new byte[buffer.remaining()];
                        buffer.get(data);

                        PlanarYUVLuminanceSource mapa = new PlanarYUVLuminanceSource( //convvertimos cada bit en blanco o negro
                                data,
                                mediaImage.getWidth(),
                                mediaImage.getHeight(),
                                0, 0,
                                mediaImage.getWidth(),
                                mediaImage.getHeight(),
                                false
                        );

                        //reconoce el qr
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(mapa));
                        MultiFormatReader reader = new MultiFormatReader(); //almacenamos lo que encuentre

                        try { //la acccion al encontrar el qr
                            Result result = reader.decode(bitmap); //guardamos el hash del qr detectado
                            requireActivity().runOnUiThread(() -> {//como estabamos en hilo secundario volvemos al principal
                                Toast.makeText(requireContext(), "QR detectado: " + result.getText(), Toast.LENGTH_LONG).show(); //po rahora solo mostramos el hash del qr
                                imageAnalysis.clearAnalyzer();
                                // Aquí  se validara el hash con la base de datos
                            });
                        } catch (Exception e) {
                            //implementar la excepcion
                        } finally {
                            image.close();
                        }
                    }
                });
                //vinculamos la camara trasera
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();


                cameraProvider.unbindAll(); //limpia cualquier uso previo de la camara
                cameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, preview, imageAnalysis); //vinculamos el ciclo de vida de la camara con el fragment
                //cuando el fragment se destruya la camara se apaga

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (camaraExecutor != null) {
            camaraExecutor.shutdown();
        }
    }

}

