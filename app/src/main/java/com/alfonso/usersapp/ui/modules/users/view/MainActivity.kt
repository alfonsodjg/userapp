package com.alfonso.usersapp.ui.modules.users.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alfonso.usersapp.R
import com.alfonso.usersapp.databinding.ActivityMainBinding
import com.alfonso.usersapp.domain.conection.NetworkStatus
import com.alfonso.usersapp.ui.modules.users.adapter.UsersAdapter
import com.alfonso.usersapp.ui.modules.users.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter
    private val viewModel: UsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initRecyclerView()
        val isConnected = isNetworkAvailable()
        viewModel.onGetUsers(isConnected)
        showConnectionBanner(isConnected)
        adapter.updateConnectionStatus(isConnected)

        viewModel.viewState.observe(this) { state ->
            adapter.updateConnectionStatus(state.networkStatus == NetworkStatus.Available)

            if (state.response.isEmpty()) {
                binding.rvUsers.visibility = View.GONE
                binding.progress.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
                binding.tvEmpty.text =  getString(R.string.message_empty)
            } else {
                adapter.updateList(state.response)
                binding.rvUsers.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.GONE
            }

            binding.progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE

            when (state.networkStatus) {
                NetworkStatus.Available -> showConnectionBanner(true)
                NetworkStatus.Lost,
                NetworkStatus.Unavailable -> showConnectionBanner(false)
                else -> {}
            }
        }
    }

    //fun to init recyclerview
    private fun initRecyclerView() {
        adapter = UsersAdapter(
            onCheckedChange = { user, isChecked ->
                val updatedUser = user.copy(completed = isChecked)
                viewModel.onUpdateUser(updatedUser)
            },
            onDeleteClick = { user ->
                Toast.makeText(binding.root.context, "Borrado con éxito", Toast.LENGTH_SHORT).show()
                viewModel.onDeleteUser(user)
            }
        )
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
    }

    fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
    //Fun to show banner when network is available or unavailable
    private fun showConnectionBanner(isConnected: Boolean) {
        binding.connectionBanner.apply {
            visibility = View.VISIBLE
            if (isConnected) {
                text = getString(R.string.network_online)
                setBackgroundColor(ContextCompat.getColor(context, R.color.color_banner))
                setTextColor(ContextCompat.getColor(context, R.color.black))
            } else {
                text = getString(R.string.network_offline)
                setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }

            lifecycleScope.launch {
                delay(4000)
                visibility = View.GONE
            }
        }
    }

}