package com.flavrei.naturecollection

import android.net.Uri
import com.flavrei.naturecollection.PlantRepository.Singleton.databaseRef
import com.flavrei.naturecollection.PlantRepository.Singleton.downloadUri
import com.flavrei.naturecollection.PlantRepository.Singleton.plantList
import com.flavrei.naturecollection.PlantRepository.Singleton.storageReference
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.*

class PlantRepository {

    object Singleton {
        // Créer le lien pour accéder au bucket
        private val BUCKET_URL: String = "gs://nature-collection-3b3ce.appspot.com"

        // Se connecter à notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // Se connecter à la référence "plants"
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        // Créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()

        // Contenir le lien de l'image courante
        var downloadUri: Uri? = null
    }

    fun updateData(callback: () -> Unit) {
        // Absorber les données récupérées dans la databaseref pour les donner à la liste de plantes
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Retirer les anciennes plantes
                plantList.clear()

                // Récolter la liste
                for (ds in snapshot.children) {
                    // Construire un objet plante
                    val plant = ds.getValue(PlantModel::class.java)

                    // Vérifier que la plante n'est pas null
                    if (plant != null) {
                        // Ajouter la plante à notre liste
                        plantList.add(plant)
                    }
                }
                // Actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    // Créer une fonction pour envoyer des fichiers sur le storage
    fun uploadImage(file: Uri, callback: () -> Unit) {
        // Vérifier que ce fichier n'est pas null
        if (file != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            // Démarrer la tâche d'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                // Vérifier si il y a eu un problème lors de l'envoi
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener { task ->
                // Vérifier si tout a fonctionné
                if (!task.isSuccessful) {
                    // Récupérer l'image
                    downloadUri = task.result
                    callback()
                }
            }
        }
    }

    // Mettre à jour l'objet plante en BD
    fun updatePlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    // Insérer une nouvelle plante en BD
    fun insertPlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    // Supprimer une plante de la BD
    fun deletePlant(plant: PlantModel) = databaseRef.child(plant.id).removeValue()
}