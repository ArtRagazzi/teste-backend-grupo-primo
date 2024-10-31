package com.artragazzi.desafio_grupo_primo.repositories

import com.artragazzi.desafio_grupo_primo.model.Transacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransacaoRepository:JpaRepository<Transacao,Long>
