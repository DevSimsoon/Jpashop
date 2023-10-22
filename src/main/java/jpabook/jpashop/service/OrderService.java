package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회 -> 트랜잭션 안에서 엔티티를 조회해야 영속 상태가 유지된다.
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);    // cascade 옵션으로 인해 delivery와 orderItem도 같이 저장된다.

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
        // JPA의 변경내역 감지에 의해서 Entity의 바뀐 필드값을 읽어 update query가 날라간다.
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }

    // 주문 서비스 + 주문 + 주문 취소 메서드
    // -> 위 3개의 비즈니스 로직의 대부분 엔티티에 있음.
    // 서비스 계층은 단순하게 엔티티에 필요한 요청을 "위임"

    // 엔티티가 비즈니스 로직을 가지고 객체지향의 특성을 살리거나 활용하는것은 " 도메인 모델 패턴 "

    // 위와 반대되는것은 엔티티에는 비즈니스로직이 없고 서비스계층에서 대부분
    // 비즈니스 로직을 처리하는 것 = " 트랜잭션 스크립트 패턴 "

}
