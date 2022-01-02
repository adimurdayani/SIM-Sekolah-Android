package com.example.simsekolah.core.data.model

class Ulangan {
    var id = 0
    var siswa_id = 0
    var kelas_id = 0
    var guru_id = 0
    var mapel_id = 0
    var ulha_1 = ""
    var ulha_2 = ""
    var uts = ""
    var ulha_3 = ""
    var uas = ""
    var created_at = ""
    var updated_at = ""

    var siswa = Siswa()
    var kelas = Kelas()
    var guru = Guru()
    var mapel = Mapel()
}