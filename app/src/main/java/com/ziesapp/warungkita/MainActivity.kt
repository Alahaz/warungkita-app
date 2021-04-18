package com.ziesapp.warungkita

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionReference: CollectionReference = db.collection("barang")

    lateinit var query: Query
    lateinit var fireStoreList: RecyclerView

    var barangAdapter: BarangAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupDropdown()

        btn_simpan.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        var nama: String = inputNamaBarang.editText?.text.toString()
        var jumlahBarang = inputJumlahBarang.editText?.text.toString()
        var satuanBarang = inputSatuanBarang.editText?.text.toString()

        val barang = hashMapOf(
            "nama" to nama,
            "jumlahBarang" to jumlahBarang,
            "satuanBarang" to satuanBarang
        )

        db.collection("barang")
            .add(barang as Map<String, Any>)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Upload berhasil", Toast.LENGTH_SHORT).show()

                inputNamaBarang.editText?.text?.clear()
                inputJumlahBarang.editText?.text?.clear()
                inputSatuanBarang.editText?.text?.clear()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, "Upload gagal, coba lagi!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupRecyclerView() {
        fireStoreList = findViewById(R.id.rv_main)
        query = collectionReference

        val firestoreRecyclerOptions: FirestoreRecyclerOptions<Barang> =
            FirestoreRecyclerOptions.Builder<Barang>()
                .setQuery(query, Barang::class.java)
                .build()

        barangAdapter = BarangAdapter(firestoreRecyclerOptions)
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = barangAdapter
    }

    private fun setupDropdown() {
        val satuan = resources.getStringArray(R.array.satuan)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_item, satuan)
        (inputSatuanBarang.editText as? AutoCompleteTextView)?.setAdapter(arrayAdapter)
    }

    override fun onStart() {
        super.onStart()
        barangAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        barangAdapter!!.stopListening()
    }
}