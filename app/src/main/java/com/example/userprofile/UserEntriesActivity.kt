package com.example.userprofile

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.userprofile.databinding.ActivityUserEntriesBinding
import java.util.*

class UserEntriesActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserEntriesBinding

    var eMailField: EditText? = null
    var isAllFieldsChecked = false


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUserEntriesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        eMailField = binding.etEmail

        binding.etGender.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)

        binding.etDateOfBirth.setOnClickListener{view->
            clickDatePicker(view)

        }

        receiveUserInfo()


    }

    override fun onResume() {
        super.onResume()
        val genderSelection = resources.getStringArray(R.array.select_gender)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item,genderSelection)
        binding.etGender.setAdapter(arrayAdapter)
    }

    private fun receiveUserInfo() {

        val firstNAme =  intent.getStringExtra(Constants.FIRST_NAME)
        val lastName =  intent.getStringExtra(Constants.LAST_NAME)
        val email =  intent.getStringExtra(Constants.EMAIL)
        val dateOfBirth =  intent.getStringExtra(Constants.DATE_OF_BIRTH)
        val gender =  intent.getStringExtra(Constants.GENDER)


        binding.etName.setText(firstNAme)
        binding.etLastName.setText(lastName)
        binding.etEmail.setText(email)
        binding.etDateOfBirth.setText(dateOfBirth)
        binding.etGender.setText(gender)

    }

    override fun onClick(v: View?) {
        when(v?.id){


            R.id.btnSave -> {
                isAllFieldsChecked = confirmation()

                return

            }
        }
    }

    private fun confirmation(): Boolean {
        validateEmailAddress(eMailField)

        binding.apply {

            val email = etEmail.text.toString().trim { it <= ' ' }

            when {
                TextUtils.isEmpty(email) || !validEmail -> {
                    etEmail.error = getString(R.string.err_msg_invalid_email)
                }
                else -> updateUserInputs()
            }

        }
        return true
    }

    private fun updateUserInputs() {

        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra(Constants.FIRST_NAME,binding.etName.text.toString())
        intent.putExtra(Constants.LAST_NAME,binding.etLastName.text.toString())
        intent.putExtra(Constants.UPDATE_USER_INFO,binding.etEmail.text.toString())
        intent.putExtra(Constants.DATE_OF_BIRTH,binding.etDateOfBirth.text.toString())
        intent.putExtra(Constants.GENDER,binding.etGender.text.toString())

        //binding.tvInfo.visibility = View.GONE

        startActivity(intent)


    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDatePicker(view: View) {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { view, year, month, dayOfMonth ->

            val selectedDate = "$dayOfMonth/${month + 1}/$year"

            binding.etDateOfBirth.setText(selectedDate)


        }, year, month, day)

        dpd.datePicker.maxDate = Date().time - 86400000
        dpd.show()
    }

    private fun validateEmailAddress(eMailField: EditText?) {
        val emailToText = eMailField!!.text.toString()


        if (emailToText.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            validEmail = true
            binding.etEmail.error = null
        } else {
            validEmail = false
            binding.etEmail.error = getString(R.string.err_msg_invalid_email)


        }

    }

    companion object {
        var validEmail = false
    }


}