package com.example.simsekolah.core.data.model

class NIlaiJadwal {
    var id = 0
    var hari_id = 0
    var kelas_id = 0
    var guru_id = 0
    var mapel_id = 0
    var jam_mulai = ""
    var jam_selesai = ""
    var ruang_id = 0
    var created_at = ""
    var updated_at = ""
    var deleted_at = ""

    var hari = Hari()
    var kelas = Kelas()
    var mapel: ArrayList<Mapel> = ArrayList()
    var guru = Guru()
}