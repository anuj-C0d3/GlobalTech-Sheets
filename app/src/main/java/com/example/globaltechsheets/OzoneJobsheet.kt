package com.example.globaltechsheets

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.globaltechsheets.databinding.ActivityOzoneJobsheetBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class OzoneJobsheet : AppCompatActivity() {
    private lateinit var binding: ActivityOzoneJobsheetBinding
    private lateinit var layoutToConvert: ConstraintLayout

    @SuppressLint("MissingInflatedId", "MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        //super.onSaveInstanceState(outState)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOzoneJobsheetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val firstoragedb = FirebaseStorage.getInstance().reference
        val db = database.getDatabase(this)

      val jsonString =  intent.getStringExtra("jsonString")

        layoutToConvert = binding.myjobsheetlayout
        binding.sigcus.isVisible= false
        binding.signaturePad.isVisible = false
        binding.exsignimg.isVisible = false
        binding.cussignimg.isVisible = false
        binding.expertSignature.isVisible = false
        binding.cussignature.isVisible = false
        binding.exppad.setOnClickListener {
            binding.custompad.isVisible = true
            binding.cussignature.isVisible = false
            binding.expertSignature.isVisible = true
            binding.exppad.isVisible = false
            binding.signaturePad.clear();
            binding.sigcus.isVisible = true
            binding.signaturePad.isVisible = true;
            binding.expertSignature.setOnClickListener {
                   var exBitmap = binding.signaturePad.transparentSignatureBitmap
                if (exBitmap != null) {
                    binding.exsignimg.setImageBitmap(exBitmap)
                    binding.exsignimg.isVisible =true
                }
                binding.expertSignature.isVisible = false

                binding.exppad.isVisible = true
                binding.sigcus.isVisible = false
                binding.signaturePad.isVisible =false
            }
        }
        binding.custompad.setOnClickListener {
            binding.exppad.isVisible = true
            binding.expertSignature.isVisible = true

            binding.signaturePad.clear()
            binding.custompad.isVisible = false
            binding.sigcus.isVisible= true
            binding.signaturePad.isVisible = true;
            binding.expertSignature.isVisible =false
            binding.cussignature.isVisible = true
            binding.cussignature.setOnClickListener {

               var cusBitmap = binding.signaturePad.transparentSignatureBitmap
                if (cusBitmap != null) {
                    binding.cussignimg.setImageBitmap(cusBitmap)
                    binding.cussignimg.isVisible = true
                }
                binding.cussignature.isVisible = false

                binding.custompad.isVisible = true
                binding.sigcus.isVisible =false
                binding.signaturePad.isVisible = false
            }
        }
        binding.savebtn.setOnClickListener {
                val requestno = binding.requestno.text.toString()
                val addr1 = binding.sheetAddr1.text.toString()
                val addr2 = binding.sheetAddr2.text.toString()
                val mobile = binding.sheetmobile.text.toString()
                val customerName = binding.sheetCusName.text.toString()
                val keypad = binding.keypad.isChecked
                val fingerprint =binding.fingerprint.isChecked
                val completeAssesor = binding.completeasesor.isChecked
                val inwarrenty = binding.inwarrenty.isChecked
                val repair  = binding.repair.isChecked
                val service = binding.sheetservice.isChecked
                val install = binding.sheetinstall.isChecked
                val date = binding.editTextDate.text.toString()
                val productname = binding.pnameCode.text.toString()
                val spareParts = binding.spareRequest.text.toString()
                val fault1 = binding.faultDescrib.text.toString()
                val fault2 = binding.faultDescrib2.text.toString()
                val servicecharge = binding.servicecharge.text.toString()
                val sparepart2 = binding.SparePartscharge.text.toString()
                val expertname = binding.nameExpert.text.toString()
            val exsignbitmap = getBitmapFromView(binding.exsignimg)
                val paymethod = binding.paymentcashchek.isChecked
            val cusimgbitmap = getBitmapFromView(binding.cussignimg)

            val jobsheetdata = Jobsheetdata(
                requestno,
                customerName,
                addr1,
                addr2,
                keypad,
                fingerprint,
                completeAssesor,
                inwarrenty,
                repair,
                service,
                install,
                date,
                productname,
                spareParts,
                fault1,
                fault2,
                servicecharge,
                sparepart2,
                expertname,
                exsignbitmap,
                cusimgbitmap,
                paymethod,
                mobile
            )
                GlobalScope.launch {
                    if (requestno != null) {
                      if (!db.ozonDao().isExist(requestno.toInt()))  {
                            db.ozonDao().insert(jobsheetdata)
                        } else{
                            db.ozonDao().update(jobsheetdata)
                      }


                }

            val billno = binding.requestno.text.toString()
            if (billno != null) {

                val savedImagepath = convertLayoutToImageAndSave(billno)

            }
            }
        }
        if(jsonString!=null) {
            val gson = Gson()
            val data = gson.fromJson(jsonString, Jobsheetdata::class.java)


            binding.requestno.setText(data.requestNo)
            binding.editTextDate.setText(data.date)
            binding.sheetCusName.setText(data.customerName)
            binding.sheetAddr1.setText(data.address1)
            binding.sheetAddr2.setText(data.address2)
            binding.pnameCode.setText(data.productname)
            binding.spareRequest.setText(data.spareParts)
            binding.faultDescrib.setText(data.faultDes1)
            binding.nameConsent.setText(data.customerName)
            binding.nameConsent2.setText(data.requestNo)
            binding.faultDescrib2.setText(data.faultdes2)
            binding.servicecharge.setText(data.serviceCharge)
            binding.SparePartscharge.setText(data.sparePart2)
            binding.cname.setText(data.customerName)
            binding.sheetmobile.setText(data.mobile)


            if (data.inwarrenty) {
                binding.inwarrenty.isChecked = true
            } else {
                binding.outwarranty.isChecked = true
            }
            if (data.keypad) binding.keypad.isChecked = true
            if (data.fingerprint) binding.fingerprint.isChecked = true
            if (data.completeAssesor) binding.completeasesor.isChecked = true
            if (data.service) binding.sheetservice.isChecked = true
            if (data.install) binding.sheetinstall.isChecked = true
            if (data.repair) binding.repair.isChecked = true
            if (data.paymethod) {
                binding.paymentcashchek.isChecked = true
            } else {
                binding.paymentchek.isChecked = true
            }
            if(data.signature!=null){
                binding.exsignimg.isVisible = true
                Glide.with(this)

                    .load(data.signature)
                    .into(binding.exsignimg)
            }
            if(data.customerSign!=null){
                binding.cussignimg.isVisible = true
                Glide.with(this)
                    .load(data.customerSign)
                    .into(binding.cussignimg)
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.jobsheet)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets


        }
    }

    fun getBitmapFromView(view: View): Bitmap {
        // Measure and layout the view
        view.measure(
            View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(view.height, View.MeasureSpec.EXACTLY)
        )
        view.layout(view.left, view.top, view.right, view.bottom)

        // Create a Bitmap with the same dimensions as the view
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

        // Create a Canvas to draw the view onto the Bitmap
        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }

    fun convertLayoutToImageAndSave(billno: String): String {
        // Measure the full size of the layout
        val width = layoutToConvert.width
        val height = layoutToConvert.height

        // If the view is scrollable (e.g., ScrollView), use the full content height
        layoutToConvert.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val fullHeight = layoutToConvert.measuredHeight
        val fullWidth = layoutToConvert.measuredWidth

        // Create a bitmap with the correct width and height
        val bitmap = Bitmap.createBitmap(fullWidth, fullHeight, Bitmap.Config.ARGB_8888)

        // Create a canvas to draw the layout on the bitmap
        val canvas = Canvas(bitmap)
        layoutToConvert.layout(0, 0, fullWidth, fullHeight) // Set layout boundaries
        layoutToConvert.draw(canvas) // Draw the layout on the canvas

        // Save bitmap to storage
        return saveBitmap(bitmap, billno)
    }

    fun saveBitmap(bitmap: Bitmap, billno: String): String {
        val root =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val myDir = File(root, "Ozone Jobsheets")
        myDir.mkdirs()

        val fileName = "Ozone${billno}.png"
        val file = File(myDir, fileName)

        try {
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }


        return file.absolutePath


    }

}
