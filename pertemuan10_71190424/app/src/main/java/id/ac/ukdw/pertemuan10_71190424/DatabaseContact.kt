package id.ac.ukdw.pertemuan10_71190424

import android.provider.BaseColumns

class DatabaseContact {
    object Penduduk : BaseColumns {
        const val TABLE_NAME = "penduduk"
        const val COLUMN_NAME_NAMA = "nama"
        const val COLUMN_NAME_USIA = "usia"

        val SQL_CREATE_TABLE = """CREATE TABLE $TABLE_NAME {
            ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, 
            $COLUMN_NAME_NAMA TEXT,
            $COLUMN_NAME_USIA INTEGER
            }""".trimMargin()

        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}