package com.vlad.sharaga.core.view

import android.app.Dialog
import android.content.Context
import androidx.core.widget.addTextChangedListener
import com.vlad.sharaga.core.adapter.spinner.DropDownSpinnerAdapter
import com.vlad.sharaga.data.database.tables.assembly.AssemblyData
import com.vlad.sharaga.databinding.PopupAssemblyCreateBinding
import com.vlad.sharaga.databinding.PopupAssemblySelectBinding

enum class AssemblyPopupType {
    CREATE,
    SELECT
}

class AssemblyPopup(
    context: Context,
    type: AssemblyPopupType? = null,
    private val assemblies: List<AssemblyData> = emptyList(),
    private val onCreateAssembly: (String) -> Unit = {},
    private val onSelectedAssembly: (Int) -> Unit = {},
) : Dialog(context) {

    private val assemblyPopupType: AssemblyPopupType = type ?: if (assemblies.isEmpty()) {
        AssemblyPopupType.CREATE
    } else {
        AssemblyPopupType.SELECT
    }

    init {
        when (assemblyPopupType) {
            AssemblyPopupType.CREATE -> setupCreatePopup()
            AssemblyPopupType.SELECT -> setupSelectPopup()
        }
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun setupSelectPopup() {
        val binding = PopupAssemblySelectBinding.inflate(layoutInflater)
        binding.spAssembliesOptions.adapter = DropDownSpinnerAdapter(context, assemblies) {
            it?.title ?: ""
        }
        binding.btnSelect.setOnClickListener {
            binding.btnSelect.isEnabled = false
            onSelectedAssembly(assemblies[binding.spAssembliesOptions.selectedItemPosition].id)
            dismiss()
        }
        setContentView(binding.root)
    }

    private fun setupCreatePopup() {
        val binding = PopupAssemblyCreateBinding.inflate(layoutInflater)
        binding.etName.addTextChangedListener {
            binding.btnCreate.isEnabled = it?.isNotBlank() == true
        }
        binding.btnCreate.setOnClickListener {
            binding.btnCreate.isEnabled = false
            onCreateAssembly(binding.etName.text.toString())
            dismiss()
        }
        setContentView(binding.root)
    }

}