package com.anasov.cpmnuker;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Util {

    public static boolean isPackageExists(Context ctx, String targetPackage){
        // TODO: add support for rooted devices
        String files = ctx.getFilesDir().getAbsolutePath();
        String root = files.substring(0, files.indexOf(ctx.getPackageName()));
        boolean found = false;
        File[] dirs = new File(root).listFiles();
        if (dirs != null) {
            for (File dir : dirs) {
                if (dir.isDirectory() && dir.getName().equals(targetPackage)) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    public static void copyData(Context ctx){
        String files = ctx.getFilesDir().getAbsolutePath();
        String root = files.substring(0, files.indexOf(ctx.getPackageName()));
        String output_path = root + "com.olzhas.carparking.multyplayer/shared_prefs";

        if(!(new File(output_path)).exists()){
            (new File(output_path)).mkdir();
        }

        InputStream input = null;
        OutputStream output = null;

        try {
            input = ctx.getResources().openRawResource(R.raw.data);
            File outFile = new File(output_path, "com.olzhas.carparking.multyplayer.v2.playerprefs.xml");
            output = new FileOutputStream(outFile);
            copyFile(input, output);
            Toast.makeText(ctx, R.string.hack_success, Toast.LENGTH_SHORT).show();
        } catch (IOException ignored) {
            Toast.makeText(ctx, R.string.hack_fail, Toast.LENGTH_SHORT).show();
        }
    }

    public static void copyFile(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = input.read(buffer)) != -1){
            output.write(buffer, 0, read);
        }
    }

}
