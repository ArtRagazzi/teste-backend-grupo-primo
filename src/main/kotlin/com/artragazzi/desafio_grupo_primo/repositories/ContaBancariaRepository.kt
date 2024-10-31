package com.artragazzi.desafio_grupo_primo.repositories

import com.artragazzi.desafio_grupo_primo.model.ContaBancaria
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContaBancariaRepository: JpaRepository<ContaBancaria,Long>