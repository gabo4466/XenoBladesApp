package com.evaluable.ui.screens

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.evaluable.R
import com.evaluable.model.Blade
import com.evaluable.ui.TopBar
import com.evaluable.ui.theme.Gray200
import com.evaluable.ui.theme.Gray500
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListBlades(navController: NavController, email: String?) {

    var user by rememberSaveable { mutableStateOf("") }
    val bladesCollectionName = stringResource(id = R.string.collection_blades)
    val usersCollectionName = stringResource(id = R.string.collection_users)
    var blades = rememberSaveable { mutableListOf<Blade>() }
    val error = stringResource(id = R.string.error_generic)
    var message by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(true) }

    val db = FirebaseFirestore.getInstance()

    if (email != null) {
        db.collection(usersCollectionName)
            .document(email)
            .collection(bladesCollectionName)
            .get()
            .addOnSuccessListener {
                blades.clear()
                for (blade in it) {
                    val auxBlade = Blade(blade.get("name") as String, blade.get("description") as String?, blade.get("element") as String?)
                    blades.add(auxBlade)
                }
            }
            .addOnFailureListener {
                message = error
            }
            .addOnCompleteListener {
                isLoading = false
            }
    }

    email?.let {
        user = it
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navController = navController, pageName = stringResource(id = R.string.blade_list)
        ) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = message, color = MaterialTheme.colors.primary)
            if (isLoading) {
                Spacer(modifier = Modifier.size(30.dp))
                CircularProgressIndicator()
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
            ) {
                blades.forEachIndexed { index, blade ->
                    var colorCell: Color
                    if (index % 2 == 0) {
                        colorCell = Gray200
                    }else {
                        colorCell = Gray500
                    }
                    BladeItem(blade = blade, color = colorCell)
                }

            }
        }
    }
    
}

@Composable
fun BladeItem(blade: Blade, color: Color) {
    Spacer(modifier = Modifier.size(15.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = color, shape = RoundedCornerShape(5.dp))
            .padding(5.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(text = blade.name, color = Color.Black)
            blade.element?.let { Text(text = it, color = Color.Black) }
        }
        blade.description?.let {
            Row() {
                Text(text = blade.description!!, color = Color.Black)
            }
        }

    }
}