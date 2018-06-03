# Magneto's Mutant Detector



Magneto's mutant detector es una API REST que permite detectar por medio de una matriz de ADN si un humano es mutante o no.

Como se menciona en el enunciado:
> Sabrás si un humano es mutante, si encuentras más de una secuencia 
> de cuatro letras iguales​, de forma oblicua, horizontal o vertical.

## Restricciones
  - La matriz de entrada debe ser una matriz cuadrada en caso contrario se retornará error 400 (BAD REQUEST) y no se persistirá el adn.
  - Los elementos de la matriz solo podrán ser los siguientes caracteres: A, C, G, T. De encontrarse otro caracter se retornará 400 (BAD REQUEST)
## Asunciones

  - Se considera no mutante cualquier ADN de dimensión menor a 4 x 4.
  - Una suceción de bases iguales mayor a 4 será considerada como una sola secuencia. 
  - Se asume que los ADN persistidos no podrán ser eliminados ni alterados.
  - Se considera una secuencia oblicua tanto la que tiene direccion arriba- izquierda hacia abajo-derecha, como la opuesta, arriba-derecha hacia abajo-izquierda.

## Endpoints

La API cuenta con 2 endpoints:

### 1. Servicio de detección de mutantes

##### POST   /mutant
Body ejemplo:
```javascript
{
    "dna": [
    	"AACCTT",
	    "ACGTCA",
	    "ATTGAA",
	    "GGCCGG",
	    "AGGTTT",
	    "ACCCGC"
     ]
}
```
El servicio retorna 200 OK si detecta un mutante, 403 FORBIDDEN si detecta un no mutante, o 400 BAD REQUEST si no cumple las restricciones ya mencionadas.

Al detectar un mutante o no mutante el ADN se almacena en una base de datos.

### 2. Servicio de estadísticas

##### GET   /stats
El servicio retorna el conteo de mutantes detectados, el conteo de humanos procesados y la relacion entre ambos. Ejemplo:
```javascript
{
    "ratio": 0.4,
    "count_mutant_dna": 2,
    "count_human_dna": 5
}
```
Si no hay humanos procedados el ratio será 0.

## Hosting

El server está hosteado en [Heroku] y su URL es:
 https://mutantdetector.herokuapp.com
 

Quedando así definidos los servicios /mutant y /stats con las siguientes URL's:
  - https://mutantdetector.herokuapp.com/mutant  [POST]
  - https://mutantdetector.herokuapp.com/stats   [GET]

## Configuración
La aplicación puede ser configurada con las siguientes properties.

| Property | Descripcion |
| ------ | ------ |
| validate.matrixsize | true o false, indicando si debe validar que la matriz sea cuadrada.(default true) |
| validate.bases | true o false,  si debe validar la sola presencia de bases A,C,G,T. (default true)|
| persist.asyncronally | true o false, indica si la persistencia se hace de forma asíncrona usando [Kafka] o de forma sincrónica. (default false) |
| compress.threshold | número N, indica si debe comprimir los ADN con dimensión mayor o igual a N x N al persistirlos. Si el valor es 0 no hay compresión. (default 7) |
| kafka.bootstrap-servers | URL del broker de Kafka (default localhost:9092) |



   [Heroku]: <https://www.heroku.com/>
   [Kafka]: <https://kafka.apache.org/>
   
