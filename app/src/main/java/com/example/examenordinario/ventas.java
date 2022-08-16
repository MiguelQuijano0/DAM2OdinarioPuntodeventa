package com.example.examenordinario;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ventas extends AppCompatActivity {
    private TextView contenidobase;
    private Button leerbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        contenidobase = (TextView) findViewById(R.id.contenidob);
        leerbase = (Button) findViewById(R.id.leerb);


        leerbase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeerRegistro();
            }
        });

    }
    private void LeerRegistro() {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(this, "OrdinarioBD", null, 1);
        SQLiteDatabase basedatos = admin.getReadableDatabase();
        try{
            Cursor cursor = basedatos.rawQuery("SELECT * FROM productos", null);
            String cont="";
            while (cursor.moveToNext()){
                cont+="Nombre :"+cursor.getString(1)+"\nPrecio:"+cursor.getString(2)+"\nCantidad:"+cursor.getString(3)+"\nImagen:"+cursor.getString(4)+"\n______________________\n";
            }
            cursor.close();
            contenidobase.setText(cont);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}