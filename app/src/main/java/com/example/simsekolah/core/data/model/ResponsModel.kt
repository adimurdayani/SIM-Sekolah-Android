package com.example.simsekolah.core.data.model

class ResponsModel {
    var success = 0
    lateinit var message: String
    var data = User()
    var guru = Guru()
    var siswa = Siswa()
    var data_siswa = Siswa()
    var siswas: ArrayList<Siswa> = ArrayList()
    var kelas: ArrayList<Kelas> = ArrayList()
    var jadwal: ArrayList<Jadwal> = ArrayList()
    var ulangan = Ulangan()
    var mapel: ArrayList<Mapel> = ArrayList()
    var gurus: ArrayList<Guru> = ArrayList()
    var nilai: ArrayList<Nilai> = ArrayList()
}