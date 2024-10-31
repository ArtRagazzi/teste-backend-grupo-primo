package com.artragazzi.desafio_grupo_primo.model

import com.artragazzi.desafio_grupo_primo.model.enuns.TipoTransacao
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "tb_transacao")
data class Transacao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "conta-origem")
    val contaOrigem: ContaBancaria? = null,

    @ManyToOne
    @JoinColumn(name = "conta-destino")
    val contaDestino: ContaBancaria? = null,

    @Column(nullable = false)
    val valor: Double,

    @Column(nullable = false)
    val dataHora: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val tipo: TipoTransacao
)
