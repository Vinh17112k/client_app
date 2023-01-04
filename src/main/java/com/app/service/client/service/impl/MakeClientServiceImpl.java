package com.app.service.client.service.impl;

import com.app.service.client.model.Make;
import com.app.service.client.repository.MakeRepository;
import com.app.service.client.service.MakeClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class MakeClientServiceImpl implements MakeClientService {
    private final MakeRepository makeRepository;
    @Override
    public List<Make> getList() {
        return makeRepository.findAll();
    }
}
