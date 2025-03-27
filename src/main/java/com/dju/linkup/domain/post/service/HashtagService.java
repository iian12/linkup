package com.dju.linkup.domain.post.service;

import com.dju.linkup.domain.post.model.Hashtag;
import com.dju.linkup.domain.post.repository.HashtagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    public HashtagService(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    public List<String> findOrCreateHashtags(List<String> names) {
        return names.stream().map(name -> {
            Hashtag hashtag = hashtagRepository.findByName(name)
                    .orElseGet(() -> hashtagRepository.save(
                            Hashtag.builder()
                                    .name(name)
                                    .build()));
            hashtag.addCount();
            hashtagRepository.save(hashtag);

            return hashtag.getId();
        }).toList();
    }
}
