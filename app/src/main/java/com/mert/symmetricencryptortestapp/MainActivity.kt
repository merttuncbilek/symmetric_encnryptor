package com.mert.symmetricencryptortestapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mert.symmetricencryptor.SymmetricEncryptor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SymmetricEncryptor.sharedInstance().init("Ivan Medvedev")

        val encryptedValue = SymmetricEncryptor.sharedInstance().encryptData("Test", "123456")

        Log.d("EncryptedValue", encryptedValue)

        val decryptedValue = SymmetricEncryptor.sharedInstance().decryptData(encryptedValue, "123456")

        Log.d("DecryptedValue", decryptedValue)
    }
}
