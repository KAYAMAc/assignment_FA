package com.FA.assignment.dao;

import com.FA.assignment.entity.dto.historyTbl;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface historyDao extends JpaRepository<historyTbl, Long>{

}

