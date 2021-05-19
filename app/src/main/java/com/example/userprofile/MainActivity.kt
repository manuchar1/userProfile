package com.example.userprofile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.userprofile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userInputs()


        binding.btnUpdate.setOnClickListener {
            updateUser()
        }
    }

    private fun updateUser(){

        val intent = Intent(this,UserEntriesActivity::class.java)

        binding.apply {
            intent.putExtra(Constants.FIRST_NAME, tvName.text.toString())
            intent.putExtra(Constants.LAST_NAME, tvLastName.text.toString())
            intent.putExtra(Constants.EMAIL, tvEmail.text.toString())
            intent.putExtra(Constants.DATE_OF_BIRTH, tvDateOfBirth.text.toString())
            intent.putExtra(Constants.GENDER, tvGender.text.toString())
            intent.putExtra(Constants.PROFILE_PICTURE,R.drawable.wow_nice_fish)

            startActivity(intent)


        }

    }

    private fun userInputs(){



        if (intent.hasExtra(Constants.UPDATE_USER_INFO)) {
         /*   binding.mainTitle.text = "Update User"
            binding.buttonSave.text = "Update "

            binding.buttonBack.visibility = View.VISIBLE*/
            val firstNAme =  intent.getStringExtra(Constants.FIRST_NAME)
            val lastName =  intent.getStringExtra(Constants.LAST_NAME)
            val email = intent.getStringExtra(Constants.UPDATE_USER_INFO)
            val birthDay =  intent.getStringExtra(Constants.DATE_OF_BIRTH)
            val gender =  intent.getStringExtra(Constants.GENDER)



            binding.tvName.text = firstNAme
            binding.tvLastName.text = lastName
            binding.tvEmail.text = email
            binding.tvDateOfBirth.text = birthDay
            binding.tvGender.text = gender

        }

    }

}