package com.smart.parts.picker.core.view

import android.app.Dialog
import android.content.Context
import androidx.core.widget.addTextChangedListener
import com.smart.parts.picker.core.adapter.spinner.DropDownSpinnerAdapter
import com.smart.parts.picker.data.database.tables.assembly.AssemblyData
import com.smart.parts.picker.databinding.PopupAssemblyCreateBinding
import com.smart.parts.picker.databinding.PopupAssemblySelectBinding

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
        binding.spAssembliesOptions.adapter = DropDownSpinnerAdapter(
            context = context,
            values = assemblies,
            map = { it?.title ?: "" },
            withInput = true,
            onConfirmClicked = {
                onCreateAssembly(it)
                dismiss()
            },
        )
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