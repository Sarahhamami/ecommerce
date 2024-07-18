package com.example.ecom.service;

import com.example.ecom.domain.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopics();
    Topic getTopic(Long id);
    void addTopic(Topic topic);
    void updateTopic(Long id, Topic topic);
    void deleteTopic(Long id);
}
