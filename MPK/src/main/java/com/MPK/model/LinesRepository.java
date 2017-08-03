package com.MPK.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LinesRepository extends CrudRepository<Line, Integer> {

    @Query("select l from Line l where l.number = :number")
    Line findByNumber(@Param("number") String number);


}
