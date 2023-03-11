package com.gaurav.permissionsanduploadimages


import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.gaurav.permissionsanduploadimages.databinding.ActivityMainBinding
import com.google.firebase.storage.FirebaseStorage
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var storageRef=FirebaseStorage.getInstance()
    var imagePermission=registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            Toast.makeText(this,"Permission is Granted",Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(this,"No Permission Granted",Toast.LENGTH_SHORT).show()

        }
    }
    var pickImage=registerForActivityResult(ActivityResultContracts.GetContent()){
        binding.imageView.setImageURI(it)
        it?.let{it1->
            storageRef.getReference(Calendar.getInstance().timeInMillis.toString())
                .putFile(it1)
                .addOnSuccessListener { uploadTask-> }

    }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAddImage.setOnClickListener {
                if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    ==PackageManager.PERMISSION_GRANTED){
                    pickImage.launch("image/*")



                }else{
                    imagePermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)

                }
        }

    }
}