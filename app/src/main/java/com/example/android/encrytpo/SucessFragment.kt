package com.example.android.encrytpo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.example.android.encrytpo.databinding.FragmentHomeBinding
import com.example.android.encrytpo.databinding.FragmentSucessBinding
import com.google.android.material.snackbar.Snackbar

class SucessFragment : Fragment() {

    private val args: SucessFragmentArgs by navArgs()
    private var _binding: FragmentSucessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSucessBinding.inflate(inflater,container,false)

        binding.hashTextView.text = args.hash

        binding.copyToClipBoard.setOnClickListener {
            onCopyClicked()
            showSnackBar("Copied!!")
        }



          return binding.root
    }

    private fun onCopyClicked() {
        copyToClipboard(args.hash)
    }

    private fun copyToClipboard(hash: String) {
           val clipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
           val clipData = ClipData.newPlainText("Encrypted Text", hash)
           clipboardManager.setPrimaryClip(clipData)
    }

    private fun showSnackBar(message : String){
        val SnackBar = Snackbar.make(binding.successLayout,message, Snackbar.LENGTH_SHORT)
        SnackBar.setAction("okay") {}
        SnackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        SnackBar.show()
    }


}