package com.day.projectmanagementsystem.service;

import com.day.projectmanagementsystem.modal.Chat;
import com.day.projectmanagementsystem.modal.Project;
import com.day.projectmanagementsystem.modal.User;
import com.day.projectmanagementsystem.repoistory.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createProject = new Project();

        createProject.setOwner(user);
        createProject.setTags(project.getTags());
        createProject.setName(project.getName());
        createProject.setCategory(project.getCategory());
        createProject.setDescription(project.getDescription());
        createProject.getTeam().add(user);

        Project savedProject = projectRepository.save(createProject);

        Chat chat = new Chat();
        chat.setProject(savedProject);

        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);

        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);

        if (category != null) {
         projects = projects.stream().filter(project -> project.getCategory().equals(category))
                 .collect(Collectors.toList());
        }
        if (tag != null) {
            projects = projects.stream().filter(project -> project.getTags().equals(tag))
                    .collect(Collectors.toList());
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty()) {
            throw new Exception("project not found");
        }
        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        getProjectById(projectId);
//        User user = userService.findUserById(userId);
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = getProjectById(id);

        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());

        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {

    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {

    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        return null;
    }
}
