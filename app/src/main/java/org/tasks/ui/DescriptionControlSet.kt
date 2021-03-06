package org.tasks.ui

import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import org.tasks.R
import org.tasks.databinding.ControlSetDescriptionBinding
import org.tasks.dialogs.Linkify
import org.tasks.preferences.Preferences
import javax.inject.Inject

@AndroidEntryPoint
class DescriptionControlSet : TaskEditControlFragment() {
    @Inject lateinit var linkify: Linkify
    @Inject lateinit var preferences: Preferences

    private lateinit var editText: EditText
    
    override fun createView(savedInstanceState: Bundle?) {
        viewModel.description?.let(editText::setTextKeepState)
        if (preferences.getBoolean(R.string.p_linkify_task_edit, false)) {
            linkify.linkify(editText)
        }
    }

    override fun bind(parent: ViewGroup?) =
        ControlSetDescriptionBinding.inflate(layoutInflater, parent, true).let {
            editText = it.notes.apply {
                addTextChangedListener(
                    onTextChanged = { text, _, _, _ -> textChanged(text) }
                )
            }
            it.root
        }

    override val icon = R.drawable.ic_outline_notes_24px

    override fun controlId() = TAG

    private fun textChanged(text: CharSequence?) {
        viewModel.description = text?.toString()?.trim { it <= ' ' }
    }

    companion object {
        const val TAG = R.string.TEA_ctrl_notes_pref
    }
}