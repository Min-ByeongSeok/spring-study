### 의존성 주입(Dependency Injection)의 개념과 필요성

> ### A가 B를 의존한다
> 의존대상 B가 변하면 그것이 A에 영향을 미친다.

#### 의존성 주입이란?
- 외부에서 두 객체 간의 관계를 결정해주는 디자인 패턴
- 인터페이스를 사이에 둬서 클래스 레벨 에서는 의존관계가 고정되지 않도록 하고 런타임 시에 관계를 동적으로 주입

```java
public class Store {

    private Pencil pencil;
}
``` 
이러한 경우에 Store객체가 Pencil객체에 의존성이 있다고 표현한다.

그리고 두 객체 간의 관계(의존성)을 맺어주는 것을 의존성 주입이라고 하며 생성자 주입, 필드 주입, 수정자 주입 등 다양한 방법이 있다.

```java
public class Store {

    private Pencil pencil;

    public Store() {
        this.pencil = new pencil();
    }
}
```
위의 코드는 다음과 같은 문제를 가지고 있다

- 두 클래스가 강하게 결합되어 있다
  - 만약 Store에서 Pencil이 아닌 Food와 같은 다른상품을 판매하고자 한다면 Store클래스의 생성자에 변경이 필요하다. 즉, 유연성이 떨어진다.
  - 이에 대한 해결책으로 상속을 떠올릴 수 있지만, 상속은 제약이 많고 확장성이 떨어지므로 피하는 것이 좋다.
- 객체들 간의 관계가 아니라 클래스 간의 관계가 맺어져 있다.
  - 올바른 객체지향적 설계라면 객체들 간에 관계가 맺어져야한다. 객체들 간에 관계가 맺어졌다면 다른 객체의 구체클래스(Pencil인지 Food인지 등)를 전혀 알지 못하더라도 인터페이스타입으로 사용할 수 있어야 한다.
  - 문제점의 근본적인 이유는 어떤 제품을 판매할 지에 대한 **관심이 분리되지 않았기 때문이다.**

```java
public interface Product {
    
}
```

위와 같은 문제를 해결하기 위해 다형성이 필요하다. Pencil, Food 등 여러가지 제품을 하나로 표현하기 위해 인터페이스가 필요하다.

```java
public class Pencil implements Product{

    public Pencil() {

    }
}
```

Store와 Pencil이 강하게 결합되어 있는 부분을 제거해주어야 한다. 이를 제거하기 위해서는 다음과 같이 외부에서 상품을 주입받아야 Store에서 구체클래스에 의존하지 않게 된다.

```java
public class Store {

    private Product product;

    public Store(Product product) {
        this.product = product;
    }
}
```

이러한 이유로 스프링이라는 DI 컨테이너를 필요로 하는 것이다. Store에서 Product 객체를 주입하기 위해서는 애플리케이션 실행 시점에 필요한 객체(빈)를 생성해야하며,
의존성이 있는 두 객체를 연결하기 위해 한 객체를 다른객체로 주입시켜야 한다.

예를 들어 다음과 같이 Pencil이라는 객체를 만들고, 그 객체를 Store로 주입시켜주는 역할을 위해 DI컨테이너가 필요한 것이다.


```java
public class Store {

    private Product product;

    public Store(Product product) {
        this.product = product;
    }
    
    Store store = new Store(product);
}
```

> ### 의존성 주입 정리
> 한 객체가 어떤 객체에 의존할 것인지는 별도의 관심사이다. 스프링은 의존성주입을 도와주는 DI컨테이너로써 강하게 결합된 클래스들을 분리하고, 
> 애플리케이션 실행시점에 객체간의 관계를 결정해줌으로써 결합도를 낮추고 유연성을 확보해준다.