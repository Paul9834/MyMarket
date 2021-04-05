package com.paul9834.market

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {



    private lateinit var buttonDownload:MaterialButton

    companion object {
        const val PERMISSION_REQUEST_STORAGE = 0
    }

    lateinit var downloadController: DownloadController

    lateinit var mainLayout:ConstraintLayout;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonDownload = findViewById(R.id.buttonDownload);
        mainLayout = findViewById(R.id.mainLayout);


        // This apk is taking pagination sample app
        val apkUrl = "https://download.apkpure.com/custom/com.apkpure.aegon-3171821.apk?_fn=QVBLUHVyZV92My4xNy4xOF9hcGtwdXJlLmNvbS5hcGs&as=76d35a8db4f938e4fdb3285244f764bd606b467b&ai=-524167275&at=1617643011&_sa=ai%2Cat&k=1b5e21edafa6a37e543083f7e37ff7b7606de903&_p=Y29tLmFwa3B1cmUuYWVnb24&c=1%7CTOOLS%7CZGV2PUFQS1B1cmUmdD1hcGsmcz0xNjczNzIzMSZ2bj0zLjE3LjE4JnZjPTMxNzE4MjE&hot=1&arg=apkpure%3A%2F%2Fcampaign%2F%3Futm_source%3Dapkpure%26utm_medium%3Ddetails"
        downloadController = DownloadController(this, apkUrl)


        buttonDownload.setOnClickListener {
            // check storage permission granted if yes then start downloading file
            checkStoragePermission()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            // Request for camera permission.
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // start downloading
                downloadController.enqueueDownload()
            } else {
                // Permission request was denied.
                mainLayout.showSnackbar(R.string.storage_permission_denied, Snackbar.LENGTH_SHORT)
            }
        }
    }

    private fun checkStoragePermission() {
        // Check if the storage permission has been granted
        if (checkSelfPermissionCompat(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            // start downloading
            downloadController.enqueueDownload()
        } else {
            // Permission is missing and must be requested.
            requestStoragePermission()
        }
    }


    private fun requestStoragePermission() {

        if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            mainLayout.showSnackbar(
                R.string.storage_access_required,
                Snackbar.LENGTH_INDEFINITE, R.string.ok
            ) {
                requestPermissionsCompat(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_STORAGE
                )
            }

        } else {
            requestPermissionsCompat(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_STORAGE
            )
        }
    }



}