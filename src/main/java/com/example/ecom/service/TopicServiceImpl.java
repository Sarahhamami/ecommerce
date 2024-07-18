package com.example.ecom.service;

import com.example.ecom.CustomException.ResourceNotFoundException;
import com.example.ecom.domain.Topic;
import com.example.ecom.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public Topic getTopic(Long id) {
        return topicRepository.findById(id).orElse(null);
    }

    @Override
    public void addTopic(Topic topic) {
        topicRepository.save(topic);
    }

    @Override
    public void updateTopic(Long id, Topic topic) {
        Topic existingTopic = topicRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topic not found with id " + id));
        existingTopic.setName(topic.getName());
        existingTopic.setDescription(topic.getDescription());
        topicRepository.save(existingTopic);
    }

    @Override
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }
}
