package com.andres.pasaportecafeovalle.data.local.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.andres.pasaportecafeovalle.data.local.dao.ClientesDao;
import com.andres.pasaportecafeovalle.data.local.dao.ProductosDao;
import com.andres.pasaportecafeovalle.data.local.dao.SucursalDao;
import com.andres.pasaportecafeovalle.data.local.dao.VisitasDao;
import com.andres.pasaportecafeovalle.data.local.entities.BeneficioEntity;
import com.andres.pasaportecafeovalle.data.local.entities.CanjeEntity;
import com.andres.pasaportecafeovalle.data.local.entities.ClientesEntity;
import com.andres.pasaportecafeovalle.data.local.entities.ProductoEntity;
import com.andres.pasaportecafeovalle.data.local.entities.ReglaEntity;
import com.andres.pasaportecafeovalle.data.local.entities.SucursalEntity;
import com.andres.pasaportecafeovalle.data.local.entities.VisitaEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(
        entities = {
                ClientesEntity.class,
                CanjeEntity.class,
                BeneficioEntity.class,
                SucursalEntity.class,
                ReglaEntity.class,
                ProductoEntity.class,
                VisitaEntity.class
        },
        version = 8, // cada vez que cambies entidades, sube el número
        exportSchema = false
)
public abstract class DataBase extends RoomDatabase {

    public static final Executor databaseWriteExecutor = Executors.newFixedThreadPool(4);
    private static volatile DataBase INSTANCE;

    public abstract ClientesDao clientesDao();
    public abstract VisitasDao visitasDao();
    public abstract ProductosDao productosDao();

    public abstract SucursalDao sucursalDao();

    // Singleton para manejar una sola instancia de la base de datos
    public static DataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    DataBase.class,
                                    "pasaporte_cafe_db"
                            )
                            // Con esto se borra y recrea la DB si el esquema cambió
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback(){
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    db.execSQL("INSERT OR IGNORE INTO sucursales (id_sucursal, nombre, direccion, lat, lng, horario, estado) " +
                                            "VALUES (1, 'Sucursal Principal', 'Av. Central 123', 123.312, 123.123, 'lunes a viernes de 9 am a 6 pm', 'Activo')");
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

