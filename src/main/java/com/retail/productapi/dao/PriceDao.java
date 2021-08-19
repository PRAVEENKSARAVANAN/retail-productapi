package com.retail.productapi.dao;

import com.retail.productapi.entity.Price;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO for the Price
 */
@Repository
public interface PriceDao extends CassandraRepository<Price, Long> {

}
