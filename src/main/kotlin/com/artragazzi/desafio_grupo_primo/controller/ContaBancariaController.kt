package com.artragazzi.desafio_grupo_primo.controller

import com.artragazzi.desafio_grupo_primo.model.ContaBancaria
import com.artragazzi.desafio_grupo_primo.service.ContaBancariaService
import com.artragazzi.desafio_grupo_primo.service.TransacaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/contas")
class ContaBancariaController {

    @Autowired
    private lateinit var transacaoService: TransacaoService
    @Autowired
    private lateinit var contaBancariaService: ContaBancariaService

    @PostMapping
    suspend fun criarContaBancaria(@RequestParam saldo: Double): ResponseEntity<ContaBancaria> {
        return try {
            val novaConta = contaBancariaService.criarConta(saldoInicial = saldo)
            ResponseEntity(novaConta, HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
    }

    @PostMapping("/{numeroConta}/deposito")
    suspend fun depositar(@PathVariable numeroConta:Long,@RequestParam valor:Double){
        transacaoService.depositar(numeroConta,valor)
    }

    @PostMapping("/{numeroConta}/saque")
    suspend fun sacar(@PathVariable numeroConta: Long, @RequestParam valor: Double){
        transacaoService.sacar(numeroConta,valor)
    }
    @PostMapping("/transferencia")
    suspend fun transferir(@RequestParam numContaOrigem:Long, @RequestParam numContaDestino:Long, @RequestParam valor: Double){
        transacaoService.transferir(numContaOrigem = numContaOrigem, numContaDestino = numContaDestino, valor = valor)
    }

}