package com.argo.springmongo;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
  public Task findByid(String id);

  public List<Task> findByPriority(PriorityType priority);

  public List<Task> findByCompleted(Boolean completed);

  public List<Task> findByOrderByPriority();
}
