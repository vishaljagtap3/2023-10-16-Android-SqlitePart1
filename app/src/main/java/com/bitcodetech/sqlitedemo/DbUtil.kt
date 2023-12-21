package com.bitcodetech.sqlitedemo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DbUtil(context: Context) {

    private val db : SQLiteDatabase

    init {
        db = DBHelper(context, "db_products", null, 1)
            .writableDatabase
    }

    companion object {
        private var dbUtil : DbUtil? = null

        fun getInstance(context: Context) : DbUtil {
            if(dbUtil == null) {
                dbUtil = DbUtil(context)
            }
            return dbUtil!!
        }
    }

    fun getProducts() : ArrayList<Product>{
        val c = db.query("products", null, null, null, null, null, null)

        val products = ArrayList<Product>()
        while(c.moveToNext()) {
            products.add(
                Product(
                    c.getInt(0),
                    c.getString(1),
                    c.getInt(2)
                )
            )
        }

        return products
    }

    fun insertProduct(
        id : Int,
        title : String,
        price : Int
    ) : Boolean {
        val values = ContentValues()
        values.put("id", id)
        values.put("title", title)
        values.put("price", price)

        return db.insert("products", null, values) != -1L
    }

    fun deleteProduct(id : Int) : Int {
        return db.delete(
                "products",
                "id = ?",
                arrayOf("$id")
            )

    }

    fun updateProduct(title : String, price : Int) : Int{
        val values = ContentValues()
        values.put("price", price)
        values.put("title", title)

        return  db.update(
            "products",
            values,
            "id = ?",
            arrayOf("101")
        )
    }

}