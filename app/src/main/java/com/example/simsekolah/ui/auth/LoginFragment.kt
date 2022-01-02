package com.example.simsekolah.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.simsekolah.R
import com.example.simsekolah.core.data.model.ResponsModel
import com.example.simsekolah.core.data.source.ApiConfig
import com.example.simsekolah.ui.home.HomeActivity
import com.example.simsekolah.util.SharedPref
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    lateinit var e_email: EditText
    lateinit var e_password: EditText
    lateinit var btn_login: LinearLayout
    lateinit var text_login: TextView
    lateinit var progress: ProgressBar
    lateinit var btn_register: LinearLayout
    lateinit var email: String
    lateinit var password: String
    lateinit var s: SharedPref
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        s = SharedPref(requireActivity())
        setInit(view)
        setButton()
        return view
    }

    private fun setButton() {
        btn_register.setOnClickListener {
            val fragment = RegisterFragment()
            val frm = fragmentManager?.beginTransaction()
            frm!!.replace(R.id.frm_login, fragment)
            frm.commit()
        }
        btn_login.setOnClickListener {
            if (validasi()) {
                login()
            }
        }
    }

    private fun login() {
        email = e_email.text.toString()
        password = e_password.text.toString()

        progress.visibility = View.VISIBLE
        text_login.visibility = View.GONE
        ApiConfig.instanceRetrofit.login(email, password)
            .enqueue(object : Callback<ResponsModel> {
                override fun onResponse(
                    call: Call<ResponsModel>,
                    response: Response<ResponsModel>
                ) {
                    progress.visibility = View.GONE
                    text_login.visibility = View.VISIBLE
                    val respon = response.body()!!
                    if (respon.success == 1) {
                        s.setStatusLogin(true)
                        s.setUser(respon.data)
                        val intent = Intent(requireActivity(), HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        requireActivity().finish()
                    } else {
                        progress.visibility = View.GONE
                        text_login.visibility = View.VISIBLE
                        setError(respon.message)
                    }
                }

                override fun onFailure(call: Call<ResponsModel>, t: Throwable) {
                    progress.visibility = View.GONE
                    text_login.visibility = View.VISIBLE
                    setError(t.message.toString())
                }
            })
    }

    private fun setError(pesan: String) {
        var alertDialog: LottieAlertDialog =
            LottieAlertDialog.Builder(requireActivity(), DialogTypes.TYPE_ERROR)
                .setTitle("Something error")
                .setDescription(pesan)
                .setPositiveText("Oke")
                .setPositiveTextColor(Color.WHITE)
                .setPositiveButtonColor(Color.RED)
                .setPositiveListener(object : ClickListener {
                    override fun onClick(dialog: LottieAlertDialog) {
                        dialog.dismiss()
                    }

                })
                .build()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun validasi(): Boolean {
        if (e_email.text.toString().isEmpty()) {
            e_email.error = "Kolom email tidak boleh kosong!"
            e_email.requestFocus()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(e_email.text.toString()).matches()) {
            e_email.error = "Format email salah!. Contoh: gunakan @example.com"
            e_email.requestFocus()
            return false
        }
        if (e_password.text.toString().isEmpty()) {
            e_password.error = "Kolom password tidak boleh kosong!"
            e_password.requestFocus()
            return false
        } else if (e_password.text.toString().length < 6) {
            e_password.error = "Password tidak boleh kurang dari 6 karakter!"
            e_password.requestFocus()
            return false
        }
        return true
    }

    private fun setInit(view: View) {
        e_email = view.findViewById(R.id.e_email)
        e_password = view.findViewById(R.id.e_password)
        btn_login = view.findViewById(R.id.btn_login)
        text_login = view.findViewById(R.id.text_login)
        progress = view.findViewById(R.id.progress)
        btn_register = view.findViewById(R.id.btn_register)
    }
}