package ru.mail.polis.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.mail.polis.R
import ru.mail.polis.viewModels.SettingsViewModel

class SettingsFragment : Fragment() {

    private lateinit var editButton: Button
    private lateinit var changePhotoButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var addExternalAccountImageButton: ImageButton
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editButton = view.findViewById(R.id.fragment_settings__button_edit)



        changePhotoButton = view.findViewById(R.id.fragment_settings__change_avatar_component)
        nameEditText = view.findViewById(R.id.fragment_settings__et_name)
        surnameEditText = view.findViewById(R.id.fragment_settings__et_surname)
        phoneEditText = view.findViewById(R.id.fragment_settings__et_phone)
        ageEditText = view.findViewById(R.id.fragment_settings__et_age)
        addExternalAccountImageButton =
            view.findViewById(R.id.fragment_settings__add_external_account_button)

        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        settingsViewModel.getUserInfo()

        settingsViewModel.getUser().observe(viewLifecycleOwner, { userED ->
            nameEditText.setText(userED.name)
            surnameEditText.setText(userED.surname)
            phoneEditText.setText(userED.phone)
            ageEditText.setText(userED.age.toString())
        })

        editButton.setOnClickListener(this::onClickEditUser)
        changePhotoButton.setOnClickListener(this::onClickChangePhoto)
        addExternalAccountImageButton.setOnClickListener(this::onClickAddExternalAccount)

    }

    private fun onClickChangePhoto(view: View) {

    }

    private fun onClickEditUser(view: View) {

    }

    private fun onClickAddExternalAccount(view: View) {

    }
}
