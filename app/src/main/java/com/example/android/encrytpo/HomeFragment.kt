package com.example.android.encrytpo

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android.encrytpo.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onResume() {
        super.onResume()
        val hashAlgos = resources.getStringArray(R.array.hash_algorithms)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.drop_down, hashAlgos)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        // Inflate the layout for this fragment
        _binding =  FragmentHomeBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true) // for enabling the menu in the application



        binding.generateButton.setOnClickListener {
             onGenerateClicked()
        }


        return binding.root
    }

    private fun onGenerateClicked(){
        if(binding.plainText.text.isEmpty()){
               showSnackBar("Field Empty.")
        }else{

            lifecycleScope.launch {
                applyAnimations()
                navigateToSuccess( getHashData())
            }
        }

    }

     // function for animation
    private suspend  fun applyAnimations(){
         binding.generateButton.isClickable = false
        binding.homeChooseTitle.animate().alpha(0f).duration = 400L
        binding.generateButton.animate().alpha(0f).duration = 400L
        binding.textInputLayout.animate().alpha(0f).translationXBy(1200f).duration = 400L
        binding.plainText.animate().alpha(0f).translationXBy(-1200f).duration = 400L

         delay(300)

         binding.successBackground.animate().alpha(1f).duration = 600L
         binding.successBackground.animate().rotationBy(720f).duration = 600L
         binding.successBackground.animate().scaleXBy(1000f).duration = 600L
         binding.successBackground.animate().scaleYBy(1000f).duration = 600L

         delay(300)

         binding.successImageView.animate().alpha(1f).duration = 1000L

         delay(1200L)


    }


    private fun navigateToSuccess(hash : String){
        val directions = HomeFragmentDirections.actionHomeFragmentToSucessFragment(hash)
        findNavController().navigate(directions)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu , menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSnackBar(message : String){
        val SnackBar = Snackbar.make(binding.homeLayout,message,Snackbar.LENGTH_SHORT)
        SnackBar.setAction("okay") {}
        SnackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        SnackBar.show()
    }

    private fun getHashData(): String{
        val algorithm = binding.autoCompleteTextView.text.toString()
        val plainText = binding.plainText.text.toString()
        return homeViewModel.getHash(plainText,algorithm)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.clear_menu){
            binding.plainText.text.clear()
            showSnackBar("Cleared")
            return true;
        }
        return true
    }
}