package com.example.examenordinario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Inventarios extends AppCompatActivity {

    EditText nombre;
    EditText precio;
    EditText cantidad;
    EditText imagen;
    Button guardar;
    Button eliminar;
    Button update;
    Button resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventarios);
        nombre = (EditText) findViewById(R.id.nombre);
        imagen = (EditText) findViewById(R.id.imagen);
        precio = (EditText) findViewById(R.id.precio);
        cantidad = (EditText) findViewById(R.id.cantidad);
        guardar = (Button) findViewById(R.id.guardar);
        eliminar = (Button) findViewById(R.id.eliminar);
        resultado = (Button) findViewById(R.id.resultados);
        update = (Button) findViewById(R.id.update);

        resultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ventas.class);
                startActivity(intent);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Inventarios.this);
                final View customlayout = getLayoutInflater().inflate(R.layout.dialog_layout, null);
                alert.setCancelable(false);
                EditText nombre = customlayout.findViewById(R.id.nombre);
                EditText precio = customlayout.findViewById(R.id.precio);
                EditText cantidad = customlayout.findViewById(R.id.cantidad);
                EditText imagen = customlayout.findViewById(R.id.imagen);


                alert.setView(customlayout);
                alert.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!nombre.getText().toString().equals("") && !precio.getText().toString().equals("")&&!cantidad.getText().toString().equals("")&&!imagen.getText().toString().equals(""))
                            GuardarRegistro(nombre.getText().toString(),precio.getText().toString(),cantidad.getText().toString(),imagen.getText().toString());
                        else
                            Toast.makeText(getApplicationContext(), "Debes llenar todos los ambos campos", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Toast.makeText(getApplicationContext(), "Opcion Cancelada", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Inventarios.this);
                final View customlayout = getLayoutInflater().inflate(R.layout.idproducto_layout, null);
                alert.setCancelable(false);
                EditText nombre = customlayout.findViewById(R.id.nombre);

                alert.setView(customlayout);
                alert.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                eliminarRegistro(nombre.getText().toString());
                    }
                });
                alert.setNegativeButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Toast.makeText(getApplicationContext(), "Opcion Cancelada", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Inventarios.this);
                final View customlayout = getLayoutInflater().inflate(R.layout.update_layout, null);
                alert.setCancelable(false);
                EditText precio = customlayout.findViewById(R.id.precio);
                EditText cantidad = customlayout.findViewById(R.id.cantidad);
                EditText nombre = customlayout.findViewById(R.id.nombre);

                alert.setView(customlayout);
                alert.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                actualizarRegistro(nombre.getText().toString(),cantidad.getText().toString(),precio.getText().toString());
                    }
                });
                alert.setNegativeButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Toast.makeText(getApplicationContext(), "Opcion Cancelada", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

            }
        });
    }

    private void actualizarRegistro(String nombres, String canidades, String precios) {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(this ,"OrdinarioBD", null, 1);
        SQLiteDatabase basedatos= admin.getReadableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombres);
        registro.put("cantidad", canidades);
        registro.put("precio", precios);
        basedatos.update("productos",registro,"nombre='"+nombres+"'", null);
        basedatos.update("productos",registro,"cantidad='"+canidades+"'", null);
        basedatos.update("productos",registro,"precio='"+precios+"'", null);
        basedatos.close();
        Intent intent = new Intent(getApplicationContext(), Inventarios.class);
        startActivity(intent);
        cantidad.setText("");
        precio.setText("");
        nombre.setText("");
    }

    private void eliminarRegistro(String nombres) {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(this, "OrdinarioBD", null, 1);
        SQLiteDatabase basedatos = admin.getReadableDatabase();
        basedatos.delete( "productos", "nombre='"+nombres+"'", null);
        basedatos.close();
        Toast.makeText(getApplicationContext(), "Registro Eliminado Con Exito!! ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Inventarios.class);
        startActivity(intent);
        nombre.setText("");

    }




    private void GuardarRegistro(String nombres, String precios, String cantidades, String imagenes) {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(getApplicationContext(), "OrdinarioBD", null, 1);
        SQLiteDatabase basedatos= admin.getReadableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombres);
        registro.put("precio", precios);
        registro.put("cantidad", cantidades);
        registro.put("imagen", imagenes);
        basedatos.insert("productos", null, registro);
        basedatos.close();
        Toast.makeText(getApplicationContext(), "Registro Insertado con exito", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Inventarios.class);
        startActivity(intent);
        nombre.setText("");
        precio.setText("");
        cantidad.setText("");
        imagen.setText("");
    }
}



