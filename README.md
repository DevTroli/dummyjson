# DummyJSON API

Aplicação Spring Boot que consulta carrinhos da [DummyJSON](https://dummyjson.com/) e demonstra operações com Streams e `Optional`.

## Como executar

Requisitos: **Java 17+**.

```bash
./mvnw spring-boot:run
```

Depois, acesse:

```text
http://localhost:8080/testar
```

O endpoint consulta `/carts?limit=0`, processa os dados e imprime os resultados no console.

## Saída formatada

A aplicação imprime cada carrinho usando uma lambda:

```text
Carrinho #[id] | Usuário: [userId] | Itens: [totalQuantity] | Total: US$ [valor]
```

Exemplo:

```text
Carrinho #1 | Usuário: 1 | Itens: 12 | Total: US$ 13037.88
```

## Operações implementadas

- Filtro de carrinhos com economia acima de 50.
- `flatMap` e `map` para listar nomes de produtos em maiúsculo.
- Ordenação dos produtos por preço decrescente.
- `reduce` para calcular o total de itens.
- `groupingBy` para agrupar produtos por preço.
- `max` com `Optional` para encontrar o produto de maior valor.

## Testes

```bash
./mvnw test
```
