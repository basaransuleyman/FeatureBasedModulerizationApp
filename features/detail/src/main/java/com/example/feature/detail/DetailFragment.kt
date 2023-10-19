package com.example.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.feature.detail.databinding.FragmentDetailBottomSheetBinding
import com.example.core.extensions.setImageUrl
import com.example.navigation.CardUIModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            getArguments(requireArguments())
        }
    }

    private fun getArguments(bundle: Bundle) {
        bundle.let {
            val heroCard = it.getParcelable(ARGUMENTS_DATA) as? CardUIModel
            if (heroCard != null) {
                bindUI(heroCard)
                setBackStackEntry(heroCard.hp)
            }
        }
    }

    private fun bindUI(heroCard: CardUIModel) {
        with(binding) {
            heroNameTextView.text = heroCard.name
            heroArtistTextView.text = heroCard.artistName
            heroImageView.setImageUrl(heroCard.image)
        }
    }

    private fun setBackStackEntry(heroHealthPoint: String) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            BACK_STACK_INPUT,
            heroHealthPoint
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val BACK_STACK_INPUT = "backStackEntry"
        const val ARGUMENTS_DATA = "card"
    }
}