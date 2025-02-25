package pl.mlynarczyk.pianoapp.data

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

object FirebaseStorageObject {
    private var storage: StorageReference? = null

    fun getStorageRef() : StorageReference{
        if(storage == null){
            //storage = FirebaseStorage.getInstance().getReference()
            storage = Firebase.storage.getReference()

        }
        return storage!!
    }

}