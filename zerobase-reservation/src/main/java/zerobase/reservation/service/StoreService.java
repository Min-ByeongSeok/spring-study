package zerobase.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.reservation.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final CustomerRepository customerRepository;


}
