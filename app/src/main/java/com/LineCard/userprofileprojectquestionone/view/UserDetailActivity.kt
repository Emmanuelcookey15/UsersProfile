package com.LineCard.userprofileprojectquestionone.view

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.LineCard.userprofileprojectquestionone.R
import com.LineCard.userprofileprojectquestionone.model.UserImage
import com.LineCard.userprofileprojectquestionone.viewmodel.UsersViewModel
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

class UserDetailActivity : AppCompatActivity() {

    lateinit var userTextName: TextView
    lateinit var userTextUsername: TextView
    lateinit var userTextEmail: TextView
    lateinit var userTextPhoneNum: TextView
    lateinit var userImageView: ImageView

    lateinit var viewmodel: UsersViewModel

    private val RESULT_LOAD_IMG = 46
    private val CAMERA_REQUEST = 47

    var imageValue: String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        viewmodel = ViewModelProvider(this).get(UsersViewModel::class.java)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        userTextName = findViewById(R.id.user_main_name)
        userImageView = findViewById(R.id.userImage)
        userTextUsername = findViewById(R.id.user_name)
        userTextEmail = findViewById(R.id.user_email)
        userTextPhoneNum = findViewById(R.id.user_phone)

        val intentUser = intent

        setupViews(intentUser.getIntExtra("user_id", 1))



        userImageView.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
                startDialogForImageSelection()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_REQUEST)
            }
        }

    }


    fun setupViews(userId: Int) {
        viewmodel.getParticularUsers(userId).observe(this, Observer {
            userTextName.text = it.name
            userTextUsername.text = it.username
            userTextEmail.text = it.email
            userTextPhoneNum.text = it.phone
        })

        viewmodel.getParticularImages(userId).observe(this, Observer {
            if (it?.image != null){
                val imageInBitmap = StringToBitMap(it.image)
                Log.d("IMAAA", imageInBitmap.toString())
                if (imageInBitmap != null){
                    userImageView.setImageBitmap(imageInBitmap)
                }
            }
        })
    }

    private fun StringToBitMap(encodedString: String): Bitmap? {
        try {
            val encodeByte = android.util.Base64.decode(encodedString, android.util.Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            return null
        }

    }



    private fun startDialogForImageSelection() {
        val myAlertDialog = androidx.appcompat.app.AlertDialog.Builder(
            this)
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery", DialogInterface.OnClickListener() {
                dialogInterface: DialogInterface, i: Int ->

            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)

        })

        myAlertDialog.setNegativeButton("Camera", DialogInterface.OnClickListener() {
                dialogInterface: DialogInterface, i: Int ->
            val intent = Intent(
                MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,
                CAMERA_REQUEST)
        })
        myAlertDialog.show()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMG) {
            try {
                val imageUri = data!!.data

                val imageStream = contentResolver.openInputStream(imageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                val nh = (selectedImage.height * (512.0 / selectedImage.width)).toInt()
                val scaled = Bitmap.createScaledBitmap(selectedImage, 512, nh, true)
                userImageView.setImageBitmap(scaled)
                imageValue = imageUri.toString()
                BitMapToStringAndSaveInDB(scaled)


            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        } else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            val theImage = data?.extras?.get("data") as Bitmap
            val nh = (theImage.height * (512.0 / theImage.width)).toInt()
            val scaled = Bitmap.createScaledBitmap(theImage, 512, nh, true)

            userImageView.setImageBitmap(scaled)

            BitMapToStringAndSaveInDB(scaled)

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDialogForImageSelection()
            }

        }
    }


    private fun BitMapToStringAndSaveInDB(bitmap: Bitmap){
        val intentUser = intent
        val baos =  ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos)
        val b= baos.toByteArray()
        val temp= android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT)
        imageValue = temp
        val userImage = UserImage()
        userImage.id = intentUser.getIntExtra("user_id", 0)
        userImage.image = imageValue!!
        viewmodel.insertImages(userImage)
    }


}
