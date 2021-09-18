package com.example.android.encrytpo

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android.encrytpo.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

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

            lifecycleScope.launch {
                applyAnimations()
                navigateToSuccess()
            }


        }


        return binding.root
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


    private fun navigateToSuccess(){
        findNavController().navigate(R.id.action_homeFragment_to_sucessFragment)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu , menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}