package com.andres.pasaportecafeovalle.view.fragment.admin.sucursales;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.view.modelo.SucursalModel;
import com.andres.pasaportecafeovalle.viewmodel.SucursalViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentAdminEditarSucursal extends Fragment {

    private SucursalViewModel sucursalViewModel;

    // Campos del formulario
    private EditText etNombre, etDireccion, etLatitud, etLongitud;
    private Spinner spEstado;
    private TextView tvHorarioLunVieApertura, tvHorarioLunVieCierre;
    private Button btnGuardar; // Renombrado de btnCrear para claridad
    private FloatingActionButton fabVolver;

    // Variables para almacenar datos
    private int sucursalId = -1;
    private int lunVieAperturaHora = -1, lunVieAperturaMinuto = -1;
    private int lunVieCierreHora = -1, lunVieCierreMinuto = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_crear_sucursal, container, false);

        sucursalViewModel = new ViewModelProvider(this).get(SucursalViewModel.class);

        // Inicializar vistas
        etNombre = view.findViewById(R.id.etNombre);
        etDireccion = view.findViewById(R.id.etDireccion);
        etLatitud = view.findViewById(R.id.etLatitud);
        etLongitud = view.findViewById(R.id.etLongitud);
        spEstado = view.findViewById(R.id.spEstado); // <-- Usando Spinner
        tvHorarioLunVieApertura = view.findViewById(R.id.tvHorarioLunVieApertura); // <-- Usando TextView
        tvHorarioLunVieCierre = view.findViewById(R.id.tvHorarioLunVieCierre);   // <-- Usando TextView
        btnGuardar = view.findViewById(R.id.btnCrear); // ID del botón en el XML
        fabVolver = view.findViewById(R.id.fabVolver);

        configurarSpinnerEstado();
        configurarSelectoresHorario();

        btnGuardar.setText("Guardar Cambios");

        cargarDatosSucursal();

        fabVolver.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        btnGuardar.setOnClickListener(v -> {
            if (validarCampos()) {
                guardarCambios();
            }
        });

        return view;
    }

    private void configurarSpinnerEstado() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.estado_sucursal,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(adapter);
    }

    private void configurarSelectoresHorario() {
        tvHorarioLunVieApertura.setOnClickListener(v -> showTimePicker(true));
        tvHorarioLunVieCierre.setOnClickListener(v -> showTimePicker(false));
    }

    private void cargarDatosSucursal() {
        Bundle args = getArguments();
        if (args != null) {
            sucursalId = args.getInt("sucursal_id", -1);
            etNombre.setText(args.getString("sucursal_nombre", ""));
            etDireccion.setText(args.getString("sucursal_direccion", ""));
            // Es mejor pasar Double como Double, y convertirlo a string aquí.
            etLatitud.setText(String.valueOf(args.getDouble("sucursal_latitud", 0.0)));
            etLongitud.setText(String.valueOf(args.getDouble("sucursal_longitud", 0.0)));

            String horarioGuardado = args.getString("sucursal_horario", "");
            parsearYEstablecerHorario(horarioGuardado);

            String estadoGuardado = args.getString("sucursal_estado", "");
            establecerEstadoEnSpinner(estadoGuardado);
        }
    }

    private void guardarCambios() {
        if (sucursalId == -1) {
            Toast.makeText(getContext(), "Error: No se pudo identificar la sucursal.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Construir la cadena de horario a partir de las horas seleccionadas
        String horarioFinal;
        if (lunVieAperturaHora != -1 && lunVieCierreHora != -1) {
            horarioFinal = "L-V: " + formatTime(lunVieAperturaHora, lunVieAperturaMinuto) +
                    " - " + formatTime(lunVieCierreHora, lunVieCierreMinuto);
        } else {
            Toast.makeText(getContext(), "Horario incompleto.", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitud = Double.parseDouble(etLatitud.getText().toString());
        double longitud = Double.parseDouble(etLongitud.getText().toString());

        // Crear el modelo con el ID para la actualización
        SucursalModel sucursalActualizada = new SucursalModel(
                sucursalId,
                etNombre.getText().toString().trim(),
                etDireccion.getText().toString().trim(),
                latitud,
                longitud,
                horarioFinal,
                spEstado.getSelectedItem().toString()
        );

        // Llamar al método del ViewModel para editar
        try {
            sucursalViewModel.editarSucursal(sucursalActualizada);
            Toast.makeText(getContext(), "Sucursal actualizada", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack(); // Volver a la lista
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al actualizar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("EditarSucursal", "Error al guardar cambios", e);
        }
    }

    private boolean validarCampos() {
        // Validaciones de EditText (Nombre, Dirección, Lat/Lon)
        if (etNombre.getText().toString().trim().isEmpty()) {
            etNombre.setError("El nombre es requerido");
            return false;
        }
        if (etDireccion.getText().toString().trim().isEmpty()) {
            etDireccion.setError("La dirección es requerida");
            return false;
        }
        try {
            double lat = Double.parseDouble(etLatitud.getText().toString());
            if (lat < -90 || lat > 90) {
                etLatitud.setError("Latitud inválida");
                return false;
            }
        } catch (NumberFormatException e) {
            etLatitud.setError("Latitud debe ser un número");
            return false;
        }
        try {
            double lon = Double.parseDouble(etLongitud.getText().toString());
            if (lon < -180 || lon > 180) {
                etLongitud.setError("Longitud inválida");
                return false;
            }
        } catch (NumberFormatException e) {
            etLongitud.setError("Longitud debe ser un número");
            return false;
        }

        // Validar que se haya seleccionado un horario
        if (lunVieAperturaHora == -1 || lunVieCierreHora == -1) {
            Toast.makeText(getContext(), "Por favor, seleccione un horario de apertura y cierre", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // --- Métodos Helper para Horario y Estado ---

    private void showTimePicker(boolean isApertura) {
        // Lógica para mostrar TimePickerDialog (similar a CrearSucursal)
        final Calendar c = Calendar.getInstance();
        int initialHour, initialMinute;

        if (isApertura && lunVieAperturaHora != -1) {
            initialHour = lunVieAperturaHora;
            initialMinute = lunVieAperturaMinuto;
        } else if (!isApertura && lunVieCierreHora != -1) {
            initialHour = lunVieCierreHora;
            initialMinute = lunVieCierreMinuto;
        } else {
            initialHour = c.get(Calendar.HOUR_OF_DAY);
            initialMinute = c.get(Calendar.MINUTE);
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {
            String tiempoFormateado = formatTime(hourOfDay, minute);
            if (isApertura) {
                lunVieAperturaHora = hourOfDay;
                lunVieAperturaMinuto = minute;
                tvHorarioLunVieApertura.setText(tiempoFormateado);
            } else {
                lunVieCierreHora = hourOfDay;
                lunVieCierreMinuto = minute;
                tvHorarioLunVieCierre.setText(tiempoFormateado);
            }
        }, initialHour, initialMinute, DateFormat.is24HourFormat(getContext()));
        timePickerDialog.show();
    }

    private String formatTime(int hourOfDay, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        return new SimpleDateFormat("h:mm a", Locale.getDefault()).format(cal.getTime());
    }

    private void parsearYEstablecerHorario(String horarioGuardado) {
        // Lógica para parsear "L-V: 9:00 AM - 5:00 PM"
        Pattern pattern = Pattern.compile("L-V:\\s*(.*?)\\s*-\\s*(.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(horarioGuardado);

        if (matcher.find()) {
            String aperturaStr = matcher.group(1).trim();
            String cierreStr = matcher.group(2).trim();
            establecerTiempoDesdeString(aperturaStr, true);
            establecerTiempoDesdeString(cierreStr, false);
        } else {
            Log.w("EditarSucursal", "Formato de horario no reconocido: " + horarioGuardado);
        }
    }

    private void establecerTiempoDesdeString(String tiempoStr, boolean isApertura) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(tiempoStr));
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);

            if (isApertura) {
                lunVieAperturaHora = hour;
                lunVieAperturaMinuto = minute;
                tvHorarioLunVieApertura.setText(formatTime(hour, minute));
            } else {
                lunVieCierreHora = hour;
                lunVieCierreMinuto = minute;
                tvHorarioLunVieCierre.setText(formatTime(hour, minute));
            }
        } catch (ParseException e) {
            Log.e("EditarSucursal", "Error parseando tiempo: " + tiempoStr, e);
        }
    }

    private void establecerEstadoEnSpinner(String estado) {
        // Lógica para seleccionar el ítem correcto en el Spinner (idéntica a EditarCliente)
        if (spEstado.getAdapter() == null) return;
        for (int i = 0; i < spEstado.getAdapter().getCount(); i++) {
            if (spEstado.getAdapter().getItem(i).toString().equalsIgnoreCase(estado)) {
                spEstado.setSelection(i);
                break;
            }
        }
    }
}
