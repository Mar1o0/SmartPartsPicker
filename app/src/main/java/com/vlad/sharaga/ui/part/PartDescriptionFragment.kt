package com.vlad.sharaga.ui.part

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vlad.sharaga.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartDescriptionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_part_description, container, false)
    }
}