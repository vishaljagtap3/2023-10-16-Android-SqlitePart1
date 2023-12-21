package com.bitcodetech.sqlitedemo

import android.app.Activity
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbUtil = DbUtil.getInstance(this)
        dbUtil.insertProduct(101, "XYZ", 1000)
        dbUtil.insertProduct(201, "ABC", 1020)

        for(product in dbUtil.getProducts()) {
            log(product.toString())
        }

        /*val dbHelper = DBHelper(this, "db_products", null, 2)
        db = dbHelper.writableDatabase*/

        //populate()


        /*db = openOrCreateDatabase("db_products", Activity.MODE_PRIVATE, null)
        try {
            db.execSQL(
                "create table products(id integer primary key, title text not null, price integer)"
            )
        } catch (e: Exception) {
        }*/

        /*db.rawQuery(
            "insert into products values(?, ?, ?)",
            arrayOf("1", "Phone", "1234")
        )*/

        /*var rowNum = insertProduct(101, "laptop", 3450)
        mt("$rowNum")
        rowNum = insertProduct(202, "android watch", 3210)
        mt("$rowNum")
        rowNum = insertProduct(154, "android phone", 4500)
        mt("$rowNum")
        rowNum = insertProduct(609, "television", 7890)
        mt("$rowNum")

        populate()
        updateProduct()
        populate()
        deleteProduct()
        populate()*/

        //db.close()
    }

    private fun deleteProduct() {
        val numOfRowsAffected =
            db.delete(
                "products",
                "id = ?",
                arrayOf("101")
            )
        log("Deleted: $numOfRowsAffected")
    }

    private fun updateProduct() {
        val values = ContentValues()
        values.put("price", 9000)
        values.put("title", "Macbook")

        val numOfRowsAffected = db.update(
            "products",
            values,
            "id = ?",
            arrayOf("101")
        )
        log("Updated: $numOfRowsAffected")
    }

    private fun populate() {

        val c: Cursor = db.query(
            "products",
            null,
            null,
            null,
            null,
            null,
            "price desc"
        )

        /*for(columnName in c.columnNames) {
            mt(columnName)
        }*/

        while (c.moveToNext()) {
            log("${c.getInt(0)} ${c.getString(1)} ${c.getInt(2)}")
        }
        log("-----------------------------")
        c.close()

        /*val columns = arrayOf("id", "title", "price")
        val where = "price > ? and price < ?"
        val whereArgs = arrayOf("3000", "4000")
        val groupBy = "category"
        val having = "count(*) > 5"
        val orderBy = "price desc"
        db.query("products", columns, where, whereArgs, groupBy, having, orderBy)*/
    }

    private fun log(text: String) {
        Log.e("tag", text)
    }

    fun insertProduct(id: Int, title: String, price: Int): Long {
        val values = ContentValues()
        values.put("id", id)
        values.put("title", title)
        values.put("price", price)

        return db.insert("products", null, values)
    }

    private fun mt(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}