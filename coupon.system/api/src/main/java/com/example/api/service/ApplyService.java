package com.example.api.service;

import com.example.api.producer.CouponCreateProducer;
import com.example.api.repository.CouponCountRepository;
import com.example.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {
    private final CouponRepository couponRepository;

    private final CouponCountRepository couponCountRepository;

    private final CouponCreateProducer couponCreateProducer;


    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
    }

    public void apply(Long userId){
        Long count = couponCountRepository.increment(); // 쿠폰 발급 전 쿠폰 발급 갯수를 증가 시킨다

        // 쿠폰 카운트가 100개가 넘는다면
        if(count > 100){
            return;
        }

        // RDB 방식은 부하를 줄 수 있음
//        couponRepository.save(new Coupon(userId));

        couponCreateProducer.create(userId);
    }
}
