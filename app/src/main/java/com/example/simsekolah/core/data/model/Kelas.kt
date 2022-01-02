package com.example.simsekolah.core.data.model

class Kelas {
    var id = 0
    var nama_kelas = ""
    var paket_id = 0
    var guru_id = 0
    var created_at = ""
    var updated_at = ""
    var deleted_at = ""

    var guru = Guru()
    var paket = Paket()
}