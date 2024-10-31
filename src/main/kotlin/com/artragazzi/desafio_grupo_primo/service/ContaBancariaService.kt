package com.artragazzi.desafio_grupo_primo.service

import com.artragazzi.desafio_grupo_primo.model.ContaBancaria
import com.artragazzi.desafio_grupo_primo.model.Transacao
import com.artragazzi.desafio_grupo_primo.repositories.ContaBancariaRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContaBancariaService {

    @Autowired
    lateinit var contaBancariaRepository: ContaBancariaRepository
    private val logs = LoggerFactory.getLogger((Transacao::class.java))

    @Transactional
    fun criarConta(saldoInicial: Double):ContaBancaria{
        logs.info("Criacao de conta inciado: saldo inicial: R$ $saldoInicial")
        val novaConta = ContaBancaria(saldo = saldoInicial)
        return contaBancariaRepository.save(novaConta)
    }


}