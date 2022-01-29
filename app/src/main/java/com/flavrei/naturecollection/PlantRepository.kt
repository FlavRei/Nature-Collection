package com.flavrei.naturecollection

import com.flavrei.naturecollection.PlantRepository.Singleton.databaseRef
import com.flavrei.naturecollection.PlantRepository.Singleton.plantList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PlantRepository {

    object Singleton {
        // Se connecter à la référence "plants"
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        // Créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()
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
}