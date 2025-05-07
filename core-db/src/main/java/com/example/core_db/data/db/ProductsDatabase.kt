package com.example.core_db.data.db

import com.example.core_model.data.db.ProductDbModel
import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ProductDbModel::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class ProductsDatabase : RoomDatabase() {

    abstract fun productsDao(): ProductsDao

    companion object {

        private var INSTANCE: ProductsDatabase? = null
        private val lock = Any()
        private const val DB_NAME = "all_products.db"

        fun getInstance(context: Context): ProductsDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(lock) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    context,
                    ProductsDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}