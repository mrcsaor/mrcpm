package com.anasov.cpmnuker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fabHelp = findViewById(R.id.fabHelp);
        Button btnHack = findViewById(R.id.btnHack);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermission();
        }
        fabHelp.setOnClickListener(view -> {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialog_help);
            dialog.findViewById(R.id.btnHelpDialogOk).setOnClickListener(view1 -> dialog.dismiss());
            dialog.findViewById(R.id.btnHelpDialogDeveloper).setOnClickListener(view2 -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/anasybal")));
                dialog.dismiss();
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.show();
        });
        btnHack.setOnClickListener(view -> {
            if(Util.isPackageExists(MainActivity.this, "com.olzhas.carparking.multyplayer")){
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_alert);

                TextView title =  dialog.findViewById(R.id.dialog_title);
                title.setText(R.string.warning_title);

                TextView description =  dialog.findViewById(R.id.dialog_description);
                description.setText(R.string.warning_description);

                Button button_cancel = dialog.findViewById(R.id.dialog_button_cancel);
                button_cancel.setText(R.string.no);
                button_cancel.setOnClickListener(view1 -> {
                    dialog.dismiss();
                });

                Button button_ok = dialog.findViewById(R.id.dialog_button_ok);
                button_ok.setText(R.string.yes);
                button_ok.setOnClickListener(view2 -> {
                    dialog.dismiss();
                    Util.copyData(MainActivity.this);
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.show();
            } else {
                Toast.makeText(MainActivity.this, "CPM Most be installed first !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_alert);

            TextView title =  dialog.findViewById(R.id.dialog_title);
            title.setText(R.string.permission_needed);

            TextView description =  dialog.findViewById(R.id.dialog_description);
            description.setText(R.string.storage_permission_message);

            Button button_cancel = dialog.findViewById(R.id.dialog_button_cancel);
            button_cancel.setOnClickListener(view -> {
                dialog.dismiss();
                onPermissionDenied();
            });

            Button button_ok = dialog.findViewById(R.id.dialog_button_ok);
            button_ok.setOnClickListener(view -> {
                dialog.dismiss();
                ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, STORAGE_PERMISSION_CODE);
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show();
            } else {
                onPermissionDenied();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void onPermissionDenied(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_alert);

        TextView title =  dialog.findViewById(R.id.dialog_title);
        title.setText(R.string.permission_denied_title);

        TextView description =  dialog.findViewById(R.id.dialog_description);
        description.setText(R.string.permission_denied_message);

        dialog.findViewById(R.id.dialog_button_cancel).setVisibility(View.GONE);
        Button button_ok = dialog.findViewById(R.id.dialog_button_ok);
        button_ok.setOnClickListener(view -> finishAffinity());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.show();
    }
}
