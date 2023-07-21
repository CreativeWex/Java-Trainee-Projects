package io.ylab.intensive.lesson04.eventsourcing.db;
/*
    =====================================
    @project Ylab
    @created 27/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import io.ylab.intensive.lesson04.eventsourcing.Person;

public interface Dao {
  public void save(Person person);

  public void delete(Long id);
}
