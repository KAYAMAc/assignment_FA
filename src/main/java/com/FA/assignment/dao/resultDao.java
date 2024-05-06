package com.FA.assignment.dao;

import com.FA.assignment.entity.dto.resultTbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface resultDao extends JpaRepository<resultTbl, Long>{

}
