package com.teststa.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.teststa.adapter.UserAdapter
import com.teststa.databinding.ActivityListUserGithubBinding
import com.teststa.remote.Repo
import com.teststa.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUserGithubActivity : AppCompatActivity() {

    private val binding: ActivityListUserGithubBinding by lazy {
        ActivityListUserGithubBinding.inflate(layoutInflater)
    }

    private var listUserGithub: List<Repo> = emptyList()

    private val karyawanAdapter: UserAdapter by lazy {
        UserAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            listUserGithubRv.adapter = karyawanAdapter

        }

        val apiService = RetrofitClient.apiService

        val call = apiService.fetchRepo()
        manageUi(
            layout = false,
            loading = true,
            error = false
        )
        call.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(
                call: Call<List<Repo>>,
                response: Response<List<Repo>>
            ) {
                if (response.isSuccessful) {
                    listUserGithub = response.body() ?: emptyList()
                    karyawanAdapter.updateData(listUserGithub)
                    manageUi(
                        layout = true,
                        loading = false,
                        error = false
                    )
                } else {
                    manageUi(
                        layout = false,
                        loading = false,
                        error = true
                    )
                    binding.errorTxt.text = response.message()
                }
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                manageUi(
                    layout = false,
                    loading = false,
                    error = true
                )
                binding.errorTxt.text = t.message
            }
        })

    }

    private fun manageUi(
        layout: Boolean,
        loading: Boolean,
        error: Boolean
    ) {
        binding.apply {
            layoutLUG.visibility = if (layout) View.VISIBLE else View.INVISIBLE
            loadingPB.visibility = if (loading) View.VISIBLE else View.INVISIBLE
            errorTxt.visibility = if (error) View.VISIBLE else View.INVISIBLE
        }
    }
}