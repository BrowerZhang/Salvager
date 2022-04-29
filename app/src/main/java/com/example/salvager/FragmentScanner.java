package com.example.salvager;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.salvager.ml.ModelUnquant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FragmentScanner extends Fragment {

    TextView result, confidence;
    ImageView imageView;
    Button picture;
    int imageSize = 224;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragmentscanner_layout,container,false);

        result = rootView.findViewById(R.id.result);
        confidence = rootView.findViewById(R.id.confidence);
        imageView = rootView.findViewById(R.id.imageView);
        picture = rootView.findViewById(R.id.button);

        picture.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    //Request camera permission if we don't have it.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });

        return rootView;

    }


    public void classifyImage(Bitmap image){
        try {
            ModelUnquant model = ModelUnquant.newInstance(getActivity().getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);

            byteBuffer.order(ByteOrder.nativeOrder());
            int [] intValues =  new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            for (int i=0; i < imageSize; i++){
                for (int j=0; j < imageSize; j++){
                    int val = intValues[pixel++]; //RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            ModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();


            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i =0; i < confidences.length; i++){
                if (confidences[i]>maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            String[] classes = {"Water Bottle", "Milk Jug", "Detergent Bottle", "Shopping Bag", "Ketchup Bottle", "Egg Carton",
            "Glass Bottle", "Battery", "Cardboard", "Soup Cans", "Baby Bottle", "Paint", "Scrap Metal"};

            if (maxPos == 0){
                Intent intent = new Intent(getActivity(), ActivityWaterBottle.class);
                startActivity(intent);
            }
            else if (maxPos == 1)
            {
                Intent intent = new Intent(getActivity(), ActivityMilkCarton.class);
                startActivity(intent);
            }
            else if (maxPos == 2){
                Intent intent = new Intent(getActivity(), ActivityDetergentBottles.class);
                startActivity(intent);
            }
            else if (maxPos == 3){
                Intent intent = new Intent(getActivity(), ActivityShoppingBag.class);
                startActivity(intent);
            }
            else if (maxPos == 4){
                Intent intent = new Intent(getActivity(), ActivityKetchupBottle.class);
                startActivity(intent);
            }
            else if (maxPos == 5){
                Intent intent = new Intent(getActivity(), ActivityEggCarton.class);
                startActivity(intent);
            }
            else if (maxPos == 6){
                Intent intent = new Intent(getActivity(), ActivityGlassBottle.class);
                startActivity(intent);
            }
            else if (maxPos == 7){
                Intent intent = new Intent(getActivity(), ActivityBattery.class);
                startActivity(intent);
            }
            else if (maxPos == 8){
                Intent intent = new Intent(getActivity(), ActivityCardboard.class);
                startActivity(intent);
            }
            else if (maxPos == 9){
                Intent intent = new Intent(getActivity(), ActivitySoupCan.class);
                startActivity(intent);
            }
            else if (maxPos == 10){
                Intent intent = new Intent(getActivity(), ActivityBabyBottle.class);
                startActivity(intent);
            }
            else if (maxPos == 11){
                Intent intent = new Intent(getActivity(), ActivityPaint.class);
                startActivity(intent);
            }
            else if (maxPos == 12){
                Intent intent = new Intent(getActivity(), ActivityScrapMetal.class);
                startActivity(intent);
            }

            result.setText(classes[maxPos]);

            String s = "";
            s += String.format("%s: %.1f%%\n", classes[maxPos],confidences[maxPos] *100);
            confidence.setText(s);


            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(image);

            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
