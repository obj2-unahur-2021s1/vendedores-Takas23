package ar.edu.unahur.obj2.vendedores

import java.lang.Exception

class Centro(val ciudad: Ciudad) {
    val vendedores = mutableListOf<Vendedor>()

    fun agregarVendedor(vendedor: Vendedor) {
        if (!vendedores.contains(vendedor)) {
            vendedores.add(vendedor)
        } else throw Exception("El vendedor ya esta incluido")
    }

    //mal, revisar
    fun vendedorEstrella() =
        vendedores.map { v -> v.puntajeCertificaciones() }.max()

    fun puedeCubrir(ciudad: Ciudad) =
        vendedores.any { v -> v.puedeTrabajarEn(ciudad) }

    fun vendedoresGenericos() =
        vendedores.filter { v -> v.otrasCertificaciones() > 0 }

    fun esRobusto() =
        vendedores.count { v -> v.esFirme() } >= 3

}