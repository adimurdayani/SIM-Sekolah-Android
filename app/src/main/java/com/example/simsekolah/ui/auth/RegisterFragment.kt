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

class RegisterFragment : Fragment() {
    lateinit var e_email: EditText
    lateinit var e_password: EditText
    lateinit var e_konfirmasi_password: EditText
    lateinit var level: Spinner
    lateinit var btn_register: LinearLayout
    lateinit var btn_kembali: ImageView
    lateinit var text_register: TextView
    lateinit var progress: ProgressBar
    lateinit var email: String
    lateinit var password: String
    lateinit var role: String
    lateinit var nomer: String
    lateinit var s: SharedPref
    lateinit var e_id_card: EditText
    lateinit var e_no_induk: EditText
    lateinit var div_no_induk: LinearLayout
    lateinit var div_id_card: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)
        s = SharedPref(requireActivity())
        setInit(view)
        setButton()
        setSpinner()
        return view
    }

    private fun setSpinner() {
        level.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                role = level.selectedItem.toString()

                if (role == "Guru") {
                    div_id_card.visibility = View.VISIBLE
                    div_no_induk.visibility = View.GONE
                } else if (role == "Siswa") {
                    div_id_card.visibility = View.GONE
                    div_no_induk.visibility = View.VISIBLE
                } else {
                    div_id_card.visibility = View.GONE
                    div_no_induk.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                setNoselected("Anda harus memilih salah satu level")
            }

        }
    }

    private fun setNoselected(pesan: String) {
        var alertDialog: LottieAlertDialog =
            LottieAlertDialog.Builder(requireActivity(), DialogTypes.TYPE_WARNING)
                .setTitle("Maaf")
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

    private fun setButton() {
        btn_kembali.setOnClickListener {
            val fragment = LoginFragment()
            val frm = fragmentManager?.beginTransaction()
            frm!!.replace(R.id.frm_login, fragment)
            frm.commit()
        }
        btn_register.setOnClickListener {
            if (validasi()) {
                register()
            }
        }
    }

    private fun register() {
        email = e_email.text.toString()
        password = e_password.text.toString()
        nomer = e_no_induk.text.toString()
        nomer = e_id_card.text.toString()

        progress.visibility = View.VISIBLE
        text_register.visibility = View.GONE
        ApiConfig.instanceRetrofit.register(email, role, nomer, password, e_konfirmasi_password.text.toString())
            .enqueue(object : Callback<ResponsModel> {
                override fun onResponse(
                    call: Call<ResponsModel>,
                    response: Response<ResponsModel>
                ) {
                    progress.visibility = View.GONE
                    text_register.visibility = View.VISIBLE
                    val respon = response.body()!!
                    if (respon.success == 1) {
                        sukses("Register akun telah berhasil!")
                    } else {
                        progress.visibility = View.GONE
                        text_register.visibility = View.VISIBLE
                        setError(respon.message)
                    }
                }

                override fun onFailure(call: Call<ResponsModel>, t: Throwable) {
                    progress.visibility = View.GONE
                    text_register.visibility = View.VISIBLE
                    setError(t.message.toString())
                }
            })
    }

    private fun sukses(pesan: String) {
        var alertDialog: LottieAlertDialog =
            LottieAlertDialog.Builder(requireActivity(), DialogTypes.TYPE_SUCCESS)
                .setTitle("Sukses")
                .setDescription(pesan)
                .setPositiveTextColor(Color.WHITE)
                .setPositiveText("Ok")
                .setPositiveListener(object : ClickListener {
                    override fun onClick(dialog: LottieAlertDialog) {
                        val fragment = LoginFragment()
                        val frm = fragmentManager?.beginTransaction()
                        frm!!.replace(R.id.frm_login, fragment)
                        frm.commit()
                        dialog.dismiss()
                    }
                })
                .build()
        alertDialog.setCancelable(false)
        alertDialog.show()
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
        } else if (e_password.text.toString().length < 8) {
            e_password.error = "Password tidak boleh kurang dari 8 karakter!"
            e_password.requestFocus()
            return false
        }
        if (e_konfirmasi_password.text.toString().isEmpty()) {
            e_konfirmasi_password.error = "Kolom password tidak boleh kosong!"
            e_konfirmasi_password.requestFocus()
            return false
        } else if (!e_konfirmasi_password.text.toString()
                .matches(e_password.text.toString().toRegex())
        ) {
            e_konfirmasi_password.error = "Konfirmasi password tidak sama dengan password!"
            e_konfirmasi_password.requestFocus()
            return false
        } else if (e_konfirmasi_password.text.toString().length < 6) {
            e_konfirmasi_password.error = "Password tidak boleh kurang dari 6 karakter!"
            e_konfirmasi_password.requestFocus()
            return false
        }
        return true
    }

    private fun setInit(view: View) {
        e_email = view.findViewById(R.id.e_email)
        e_password = view.findViewById(R.id.e_password)
        e_konfirmasi_password = view.findViewById(R.id.e_konfirmasi_password)
        level = view.findViewById(R.id.level)
        btn_register = view.findViewById(R.id.btn_register)
        text_register = view.findViewById(R.id.text_register)
        progress = view.findViewById(R.id.progress)
        btn_kembali = view.findViewById(R.id.btn_kembali)
        e_id_card = view.findViewById(R.id.e_id_card)
        e_no_induk = view.findViewById(R.id.e_no_induk)
        div_no_induk = view.findViewById(R.id.div_no_induk)
        div_id_card = view.findViewById(R.id.div_id_card)
    }
}