package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.booleans.shouldNotBeFalse
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class VendedorTest : DescribeSpec({
  val misiones = Provincia(1300000)
  val sanIgnacio = Ciudad(misiones)
  val obera = Ciudad(misiones)
  val cordoba = Provincia(2000000)
  val villaDolores = Ciudad(cordoba)
  val bsAs = Provincia(15000000)
  val buenosAires = Ciudad(bsAs)
  val certP1 = Certificacion(true, 10)
  val certP2 = Certificacion(true, 10)
  val certG1 = Certificacion(false, 5)
  val certG2 = Certificacion(false, 5)
  val certG3 = Certificacion(false,5)

  describe("Vendedor fijo") {
//    val obera = Ciudad(misiones)
    val vendedorFijo = VendedorFijo(obera)


    describe("puedeTrabajarEn") {
      it("su ciudad de origen") {
        vendedorFijo.puedeTrabajarEn(obera).shouldBeTrue()
      }
      it("otra ciudad") {
        vendedorFijo.puedeTrabajarEn(sanIgnacio).shouldBeFalse()
      }
    }

    describe("es influyente") {
      it("no influyente") {
        vendedorFijo.esInfluyente().shouldBeFalse()
      }
    }

    describe("certificaciones") {
      vendedorFijo.agregarCertificacion(certP1)
      vendedorFijo.agregarCertificacion(certG1)
      it("no es versatil, 2 cert"){
        vendedorFijo.esVersatil().shouldBeFalse()
      }
      vendedorFijo.agregarCertificacion(certP2)
      it("es versatil 3 cert"){
        vendedorFijo.esVersatil().shouldBeTrue()
      }
      it("no es firme, 25 pts"){
        vendedorFijo.esFirme().shouldBeFalse()
      }
      vendedorFijo.agregarCertificacion(certG2)
      it("es firme, 30 pts"){
        vendedorFijo.esFirme().shouldBeTrue()
      }
    }
  }

  describe("Viajante") {
//    val cordoba = Provincia(2000000)
//    val villaDolores = Ciudad(cordoba)
    val viajante = Viajante(listOf(misiones))
    val viajanteInf = Viajante(listOf(bsAs))

    describe("puedeTrabajarEn") {
      it("una ciudad que pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(sanIgnacio).shouldBeTrue()
      }
      it("una ciudad que no pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(villaDolores).shouldBeFalse()
      }
    }

    describe("influyente") {
      it("no influyente 1.3M") {
        viajante.esInfluyente().shouldBeFalse()
      }
      it("influyente 15M") {
        viajanteInf.esInfluyente().shouldBeTrue()
      }
    }

  }

  describe("Comercio") {
    val comercio = ComercioCorresponsal(listOf(obera, sanIgnacio))
    val comercioInf = ComercioCorresponsal(listOf(obera, villaDolores, buenosAires))

    describe("puedeTrabajarEn") {
      it("una ciudad que pertenece a una provincia habilitada") {
        comercio.puedeTrabajarEn(obera).shouldBeTrue()
      }
      it("una ciudad que no pertenece a una provincia habilitada") {
        comercio.puedeTrabajarEn(villaDolores).shouldBeFalse()
      }
    }

    describe("influyente") {
      it("no influyente, 2 ciudades") {
        comercio.esInfluyente().shouldBeFalse()
      }
      it("influyente, 3 ciu/3 prov") {
        comercioInf.esInfluyente().shouldBeTrue()
      }
    }
  }

  describe("centros"){
    val fijo1 = VendedorFijo(villaDolores)
    val fijo2 = VendedorFijo(buenosAires)
    val viajante1 = Viajante(listOf(bsAs,cordoba))
    val viajante2 = Viajante(listOf(misiones))
    val viajante3 = Viajante(listOf(bsAs))
    val centro1 = Centro(buenosAires)

    fijo1.agregarCertificacion(certP1)
    viajante1.agregarCertificacion(certP2)
    fijo2.agregarCertificacion(certG1)
    fijo2.agregarCertificacion(certG2)
    fijo2.agregarCertificacion(certP1)
    fijo2.agregarCertificacion(certP2)
    viajante2.agregarCertificacion(certG1)
    viajante2.agregarCertificacion(certG2)
    viajante2.agregarCertificacion(certP1)
    viajante2.agregarCertificacion(certP2)
    viajante3.agregarCertificacion(certG1)
    viajante3.agregarCertificacion(certG2)
    viajante3.agregarCertificacion(certP1)
    viajante3.agregarCertificacion(certP2)
    viajante3.agregarCertificacion(certG3)


    describe("vendedores") {
      centro1.agregarVendedor(fijo1)
      centro1.agregarVendedor(fijo2)
      centro1.agregarVendedor(viajante1)
      centro1.agregarVendedor(viajante3)
/*
      it("estrella viajante3 35pts") {
        centro1.vendedorEstrella().shouldBe(viajante3)
      }
*/
      it("puede cubrir") {
        centro1.puedeCubrir(villaDolores).shouldBeTrue()
      }
      it("no puede cubrir") {
        centro1.puedeCubrir(obera).shouldBeFalse()
      }

      it("vendedores genericos") {
        centro1.vendedoresGenericos().shouldContainAll(fijo2,viajante3)
      }

      it("no es Robusto, 2 firmes") {
        centro1.esRobusto().shouldBeFalse()
      }
      it("es robusto, agregado 1 firme") {
        centro1.agregarVendedor(viajante2)
        centro1.esRobusto().shouldBeTrue()
      }

    }
  }
})
