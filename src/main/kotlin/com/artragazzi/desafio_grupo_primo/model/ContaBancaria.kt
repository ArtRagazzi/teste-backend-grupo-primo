package com.artragazzi.desafio_grupo_primo.model

import jakarta.persistence.*


@Entity
@Table(name = "tb_conta_bancaria")
data class ContaBancaria(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val numberoConta: Long =0,

    @Column
    var saldo:Double
)