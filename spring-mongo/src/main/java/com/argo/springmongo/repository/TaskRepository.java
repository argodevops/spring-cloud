package com.argo.springmongo.repository;

import com.argo.springmongo.*;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
  Task findByid(String id);

  List<Task> findByPriority(PriorityType priority);

  List<Task> findByCompleted(Boolean completed);

  List<Task> findByOrderByPriority();

  List<Task> findByCompletedOrderByPriorityDesc(Boolean completed);
}
