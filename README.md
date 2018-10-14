# Symmetric Encryptor
This library is a helper to encrypt any data with Symmetric Encryption Algorithm.

       compile 'com.mert.symmetricencryptor:SymmetricEncryptor:1.0.0'

#Usage

       
	Initialize a SaltKey;
        SymmetricEncryptor.sharedInstance().init("Ivan Medvedev")

    Encryption;

        val encryptedValue = SymmetricEncryptor.sharedInstance().encryptData("Test", "123456")
        Log.d("EncryptedValue", encryptedValue)
        
    Decryption;

        val decryptedValue = SymmetricEncryptor.sharedInstance().decryptData(encryptedValue, "123456")
        Log.d("DecryptedValue", decryptedValue)
    
