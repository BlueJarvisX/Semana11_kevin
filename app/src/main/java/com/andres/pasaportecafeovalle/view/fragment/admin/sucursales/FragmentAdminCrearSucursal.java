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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.view.modelo.SucursalModel;
import com.andres.pasaportecafeovalle.viewmodel.SucursalViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FragmentAdminCrearSucursal extends Fragment {

        private SucursalViewModel sucursalViewModel;
        // No es necesario tener una variable 'sucursal' a nivel de clase si solo se crea localmente
        // private SucursalModel sucursal;
        EditText etNombre, etDireccion, etLatitud, etLongitud;
        Spinner spEstado;
        TextView tvHorarioLunVieApertura, tvHorarioLunVieCierre;
        private int lunVieAperturaHora = -1, lunVieAperturaMinuto = -1;
        private int lunVieCierreHora = -1, lunVieCierreMinuto = -1;
        private FloatingActionButton fabVolver;
        Button btnCrear;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_admin_crear_sucursal, container, false);

            sucursalViewModel = new ViewModelProvider(this).get(SucursalViewModel.class);

            // --- Inicialización de vistas ---
            etNombre = view.findViewById(R.id.etNombre);
            etDireccion = view.findViewById(R.id.etDireccion);
            etLatitud = view.findViewById(R.id.etLatitud);
            etLongitud = view.findViewById(R.id.etLongitud);
            spEstado = view.findViewById(R.id.spEstado);
            fabVolver = view.findViewById(R.id.fabVolver);
            btnCrear = view.findViewById(R.id.btnCrear);
            tvHorarioLunVieApertura = view.findViewById(R.id.tvHorarioLunVieApertura);
            tvHorarioLunVieCierre = view.findViewById(R.id.tvHorarioLunVieCierre);

            configurarSpinnerEstadoSucursal();
            configurarSelectoresHorario();

            // fabVolver ya no necesita ser inicializado dos veces.
            fabVolver.setOnClickListener(v -> {
                // Es más eficiente usar popBackStack si solo quieremos volver al fragmento anterior
                if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                    getParentFragmentManager().popBackStack();
                } else {
                    // Como fallback si no hay nada en el back stack
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new FragmentAdminSucursales());
                    transaction.commit();
                }
            });

            btnCrear.setOnClickListener(v -> {
                if (!validarCampos()) return;

                String nombre = etNombre.getText().toString().trim();
                String direccion = etDireccion.getText().toString().trim();
                String latitudStr = etLatitud.getText().toString().trim();
                String longitudStr = etLongitud.getText().toString().trim();
                String estadoSeleccionado = spEstado.getSelectedItem().toString();

                String horarioFinal = "L-V: " + formatTime(lunVieAperturaHora, lunVieAperturaMinuto) +
                        " - " + formatTime(lunVieCierreHora, lunVieCierreMinuto);

                double latitud, longitud;
                try {
                    latitud = Double.parseDouble(latitudStr);
                    longitud = Double.parseDouble(longitudStr);
                } catch (NumberFormatException e) {
                    Log.e("CrearSucursal", "Error al parsear latitud o longitud a pesar de la validación", e);
                    return;
                }

                SucursalModel nuevaSucursal = new SucursalModel(
                        nombre,
                        direccion,
                        latitud,
                        longitud,
                        horarioFinal,
                        estadoSeleccionado
                );
                insertarSucursal(nuevaSucursal);
            });

            return view;
        }

        private void configurarSpinnerEstadoSucursal() {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    requireContext(),
                    R.array.estado_sucursal,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spEstado.setAdapter(adapter);
            spEstado.setSelection(0);
        }

        private void configurarSelectoresHorario() {
            tvHorarioLunVieApertura.setOnClickListener(v -> showTimePicker(true));
            tvHorarioLunVieCierre.setOnClickListener(v -> showTimePicker(false));
        }

        private void showTimePicker(boolean isApertura) {
            Calendar c = Calendar.getInstance();
            int initialHour;
            int initialMinute;

            if (isApertura && lunVieAperturaHora != -1) {
                initialHour = lunVieAperturaHora;
                initialMinute = lunVieAperturaMinuto;
            } else if (!isApertura && lunVieCierreHora != -1) {
                initialHour = lunVieCierreHora;
                initialMinute = lunVieCierreMinuto;
            } else {
                initialHour = 9;
                initialMinute = 0;
                if (!isApertura) {
                    initialHour = 17;
                }
            }

            TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                    (timePicker, hourOfDay, minute) -> {
                        String tiempoFormateado = formatTime(hourOfDay, minute);
                        if (isApertura) {
                            lunVieAperturaHora = hourOfDay;
                            lunVieAperturaMinuto = minute;
                            tvHorarioLunVieApertura.setText(tiempoFormateado);
                            tvHorarioLunVieApertura.setError(null);
                        } else {
                            lunVieCierreHora = hourOfDay;
                            lunVieCierreMinuto = minute;
                            tvHorarioLunVieCierre.setText(tiempoFormateado);
                            tvHorarioLunVieCierre.setError(null);
                        }
                    }, initialHour, initialMinute, DateFormat.is24HourFormat(requireContext()));
            timePickerDialog.show();
        }

        private String formatTime(int hourOfDay, int minute) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
            return sdf.format(cal.getTime());
        }

        private void insertarSucursal(SucursalModel sucursal){
            try{
                sucursalViewModel.insertarSucursal(sucursal);
                Toast.makeText(requireContext(), "Sucursal creada exitosamente", Toast.LENGTH_SHORT).show();

                sucursalViewModel.getSucursalCountLive().observe(getViewLifecycleOwner(), count -> {
                    if (count != null) {
                        Log.d("FragmentSucursal", "Cantidad de sucursales: " + count);
                    }
                });
                limpiarCampos();
            } catch(Exception e) {
                Toast.makeText(requireContext(), "Error al crear la sucursal: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("FragmentSucursal", "Error al insertar sucursal", e);
            }
        }

        private boolean validarCampos() {
            if (etNombre.getText().toString().trim().isEmpty()) {
                etNombre.setError("El nombre es obligatorio");
                etNombre.requestFocus();
                return false;
            }
            etNombre.setError(null);

            if (etDireccion.getText().toString().trim().isEmpty()) {
                etDireccion.setError("La dirección es obligatoria");
                etDireccion.requestFocus();
                return false;
            }
            etDireccion.setError(null);

            String latitudStr = etLatitud.getText().toString().trim();
            if (latitudStr.isEmpty()) {
                etLatitud.setError("La latitud es obligatoria");
                etLatitud.requestFocus();
                return false;
            }
            try {
                double lat = Double.parseDouble(latitudStr);
                if (lat < -90 || lat > 90) {
                    etLatitud.setError("Latitud debe estar entre -90 y 90");
                    etLatitud.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                etLatitud.setError("La latitud debe ser un número válido");
                etLatitud.requestFocus();
                return false;
            }
            etLatitud.setError(null);

            String longitudStr = etLongitud.getText().toString().trim();
            if (longitudStr.isEmpty()) {
                etLongitud.setError("La longitud es obligatoria");
                etLongitud.requestFocus();
                return false;
            }
            try {
                double lon = Double.parseDouble(longitudStr);
                if (lon < -180 || lon > 180) {
                    etLongitud.setError("La longitud debe estar entre -180 y 180");
                    etLongitud.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                etLongitud.setError("La longitud debe ser un número válido");
                etLongitud.requestFocus();
                return false;
            }
            etLongitud.setError(null);

            if (lunVieAperturaHora == -1) {
                Toast.makeText(requireContext(), "Seleccione hora de apertura", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (lunVieCierreHora == -1) {
                Toast.makeText(requireContext(), "Seleccione hora de cierre", Toast.LENGTH_SHORT).show();
                return false;
            }

            Calendar calApertura = Calendar.getInstance();
            calApertura.set(Calendar.HOUR_OF_DAY, lunVieAperturaHora);
            calApertura.set(Calendar.MINUTE, lunVieAperturaMinuto);

            Calendar calCierre = Calendar.getInstance();
            calCierre.set(Calendar.HOUR_OF_DAY, lunVieCierreHora);
            calCierre.set(Calendar.MINUTE, lunVieCierreMinuto);

            if (calCierre.before(calApertura) || calCierre.equals(calApertura)) {
                Toast.makeText(requireContext(), "La hora de cierre debe ser posterior a la de apertura.", Toast.LENGTH_LONG).show();
                return false;
            }

            return true;
        }

        private void limpiarCampos() {
            // Implementación del metodo: limpiar campos()
            // con set, toma los valores ingresados en et(editext) para limpiarlos
            etNombre.setText("");
            etDireccion.setText("");
            etLatitud.setText("");
            etLongitud.setText("");

            // Resetear TextViews de horario a sus hints
            // Es una buena práctica tener estos textos en strings.xml
            if (isAdded()) {
                tvHorarioLunVieApertura.setText(getString(R.string.hint_apertura));
                tvHorarioLunVieCierre.setText(getString(R.string.hint_cierre));
            }

            // Resetear variables de hora y minuto
            lunVieAperturaHora = -1;
            lunVieAperturaMinuto = -1;
            lunVieCierreHora = -1;
            lunVieCierreMinuto = -1;

            // Resetear Spinner a la primera posición
            if (spEstado.getAdapter() != null && spEstado.getAdapter().getCount() > 0) {
                spEstado.setSelection(0);
            }

            // Devolver el foco al primer campo
            etNombre.requestFocus();
        }
}
