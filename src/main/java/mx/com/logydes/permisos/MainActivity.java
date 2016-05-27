package mx.com.logydes.permisos;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CODIGO_SOLICITUD_PERMISO = 1;
    private static final int CODIGO_SOLICITUD_HABILITAR_BLUETOOTH = 0;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        activity = this;

    }

    public void habilitarBluetooth(View v){
        solicitarPermiso();

        BluetoothAdapter  mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null){
            Toast.makeText(MainActivity.this, "Tu dispositivo no tiene Bluetooth.", Toast.LENGTH_SHORT).show();
        }

        if (!mBluetoothAdapter.isEnabled()){
            Intent hablilitarBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(hablilitarBluetoothIntent,CODIGO_SOLICITUD_HABILITAR_BLUETOOTH);
        }

    }

    public boolean checarStatusPermiso(){
        int res = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
        if (res == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    public void solicitarPermiso(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH)){
            Toast.makeText(MainActivity.this, "El Permiso ya fue activdado, si deseas desactivarlo puedes ir a los ajuste de la app", Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.BLUETOOTH,
                            Manifest.permission.CAMERA},
                    CODIGO_SOLICITUD_PERMISO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CODIGO_SOLICITUD_PERMISO:
                if (checarStatusPermiso()) {
                    Toast.makeText(MainActivity.this, "Ya fue otorgado el permiso para Bluetooth.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "No ha sido otorgado el permiso para Bluetooth.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
