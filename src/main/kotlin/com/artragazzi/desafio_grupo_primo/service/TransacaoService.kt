package com.artragazzi.desafio_grupo_primo.service

import com.artragazzi.desafio_grupo_primo.model.Transacao
import com.artragazzi.desafio_grupo_primo.model.enuns.TipoTransacao
import com.artragazzi.desafio_grupo_primo.repositories.ContaBancariaRepository
import com.artragazzi.desafio_grupo_primo.repositories.TransacaoRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional



@Service
class TransacaoService {

    @Autowired
    private lateinit var contaBancariaRepository: ContaBancariaRepository
    @Autowired
    private lateinit var transacaoRepository: TransacaoRepository
    private val logs = LoggerFactory.getLogger((Transacao::class.java))
    private val mutex = Mutex()

    @Transactional
    suspend fun depositar(numConta:Long, valor:Double){
        mutex.withLock {
            logs.info("Deposito inciado: Conta $numConta, valor: R$ $valor")
            val conta = contaBancariaRepository.findById(numConta).orElseThrow{IllegalArgumentException("Conta nao encontrada")}
            conta.saldo += valor
            contaBancariaRepository.save(conta)

            logs.info("Deposito realizado: Conta $numConta, novo Saldo: R$ ${conta.saldo}")

            transacaoRepository.save(Transacao(contaOrigem = conta, valor = valor, tipo = TipoTransacao.DEPOSITO))

        }
    }

    @Transactional
    suspend fun sacar(numConta: Long, valor: Double){
        mutex.withLock {

            logs.info("Saque inciado: Conta $numConta, valor: R$ $valor")
            val conta = contaBancariaRepository.findById(numConta).orElseThrow { IllegalArgumentException("Conta nao encontrada") }

            if(conta.saldo < valor){
                throw IllegalArgumentException("Saldo insuficiente para realizar o saque")
            }
            conta.saldo-=valor
            contaBancariaRepository.save(conta)

            logs.info("Saque Realizado: Conta $numConta, novo saldo: R$ ${conta.saldo}")
            transacaoRepository.save(Transacao(contaOrigem = conta, valor = -valor, tipo = TipoTransacao.SAQUE))

        }
    }

    @Transactional
    suspend fun transferir(numContaOrigem: Long, numContaDestino:Long, valor:Double){
        mutex.withLock {

            logs.info("Transferencia inciada: Conta origem $numContaOrigem, Conta destino $numContaDestino,  valor: R$ $valor")

            val contaOrigem = contaBancariaRepository.findById(numContaOrigem).orElseThrow { IllegalArgumentException("Conta origem nao encnotrada") }
            val contaDestino = contaBancariaRepository.findById(numContaDestino).orElseThrow { IllegalArgumentException("Conta destino nao encontrada") }
            if(contaOrigem.saldo < valor){
                throw IllegalArgumentException("Saldo insuficiente para realizar a transferencia")
            }

            contaOrigem.saldo-=valor
            contaDestino.saldo+=valor

            logs.info("Tranferencia Realizada: Conta Origem: $contaOrigem | ${contaOrigem.saldo}\n$contaDestino | ${contaDestino.saldo}")

            transacaoRepository.save(Transacao(contaOrigem = contaOrigem, contaDestino = contaDestino, valor = valor, tipo = TipoTransacao.TRANSFERENCIA))


        }
    }
}