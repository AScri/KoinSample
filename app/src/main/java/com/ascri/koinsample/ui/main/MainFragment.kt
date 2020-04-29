package com.ascri.koinsample.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ascri.koinsample.R
import com.ascri.koinsample.ui.adapters.CatAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    // Instantiate viewModel with Koin
    private val viewModel: MainViewModel by viewModel()
    private lateinit var catAdapter: CatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val onCatClicked: (imageUrl: String) -> Unit = { imageUrl ->
            Snackbar.make(requireView(), "Clicked: $imageUrl", Snackbar.LENGTH_SHORT)
        }
        catAdapter = CatAdapter(onCatClicked)
        catsRecyclerView.apply {
            // Displaying data in a Grid design
            layoutManager = GridLayoutManager(context, 3)
            adapter = catAdapter
        }
        // Initiate the observers on viewModel fields and then starts the API request
        initViewModel()
    }

    private fun initViewModel() {
        // Observe catsList and update our adapter if we get new one from API
        viewModel.catsList.observe(viewLifecycleOwner, Observer { newCatsList ->
            catAdapter.updateData(newCatsList)
        })
        // Observe showLoading value and display or hide our activity's progressBar
        viewModel.showLoading.observe(viewLifecycleOwner, Observer { showLoading ->
            mainProgressBar.visibility = if (showLoading) View.VISIBLE else View.GONE
        })
        // Observe showError value and display the error message as a Toast
        viewModel.showError.observe(viewLifecycleOwner, Observer { showError ->
            Toast.makeText(context, showError, Toast.LENGTH_SHORT).show()
            Log.d(TAG, "error: $showError")
        })
        // The observers are set, we can now ask API to load a data list
        viewModel.loadCats()
    }

    companion object{
        const val TAG = "MAIN_FRAGMENT"
    }

}
