# java-convenience-store-precourse

## 가. 기능 요구 사항

#### 구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고 안내하는 결제 시스템을 구현한다.

- [ ] 사용자가 입력한 상품의 가격과 수량을 기반으로 최종 결제 금액을 계산한다.
- [ ] 총구매액은 상품별 가격과 수량을 곱하여 계산하며, 프로모션 및 멤버십 할인 정책을 반영하여 최종 결제 금액을 산출한다.
- [ ] 구매 내역과 산출한 금액 정보를 영수증으로 출력한다.
- [ ] 영수증 출력 후 추가 구매를 진행할지 또는 종료할지를 선택할 수 있다.
- [ ] 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException를 발생시키고, "[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.
  Exception이 아닌 IllegalArgumentException, IllegalStateException 등과 같은 명확한 유형을 처리한다.

### 1. 재고 관리

- [ ] 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
- [ ] 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감하여 수량을 관리한다.
- [ ] 재고를 차감함으로써 시스템은 최신 재고 상태를 유지하며, 다음 고객이 구매할 때 정확한 재고 정보를 제공한다.

### 2. 프로모션 할인

- [ ] 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용한다.
- [ ] 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태로 진행된다.
- [ ] 1+1 또는 2+1 프로모션이 각각 지정된 상품에 적용되며, 동일 상품에 여러 프로모션이 적용되지 않는다.
- [ ] 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.
- [ ] 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.
- [ ] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
- [ ] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.

### 3. 멤버십 할인

- [ ] 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
- [ ] 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
- [ ] 멤버십 할인의 최대 한도는 8,000원이다.

### 4. 영수증 출력

- [ ] 영수증은 고객의 구매 내역과 할인을 요약하여 출력한다.
- [ ] 영수증 항목은 아래와 같다.
- [ ] 구매 상품 내역: 구매한 상품명, 수량, 가격
- [ ] 증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록

### 5. 금액 정보

- [ ] 총구매액: 구매한 상품의 총 수량과 총 금액
- [ ] 행사할인: 프로모션에 의해 할인된 금액
- [ ] 멤버십할인: 멤버십에 의해 추가로 할인된 금액
- [ ] 내실돈: 최종 결제 금액
- [ ] 영수증의 구성 요소를 보기 좋게 정렬하여 고객이 쉽게 금액과 수량을 확인할 수 있게 한다.

## 나. 구현 Plan

#### a. 도메인 구현

1. Product
    - 상품의 이름, 가격
2. Promotion
    - 프로모션 이름, n개 구매조건, n개 증정, 프로모션 시작일, 프로모션 종료일
3. Membership
    - 멤버십 할인 퍼센트, 남은 한도, 최대 한도(상수값)
4. Receipt
    - 구매 상품 내역(Product), 증정 상품 내역(Product), 금액 정보(Amount)
5. Payment
    - 지불할 금액
    - 총구매액, 행사할인(Promotion), 멤버십할인(Membership), 최종지불금액
6. Inventory
    - 재고 관리
    - 상품(Product), quantity, 적용 프로모션(Promotion)
7. Order
    - 주문 관리
    - 주문 아이템 내역(OrderItem List)
8. OrderItem
    - 주문 아이템 (낱개)
    - 주문 상품(Product), 주문 갯수(quantity)
9. PurchaseItem, GiftItem, Item
    - 주문 상품, 주문 갯수, 개별 지불 금액

#### b. 비즈니스 로직 구현

1. ProductService
    - 상품 정보 조회
2. OrderService
    - 주문 생성 (주문 생성 시 재고 확인 후 -> 구매 프로세스 진행)
3. InventoryService
    - 재고 개수 확인, 재고 업데이트
4. PurchaseService
    - 구매 프로세스 (프로모션 및 멤버십 적용 -> 영수증 프로세스 진행)
5. PromotionService
    - 프로모션 조건 확인 및 적용
6. MembershipService
    - 멤버십 할인 및 한도 관리
7. ReceiptService
    - 영수증 생성

#### c. 유틸리티 구현

1. MarkdownFileManager
    - 파일 입출력 구현
    - *.md load 및 write
2. InputView
    - 사용자 입력
3. OutputView
    - 콘솔 출력

## 다. 변경 사항

#### v1.0-SNAPSHOT - 2023-11-06
- `PurchaseItem`, `GiftItem`, `Item` 도메인 추가하여 구매 상품 관리
- `OrderItem`로 개별 주문 상품 정보 관리, `Order`에서 주문 내역을 관할하여 여러 상품을 List로 관리
- README에 업데이트 정보 추가
