package com.day.projectmanagementsystem.controller;

import com.day.projectmanagementsystem.modal.Chat;
import com.day.projectmanagementsystem.modal.Message;
import com.day.projectmanagementsystem.modal.User;
import com.day.projectmanagementsystem.request.CreateCommentRequest;
import com.day.projectmanagementsystem.request.CreateMessageRequest;
import com.day.projectmanagementsystem.service.MessageService;
import com.day.projectmanagementsystem.service.ProjectService;
import com.day.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request)
        throws Exception {
        User user = userService.findUserById(request.getSenderId());
        if (user == null) {
            throw new Exception("user not found with id " + request.getSenderId());
        }
        Chat chats = projectService.getProjectById(request.getProjectId()).getChat();
        if (chats == null) {
            throw new Exception("chat not found with id " + request.getProjectId());
        }
        Message sentMessage = messageService.sendMessage(request.getSenderId(),
                request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long projectId) throws Exception {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
