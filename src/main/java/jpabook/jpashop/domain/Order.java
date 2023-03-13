package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 직접 객체를 생성하지 못하게 제약한다. (동영상 주문서비스 개발 10분쯤)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 여러개의 물건 하나의 고객
    @JoinColumn(name = "member_id") // FK설정
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = ALL)    // cascade로 인해 (상속받는 객체??)모두다 persist가 된다.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;    // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    // 연관관계 메서드, 양방향 관계는 해줘야한다. (mappedby 있으면 하는거 같다)
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    // 연관관계 메서드, 양방향 관계는 해줘야한다.
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){ // ...은 가변인자이다.
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // 비즈니즈 로직
    // 주문 취소
    public void cancel(){
        if (delivery.getStatus() == DeliveryStatus.COMP){   // 이미 delivery의 상태가 배송완료(COMP)이면
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    // 조회 로직
    public int getTotalPrice(){ // 전체 주문 가격 조회
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){ // for문장을 인텔리제이가 깔끔하게 바꿔준다. alt + enter
            totalPrice += orderItem.getTotalPrice();
        }
        return  totalPrice;
    }






























}
