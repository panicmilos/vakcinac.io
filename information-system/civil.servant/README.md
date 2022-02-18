# vakcinac.io-civil-servant-service
Servis za rad sa službenicima i zdravstvenim radnicima na projektu za predmet XML i Web Servisi.

## Stvari potrebne za pokretanje projekta

1. Dobra volja i neosuđujuć karakter
2. `Docker v19+`
3. `Java v1.8`

## Koraci za pokretanje projekta:

- Pre pokretanja aplikacije potrebno je podesiti exist i jena baze.

### Pokretanje exist baze: 

1. Pokrenuti docker komandu: `docker pull existdb/existdb:latest`
2. Za pokretanje instance exist baze potrebno je pokrenuti docker komandu: 
  
`docker run -it -d -p 8082:8080 -p 8443:8443 --name servantExist existdb/existdb:latest`

- Default port je:
  - 8082
- Moguće je menjati `exist.properties` fajl u slučaju da se koristi drugi port		

### Pokretanje jena baze:
1. Pokrenuti docker komandu: `docker pull stain/jena-fuseki`
2. Za pokretanje instance jena baze potrebno je pokrenuti docker komandu:

`docker run -p 3031:3030 -e ADMIN_PASSWORD=pw123 stain/jena-fuseki`

3. Nakon pokretanja baze potrebno je otići na "https://localhost:3031" i napraviti novi dataset
- Default port je:
  - 3031
- Moguće je menjati `jena.properties` fajl u slučaju da se koristi drugi port
- Ukoliko se radi izmena porta potrebno je promeniti prefikse za sparql grafove na putanji `resources/data/sparql/`

### Pokretanje aplikacije preko `mvn CLI`:
1. U `information-system` direktorijumu pokrenuti `mvn clean && mvn install`
2. U `information-system/civil.servant` direktorijumu pokrenuti `mvn spring-boot:run`