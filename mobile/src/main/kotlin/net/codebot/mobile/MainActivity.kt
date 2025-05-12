package net.codebot.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import net.codebot.mobile.controller.Controller
import net.codebot.mobile.viewmodel.ViewModel
import net.codebot.mobile.view.Content
import net.codebot.mobile.ui.theme.MobileTheme
import net.codebot.mobile.model.Model
import com.google.firebase.FirebaseApp
import net.codebot.mobile.persistance.DBStorage

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val supabase = DBStorage()
        val model = Model(
            supabase
        )
        val controller = Controller(model)
        val authViewModel = AuthViewModel(model)
        val viewModel = ViewModel(model, authViewModel)


        setContent {
            MobileTheme {
                Content(viewModel, controller, authViewModel)
            }
        }
    }
}