package com.argo.springmongo;

import com.argo.springmongo.service.*;
import com.argo.springmongo.repository.*;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // These tests probably actually need a container and repository
public class TaskServiceTests {

    @InjectMocks
    private TaskService testTaskService;
    @Mock
    private TaskRepository taskRepository;

    @Test
    void testValidateValidTask() {
        Task testTask = new Task("Run a test");

        assertEquals(Boolean.TRUE, testTaskService.validate(testTask));
    }

    void testValidateInvalidTask() {
        Task nullTextTask = new Task();
        Task emptyTextTask = new Task("");

        assertEquals(Boolean.FALSE, testTaskService.validate(nullTextTask));
        assertEquals(Boolean.FALSE, testTaskService.validate(emptyTextTask));
    }
}
