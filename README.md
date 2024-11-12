# java-convenience-store-precourse

## 가. 기능 요구 사항

#### 구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고 안내하는 결제 시스템을 구현한다.

- [x] 사용자가 입력한 상품의 가격과 수량을 기반으로 최종 결제 금액을 계산한다.
- [x] 총구매액은 상품별 가격과 수량을 곱하여 계산하며, 프로모션 및 멤버십 할인 정책을 반영하여 최종 결제 금액을 산출한다.
- [x] 구매 내역과 산출한 금액 정보를 영수증으로 출력한다.
- [x] 영수증 출력 후 추가 구매를 진행할지 또는 종료할지를 선택할 수 있다.
- [x] 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException를 발생시키고, "[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.
  Exception이 아닌 IllegalArgumentException, IllegalStateException 등과 같은 명확한 유형을 처리한다.

### 1. 재고 관리

- [x] 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
- [x] 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감하여 수량을 관리한다.
- [x] 재고를 차감함으로써 시스템은 최신 재고 상태를 유지하며, 다음 고객이 구매할 때 정확한 재고 정보를 제공한다.

### 2. 프로모션 할인

- [x] 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용한다.
- [x] 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태로 진행된다.
- [x] 1+1 또는 2+1 프로모션이 각각 지정된 상품에 적용되며, 동일 상품에 여러 프로모션이 적용되지 않는다.
- [x] 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.
- [x] 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.
- [x] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
- [x] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.

### 3. 멤버십 할인

- [x] 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
- [x] 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
- [x] 멤버십 할인의 최대 한도는 8,000원이다.

### 4. 영수증 출력

- [x] 영수증은 고객의 구매 내역과 할인을 요약하여 출력한다.
- [x] 영수증 항목은 아래와 같다.
- [x] 구매 상품 내역: 구매한 상품명, 수량, 가격
- [x] 증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록

### 5. 금액 정보

- [x] 총구매액: 구매한 상품의 총 수량과 총 금액
- [x] 행사할인: 프로모션에 의해 할인된 금액
- [x] 멤버십할인: 멤버십에 의해 추가로 할인된 금액
- [x] 내실돈: 최종 결제 금액
- [x] 영수증의 구성 요소를 보기 좋게 정렬하여 고객이 쉽게 금액과 수량을 확인할 수 있게 한다.

## 나. 구현 Plan

#### a. 도메인 구현

1. Product
    - 상품의 이름, 가격
2. Promotion
    - 프로모션 이름, n개 구매조건, n개 증정, 프로모션 시작일, 프로모션 종료일
3. Membership
    - 남은 한도
4. Receipt
    - 영수증 출력용 toString override
5. Payment
    - 지불할 금액 정보
    - 총구매액, 행사할인(Promotion), 멤버십할인(Membership), 최종지불금액
6. InventoryItem
    - 재고 정보
    - 상품(Product), quantity, 적용 프로모션(Promotion)
7. Order
    - 주문 관리
    - 주문 아이템 내역(OrderItem List)
8. OrderItem
    - 주문 아이템 (낱개)
    - 주문 상품(Product), 주문 갯수(quantity)
9. PurchaseItem, GiftItem, Item
    - 주문 상품(Product), 주문 갯수, 개별 지불 금액
10. Purchase
    - 구매 내역 정보
    - 구매 상품 내역(PurchaseItems), 증정 상품 내역(GiftItems), 금액 정보(Payment)
11. PurchaseSummary
    - 주문 내역에 대한 요약 정보, 재고 및 주문 요약 정보

#### b. 비즈니스 로직 구현

- **프로세스 1단계**
- OrderProcessor: `프롬프트 출력 -> 상품(Product) 확인 -> 재고(InventoryItem) 확인 -> 주문(Order) 생성`
- **프로세스 2단계**
- PurchaseSummaryProcessor: `PurchaseSummaries 작성`
- **프로세스 3단계**
- OptionalOrderingProcessor: `Optional Order 확인 -> PurchaseSummary 업데이트`
- **프로세스 4단계**
- InventoryProcessor: `Inventory 재고 업데이트`
- **프로세스 5단계**
- PurchaseProcessor: `멤버십(Membership) 할인여부 적용 -> -> 구매(Purchase) 프로세스 진행하여 영수증(Receipt) 출력`

1. ProductService
    - 상품 정보 조회 서비스
2. OrderService
    - 주문 생성 서비스
3. InventoryReadService
    - 재고 load 서비스
4. InventoryWriteService
   - 재고 write 서비스
5. PurchaseService
    - 구매 프로세스
6. PurchaseSummaryService
   - 구매 내역 진행을 위한 요약 정보 서비스
7. PromotionService
    - 프로모션 서비스
8. MembershipService
    - 멤버십 할인 및 한도 관리 서비스
9. AbstractFileReadService
    - 파일 출력 처리 서비스
10. AbstractFileWriteService
   - 파일 입력 처리 서비스

#### c. 유틸리티 구현

1. MarkdownFileReader
    - *.md load
2. MarkdownFileWriter
   - *.md write
3. PromptHandler
   - 화면 입출력 핸들러
4. InputToOrderConverter
    - 입력 String Order 객체 컨버터
5. InputValidator
    - 입력값에 대한 유효성 검사

## 다. 변경 사항

#### v1.0-SNAPSHOT - 2023-11-12 (-develop branch)
- README update

#### v1.0-SNAPSHOT - 2023-11-09
- `PurchaseSummary` 도메인 추가하여 주문에 대한 요약 계산 수행

#### v1.0-SNAPSHOT - 2023-11-06
- `AbstractFileReadService` 추가하여 file read에 대한 관심사 분리, 공통 로직에 대한 처리
- `Purchase`:구매 내역 정보 담게 변경, `Receipt`:영수증 출력 담당 도메인
- `Inventory` -> '`InventoryItem` 클래스명 의미에 맞게 변경
- `PurchaseItem`, `GiftItem`, `Item` 도메인 추가하여 구매 상품 관리
- `OrderItem`로 개별 주문 상품 정보 관리, `Order`에서 주문 내역을 관할하여 여러 상품을 List로 관리
- README에 업데이트 정보 추가


## 라. TODO LIST

#### v1.0-SNAPSHOT - 2023-11-10
- ~~bug: 프로모션 기간이 지나면 일반 가격으로 적용해야하는데 프롬프트 안내 메시지가 출력되고 있음~~
- ~~bug: 행사할인제품과 미제품 동시 구매시 버그 발견, 행사가 적용이 안됨, 첫번째가 증정품이 없으면 증정품이 리스트에 출력이 안되고, 증정품없는 항목이 안내 메시지가 출력됨~~
- ~~bug: `Inventory` 조회 시 날짜 반영 부분 수정 필요~~
- ~~추가적인 버그 수정~~
- ~~README 정리~~
- ~~코드 패키지 정리~~
- 코드 리팩토링

#### v1.0-SNAPSHOT - 2023-11-07
- ~~주문 이후 구매 프로세스 정의~~
- ~~file write 처리 구조 정의 (products.md 업데이트)~~