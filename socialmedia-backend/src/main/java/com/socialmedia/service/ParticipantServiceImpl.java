package com.socialmedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.socialmedia.model.Participants;
import com.socialmedia.model.Users;
import com.socialmedia.repository.ParticipantRepository;
import com.socialmedia.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    private ParticipantRepository  repository;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Participants> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Participants> getParticipantsByConversationId(int id) {
        return repository.findByConversationId(id);
    }

    @Override
    public List<Participants> getFriendChattingWith(int user_id) {
        // Get những conversation của user_id tham gia
        Optional<Users> currentUser = userRepository.findById(user_id);
        List<Participants> participantsByUserId = repository.findByUser(currentUser.get());
        List<Participants> result = new ArrayList<>();
        List<Participants> tempList = new ArrayList<>();
        for(Participants par : participantsByUserId) {
            tempList = repository.findByConversationId(par.getConversation().getId());
            for(Participants tempPar : tempList) {
                if(tempPar.getUser().getId() != user_id) {
                    result.add(tempPar);
                }
            }
        }
        return result;
    }

    @Override
    public List<Participants> getConversationJoinedByUserId(int user_id) {
        Users user = userRepository.findById(user_id).get();
        return repository.findByUser(user);
    }

    @Override
    public Participants save(Participants participant) {
        return repository.save(participant);
    }
}