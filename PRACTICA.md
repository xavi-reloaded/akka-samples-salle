## Práctica de actors

### Parte 1

1. Crea un servicio con 2 métodos que devuelvan respectivamente los siguientes tipos:

UserDTO
```
 + userName
```
   
    
DiceDTO
```
 + result
```


2. Encapsula el servicio en un actor que resuelva un comportamiento dependiendo del tipo del mensaje:

*** simplemente pinta un log dependiendo del caso ***

### Parte 2

Levanta un actor diferente por cada tipo de mensaje.

UserActor
DiceActor

### Parte 3

1. Modifica el rootActor para que supervise al UserActor.
2. UserActor recibe un mensaje que contatena al nombre:
```
"Hola" + UserDTO.username
```
2. Crea un **wait** en el UserActor para que tarde 5 segundos

Casos de uso:

* Cuando el actor UserActor llegue a 10 ejecuciones se lanzará una excepción que será recogida por el supervisor
* El supervisor levantará 4 UserActors cada vez que UserActor se caiga
   