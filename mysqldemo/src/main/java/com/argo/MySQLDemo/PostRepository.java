package com.argo.MySQLDemo;

import org.springframework.data.repository.CrudRepository;
import com.argo.MySQLDemo.Post;

public interface PostRepository extends CrudRepository<Post, Integer> {

}