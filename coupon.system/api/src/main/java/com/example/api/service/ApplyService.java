package com.example.api.service;

import com.example.api.producer.CouponCreateProducer;
import com.example.api.repository.AppliedUserRepository;
import com.example.api.repository.CouponCountRepository;
import com.example.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {
    private final CouponRepository couponRepository;

    private final CouponCountRepository couponCountRepository;

    private final CouponCreateProducer couponCreateProducer;

    private final AppliedUserRepository appliedUserRepository;


    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer, AppliedUserRepository appliedUserRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
        this.appliedUserRepository = appliedUserRepository;
    }

    public void apply(Long userId){
        Long apply = appliedUserRepository.add(userId);

        // apply가 1이 아니라면 이미 발급을 한 유저
        if(apply != 1){
            return;
        }

        Long count = couponCountRepository.increment(); // 쿠폰 발급 전 쿠폰 발급 갯수를 증가 시킨다

        // 쿠폰 카운트가 100개가 넘는다면
        if(count > 100){
            System.out.println("쿠폰 갯수 발급 초과!");
            System.out.println(count);
            return;
        }

        // RDB 방식은 부하를 줄 수 있음
//        couponRepository.save(new Coupon(userId));

        couponCreateProducer.create(userId);
    }
}
