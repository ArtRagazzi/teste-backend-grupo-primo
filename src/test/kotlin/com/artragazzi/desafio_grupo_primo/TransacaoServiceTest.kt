package com.artragazzi.desafio_grupo_primo

import com.artragazzi.desafio_grupo_primo.service.ContaBancariaService
import com.artragazzi.desafio_grupo_primo.service.TransacaoService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TransacaoServiceTest {
    @Autowired
    lateinit var contaBancariaService: ContaBancariaService

    @Autowired
    lateinit var transacaoService: TransacaoService

    @Test
    fun testarSaqueConcorrente() = runBlocking {
        val conta = contaBancariaService.criarConta(1000.0)

        val jobs = List(2) {
            launch {
                try {
                    transacaoService.sacar(conta.numberoConta, 600.0)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        jobs.forEach { it.join() }

        val contaAtualizada = contaBancariaService.contaBancariaRepository.findById(conta.numberoConta).orElseThrow()
        assertTrue(contaAtualizada.saldo >= 0)
        assertTrue(contaAtualizada.saldo <= 1000.0)
    }

    @Test
    fun testarDepositoConcorrente() = runBlocking {
        val conta = contaBancariaService.criarConta(0.0)

        val jobs = List(3) {
            launch {
                transacaoService.depositar(conta.numberoConta, 300.0)
            }
        }
        jobs.forEach { it.join() }

        val contaAtualizada = contaBancariaService.contaBancariaRepository.findById(conta.numberoConta).orElseThrow()
        assertEquals(900.0, contaAtualizada.saldo)
    }



    @Test
    fun testarTransferenciaConcorrente() = runBlocking {
        val contaA = contaBancariaService.criarConta(1000.0)
        val contaB = contaBancariaService.criarConta(500.0)

        val jobs = List(2) {
            launch {
                try {
                    transacaoService.transferir(contaA.numberoConta, contaB.numberoConta, 400.0)
                } catch (e: Exception) {
                    // Handle exception if needed
                }
            }
        }
        jobs.forEach { it.join() }

        val contaAAposTransferencia = contaBancariaService.contaBancariaRepository.findById(contaA.numberoConta).orElseThrow()
        val contaBAposTransferencia = contaBancariaService.contaBancariaRepository.findById(contaB.numberoConta).orElseThrow()

        assertTrue(contaAAposTransferencia.saldo >= 0)
        assertTrue(contaAAposTransferencia.saldo <= 1000.0)
        assertTrue(contaBAposTransferencia.saldo <= 900.0)
    }


}