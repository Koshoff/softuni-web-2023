package com.example.CryptoTreasures.repository;

import com.example.CryptoTreasures.model.entity.CryptoCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCardRepository extends JpaRepository<CryptoCard, Long> {

}
